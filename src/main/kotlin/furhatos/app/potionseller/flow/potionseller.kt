package furhatos.app.potionseller.flow

import furhatos.app.potionseller.nlu.Flavor
import furhatos.app.potionseller.nlu.ListOfFlavors
import furhatos.app.potionseller.*
import furhatos.app.potionseller.nlu.*
import furhatos.app.potionseller.flow.*
import furhatos.flow.kotlin.*
import furhatos.gestures.Gestures
import furhatos.nlu.common.No
import furhatos.nlu.common.Number
import furhatos.nlu.common.Time
import furhatos.nlu.common.Yes
import java.time.LocalTime

/*
    General enquiries that we want to be able to handle, as well as an OrderPizzaIntent that is used for initial orders.
 */
val Questions: State = state(Interaction) {
    onResponse<RequestFlavorOptionsIntent> {
        furhat.gesture(Gestures.Smile)
        furhat.say("We have " + Flavor().optionsToText())
        furhat.gesture(Gestures.Smile)
        reentry()
    }
    onResponse<RequestOptionsIntent> {
        furhat.gesture(Gestures.Thoughtful)
        furhat.say("Our ingredients are " + Ingredient().optionsToText() + ". You can buy my ingredients by themselves" +
                " or mixed into a potion. The basic effect of the potions is healing, but you can add more effects. You can also change the strength.")
        furhat.gesture(Gestures.Smile)
        reentry()
    }
    onResponse<RequestStrengthOptionsIntent> {
        furhat.say("We can make your potion weak, normal, strong or extra strong. But beware! My potions mayhaps are too strong for you.")
        furhat.gesture(Gestures.Smile)
        reentry()
    }
}




// Start of interaction
val Start = state(parent = Questions) {
    onEntry {
        furhat.gesture(Gestures.Roll)
        furhat.ask("Greetings, Traveller! Welcome to my potion shoppe. What would you like to order?")
        furhat.gesture(Gestures.Thoughtful)
    }

    onResponse<PotionSellerIntent> {
        users.current.order.adjoin(it.intent)
        furhat.gesture(Gestures.Nod)
        furhat.say("I understand, traveller, you want to buy a ${it.intent}")
        furhat.gesture(Gestures.Thoughtful)
        goto(CheckOrder)
    }
}

// Form-filling state that checks any missing slots and if so, goes to specific slot-filling states.
val CheckOrder = state {
    onEntry {
        val order = users.current.order
        when {
            order.type == null -> goto(ConfirmasIngredient)
            else -> {
                furhat.gesture(Gestures.OpenEyes)
                furhat.say("Ahh, you are so inquisitive!")
                furhat.gesture(Gestures.BigSmile)
                goto(ConfirmOrder)
            }
        }
    }
}

/*
    State for handling changes to an existing order
 */
val OrderHandling: State = state(parent = Questions) {

    var clarificationcount = 0
    // Handler that re-uses our pizza intent but has a more intelligent response handling depending on what new information we get
    onResponse<PotionSellerIntent> {
        val order = users.current.order

        // Message to be constructed based on what data points we get from the user
        var message = "Okay"

        if (it.intent.flavor != null) message += ", adding ${it.intent.flavor}"
        // Deliver our message
        furhat.gesture(Gestures.Nod)

        clarificationcount++

        when{
            clarificationcount > 3 -> goto(AngryEndOrder)
        }

        if (it.intent.strength != null) {
            /* We are constructing a specific message depending on if we
            get a delivery place and/or time and if this slot already had a value
             */
            when {
                it.intent.strength != null -> { // We get only a delivery place
                    message += ", making it ${it.intent.strength} "
                    if (order.strength != null) message += "instead " // Add an "instead" if we are overwriting the slot
                }
            }
        }

        furhat.say(message)
        // Finally we join the existing order with the new one
        order.adjoin(it.intent)

        reentry()
    }

    // Specific handler for removing toppings since this is to complex to include in our OrderPizzaIntent (due to the ambiguity of adding vs removing toppings)
    onResponse<RemoveFlavorIntent> {
        clarificationcount++
        users.current.order.flavor?.removeFromList(it.intent?.flavor!!)
        furhat.gesture(Gestures.Nod)
        furhat.say("Okay, we remove ${it.intent?.flavor} from your potion")
        reentry()
    }
}

// Request toppings
val RequestFlavor : State = state(parent = OrderHandling) {
    onEntry {
        furhat.ask("Would you like an additional effect, or change the strength?")
        furhat.gesture(Gestures.Thoughtful)
    }

    onReentry {
        furhat.ask("Do you want any additional effect?")
        furhat.gesture(Gestures.Thoughtful)
    }

    onResponse<Yes> {
        furhat.gesture(Gestures.Nod)
        furhat.ask("What kind of effect or what strength do you want, traveller?")
        furhat.gesture(Gestures.Thoughtful)
    }

    onResponse<RequestOptionsIntent> {
        raise(RequestFlavorOptionsIntent())
    }

    onResponse<No> {
        furhat.gesture(Gestures.Nod)
        furhat.say("Okay, no additional effect for you, my liege.")
        users.current.order.flavor = ListOfFlavors()
        goto(CheckOrder)
    }

    onResponse<FlavorIntent> {
        furhat.say("Okay, ${it.intent.flavor}")
        furhat.gesture(Gestures.Nod)
        users.current.order.flavor = it.intent.flavor
        goto(CheckOrder)
    }
    onResponse<TellStrengthIntent> {
        furhat.say("Okay traveller, I will make it ${it.intent.strength}")
        furhat.gesture(Gestures.Nod)
        users.current.order.strength = it.intent.strength
        goto(CheckOrder)
    }
}

// Confirming order
val ConfirmOrder : State = state(parent = OrderHandling) {
    var clarification_counter = 0
    onEntry {
        furhat.ask("Your order is ${users.current.order}. Does this sound good?")
        furhat.gesture(Gestures.Thoughtful)
    }

    onResponse<Yes> {
        goto(EndOrder)
    }

    onResponse<No> {
        clarification_counter++
        when {
            clarification_counter > 3 -> goto(AngryEndOrder)
        }
        goto(ChangeOrder)
    }
}


// Changing order
val ChangeOrder = state(parent = OrderHandling) {
    var clarification_counter = 0
    onEntry {
        furhat.ask("My liege, would you like to adjust your order?")
        furhat.gesture(Gestures.Thoughtful)
    }

    onReentry {
        furhat.ask("Your potion currently is ${users.current.order}. Would you like to change anything, if yes, what is your new order?")
        furhat.gesture(Gestures.Thoughtful)
    }

    onResponse<Yes> {
        clarification_counter++
        when {
            clarification_counter > 2 -> goto(AngryEndOrder)
        }
        reentry()
    }

    onResponse<No> {
        clarification_counter++
        when {
            clarification_counter > 2 -> goto(AngryEndOrder)
        }
        goto(EndOrder)
    }

    onResponse<ChangeTypeIntent> {
        clarification_counter++
        when {
            clarification_counter > 2 -> goto(AngryEndOrder)
        }
        goto(ConfirmasPotion)
    }

    onResponse<TellStrengthIntent> {
        clarification_counter++
        when {
            clarification_counter > 2 -> goto(AngryEndOrder)
        }
        goto(RequestStrength)
    }
}

val ConfirmasPotion : State = state(parent = OrderHandling) {
    onEntry {
        furhat.ask("Would you like to have this ingredient as a potion or by itself?")
    }

    onResponse<TellPotionIntent> {
        furhat.gesture(Gestures.Thoughtful)
        users.current.order.type = it.intent.type
        goto(ConfirmOrder)
    }

    onResponse<TellIngredientIntent> {
        goto(ConfirmOrder)
    }
}

val ConfirmasIngredient : State = state(parent = OrderHandling) {


    onEntry {
        furhat.ask("You want to buy this ingredient by itself, correct?")
        furhat.gesture(Gestures.Thoughtful)
    }

    onResponse<TellIngredientIntent> {
        users.current.order.type = it.intent.type
        goto(ConfirmOrder)
    }

    onResponse<No> {
        furhat.gesture(Gestures.Nod)
        goto(ConfirmasPotion)
    }
    onResponse<TellPotionIntent> {
        furhat.gesture(Gestures.Nod)
        users.current.order.type = it.intent.type
        goto(RequestFlavor)
    }
}

// Request delivery point
val RequestStrength : State = state(parent = OrderHandling) {
    onEntry {
        furhat.ask("Choose the strength of thy potion.")
    }

    onResponse<RequestOptionsIntent> {
        raise(it, RequestStrengthOptionsIntent())
    }

    onResponse<TellStrengthIntent> {
        furhat.gesture(Gestures.Nod)
        furhat.say("Okay, ${it.intent.strength}")
        users.current.order.strength = it.intent.strength
        goto(CheckOrder)
    }
}
// Order completed
val EndOrder = state {
    onEntry {
        furhat.gesture(Gestures.BigSmile)
        furhat.say("Huzzah! You will not be disappointed, traveller... Thank you for your purchase!")
        furhat.gesture(Gestures.Wink)
        goto(Idle)
    }
}

val AngryEndOrder = state {
    onEntry {
        furhat.gesture(Gestures.Shake)
        furhat.say("I have no patience left for you... With all your clarification requests! Begone!! Shoo!!! My potions are too strong for you anyways.")
        furhat.gesture(Gestures.ExpressAnger)
        goto(Idle)
    }
}
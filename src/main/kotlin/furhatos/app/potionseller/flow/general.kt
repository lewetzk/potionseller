package furhatos.app.potionseller.flow

import furhatos.flow.kotlin.*

val Idle : State = state {

    init {
        if (users.count > 0) {
            furhat.attend(users.random)
            goto(Start)
        }
    }

    onEntry {
        furhat.attendNobody()
    }

    onUserEnter {
        furhat.attend(it)
        goto(Start)
    }
}

val Interaction : State = state {

    var nomatches = 0
    var silences = 0

    onResponse {
        nomatches++
        when (nomatches)  {
            1 -> furhat.ask("Traveller, what are you meaning? Say it again!")
            2 -> furhat.ask("I still didn't get that... Rephrase it?")
            else -> {
                furhat.say("Still couldn't get that.")
                reentry()
            }
        }
    }

    onNoResponse {
        silences++
        when (silences)  {
            1 -> furhat.ask("Eh? Speak up, traveller!")
            2 -> furhat.ask("Rats, I still didn't hear you. Could you speak up please?")
            else -> {
                furhat.say("Still didn't hear anything")
                reentry()
            }
        }
    }

    onUserLeave(instant=true) {
        if (users.count > 0) {
            if (it == users.current) {
                furhat.attend(users.other)
                goto(Start)
            } else {
                furhat.glance(it)
            }
        } else {
            goto(Idle)
        }
    }

    onUserEnter(instant = true) {
        furhat.glance(it)
    }
}
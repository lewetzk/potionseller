package furhatos.app.potionseller.nlu
import furhatos.nlu.TextGenerator
import furhatos.util.Language
import furhatos.nlu.*
import furhatos.nlu.common.*
import furhatos.nlu.common.Number
import furhatos.records.GenericRecord

open class PotionSellerIntent : Intent(), TextGenerator {
    var flavor : ListOfFlavors? = null
    var ingredient: Ingredient? = null
    var type : Type? = null
    var strength : Strength? = null

    override fun getExamples(lang: Language): List<String> {
        return listOf(
            "I would like @ingredient as a potion",
            "I want a potion with @ingredient and @flavor",
            "I want to order @ingredient as a potion",
            "I want to buy @ingredient as an ingredient",
            "I want to add @flavor",
            "I also want @flavor",
            "I want @ingredient",
            "I want to buy @ingredient",
            "I would like @ingredient",
            "I want to buy @ingredient as a potion with @flavor, make it @strength",
            "I want to buy a @strength potion with @ingredient",
            "I would like a @strength potion"
        )
    }

    override fun toText(lang : Language) : String {
        return generate(lang, " [$ingredient] [as a $type] [with $flavor] [and $strength strength]");
    }

    override fun toString(): String {
        return toText()
    }

    override fun adjoin(record: GenericRecord<Any>?) {
        super.adjoin(record)
        if (flavor != null){
            flavor?.list = flavor?.list?.distinctBy { it.value }!!.toMutableList()
        }
    }
}


class FlavorIntent : AddFlavorIntent() {
    override fun getExamples(lang: Language): List<String> {
        return listOf(
            "@flavor",
            "yes @flavor",
            "I want @flavor")
    }
}

class TellIngredientIntent : Intent() {
    var type : Type? = null
    override fun getExamples(lang: Language): List<String> {
        return listOf("yes",
            "as an ingredient",
            "ingredient",
            "by itself")
    }
}


class TellPotionIntent : Intent() {
    var type : Type? = null
    override fun getExamples(lang: Language): List<String> {
        return listOf(
            "potion",
            "put it in a potion",
            "as a potion")
    }
}

class TellStrengthIntent : Intent() {
    var strength : Strength? = null

    override fun getExamples(lang: Language): List<String> {
        return listOf("make it @strength", "@strength", "i want a @strength potion")
    }
}

open class AddFlavorIntent : Intent() {
    var flavor : ListOfFlavors? = null

    override fun getExamples(lang: Language): List<String> {
        return listOf(
            "I want @flavor",
            "I also want @flavor",
            "I want to add @flavor",
            "add @flavor"
        )
    }
}

open class ChangeTypeIntent : Intent() {
    var type : Type? = null

    override fun getExamples(lang: Language): List<String> {
        return listOf(
            "I want it as a @type",
            "Make it as a @type instead",
            "As a @type"
        )
    }
}
class RemoveFlavorIntent : Intent() {
    var flavor : ListOfFlavors? = null

    override fun getExamples(lang: Language): List<String> {
        return listOf(
            "I want to remove the wisdom effect",
            "no wisdom",
            "I do not want the wisdom effect",
            "I don't want wisdom",
            "remove wisdom from my potion")
    }
}

class RequestOptionsIntent : Intent()  {
    override fun getExamples(lang: Language): List<String> {
        return listOf("what options are there",
            "what are the options",
            "what can I choose from",
            "what do you have",
            "what ingredients do you have")
    }
}

class RequestFlavorOptionsIntent : Intent()  {
    override fun getExamples(lang: Language): List<String> {
        return listOf("what effects do you have",
            "what different effects do you have?")
    }
}

class RequestStrengthOptionsIntent : Intent()  {
    override fun getExamples(lang: Language): List<String> {
        return listOf("What strength options do you have",
            "how strong can you make it")
    }
}
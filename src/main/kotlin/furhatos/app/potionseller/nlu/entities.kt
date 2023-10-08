package furhatos.app.potionseller.nlu

import furhatos.nlu.EnumEntity
import furhatos.nlu.ListEntity
import furhatos.util.Language
import kotlin.sequences.Sequence

class ListOfFlavors : ListEntity<Flavor>()

class Flavor : EnumEntity(speechRecPhrases = true) {

    override fun getEnum(lang: Language): List<String> {
        return listOf("love", "gender change", "stamina", "mana", "defense", "strength", "teleportation", "resurrection",
            "amnesia", "wisdom", "invincibility", "fast movement", "fire damage", "shock damage")
    }

}

class Ingredient : EnumEntity() {

    override fun getEnum(lang: Language): List<String> {
        return listOf("bat wings", "mermaid hair", "lizard eyes", "daffodil", "stinging nettle", "kobold teeth", "werewolf fur",
            "apple", "strawberry cake", "cheese", "banana", "toenails", "fire salamander")
    }

    // Method overridden to produce a spoken utterance of the place
    override fun toText(lang: Language): String {
        return generate(lang,"$value")
    }
}

class Type : EnumEntity() {
    override fun getEnum(lang: Language): List<String> {
        return listOf("ingredient", "potion")
    }

    // Method overridden to produce a spoken utterance of the place
    override fun toText(lang: Language): String {
        return generate(lang,"$value")
    }
}

class Strength : EnumEntity() {

    override fun getEnum(lang: Language): List<String> {
        return listOf(
            "weak",
            "normal",
            "strong",
            "medium",
            "medium strong",
            "very strong",
            "extra strong",
            "low strength"
        )
    }}

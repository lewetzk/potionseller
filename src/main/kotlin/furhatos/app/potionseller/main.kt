package furhatos.app.potionseller

import furhatos.app.potionseller.flow.Idle
import furhatos.flow.kotlin.*
import furhatos.skills.Skill

class PotionsellerSkill : Skill() {
    override fun start() {
        Flow().run(Idle)
    }
}

fun main(args: Array<String>) {
    Skill.main(args)
}

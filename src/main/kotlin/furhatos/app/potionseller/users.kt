package furhatos.app.potionseller

import furhatos.app.potionseller.nlu.PotionSellerIntent
import furhatos.flow.kotlin.NullSafeUserDataDelegate
import furhatos.records.User

// Associate an order to a user
val User.order by NullSafeUserDataDelegate { PotionSellerIntent() }
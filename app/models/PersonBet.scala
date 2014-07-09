package models

import javax.persistence._
import models.BetType.BetType
import play.db.ebean.Model

object BetType extends Enumeration {
  type BetType = Value
  val Over, Under = Value
}

@Entity class PersonBet extends Model {
  @Id var id: Long = 0
  var betType: BetType = BetType.Over
}

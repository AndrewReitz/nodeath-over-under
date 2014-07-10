package models

import javax.persistence._
import models.BetType.BetType
import play.db.ebean.Model

object BetType extends Enumeration {
  type BetType = Value
  val Over, Under = Value

  def fromString(s: String): BetType = {
    for (value <- values) {
      if (value.toString == s) {
        return value
      }
    }
    throw new IllegalStateException("Unknown Bet Type")
  }
}

@Entity class PersonBet(val name: String, val betType: BetType = BetType.Over) extends Model {
  @Id var id: Long = 0
}

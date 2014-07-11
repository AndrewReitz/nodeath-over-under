package models

case class PersonBet(name: String, betType: String)
case class Bet(title: String, description: String, peopleBets: Seq[PersonBet])

import sorm._

object Db extends Instance(
  entities = Set(Entity[PersonBet](), Entity[Bet]()),
  url = "jdbc:h2:mem:test"
)
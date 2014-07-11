package viewmodels

import models.PersonBet

class Bet(idc: Long, titlec: String, descriptionc: String, personBetsc: Seq[PersonBet]) {
  val id = idc
  val title = titlec
  val description = descriptionc
  val personBets = personBetsc
}

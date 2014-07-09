package models

import javax.persistence._

import play.db.ebean.Model

@Entity class Bet extends Model {
  @Id var id: Long = 0
  var title: String = ""
  var description: String = ""
}

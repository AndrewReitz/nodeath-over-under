package models

import javax.persistence._

import play.db.ebean.Model

import scala.collection.mutable.ListBuffer

@Entity class Bet(val title: String, val description: String) extends Model {
  @Id var id: Long = 0
  @OneToMany var peopleBets: ListBuffer[PersonBet] = ListBuffer()
}

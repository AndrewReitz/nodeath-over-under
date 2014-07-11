package controllers

import models.Db
import play.Logger
import play.api.data._
import play.api.data.Forms._
import play.api.mvc._

object Application extends Controller {

  /**
   * GET
   * Main page displaying all bets
   */
  def index = Action { implicit request =>
    val bets = Db.query[models.Bet].fetch()
    val betViewModels = bets.map(b => new viewmodels.Bet(b.id, b.title, b.description, b.peopleBets)).toList
    val temp = new viewmodels.Bet(1, "TestTitle", "Test", List())
    Ok(views.html.index(betViewModels))
  }

  /**
   * GET
   * Create a new bet page
   */
  def createNewBet = Action { implicit request =>
    Ok(views.html.create(""))
  }

  /**
   * GET
   * Place a new bet page
   */
  def placeBet(betId: Long) = Action { implicit request =>
    val bet = Db.query[models.Bet].whereEqual("id", betId).fetchOne().get
    Ok(views.html.placeBet(new viewmodels.Bet(bet.id, bet.title, bet.description, bet.peopleBets)))
  }

  /**
   * GET
   * Bet details page
   */
  def getBetDetails(betId: Long) = Action { implicit request =>
    val bet = Db.query[models.Bet].whereEqual("id", betId).fetchOne().get
    Logger.debug("personbets: " + bet.peopleBets.length)
    Ok(views.html.betDetails(bet))
  }


  def deleteBet(name: String) = Action {
    Db.query[models.Bet]
      .whereEqual("title", name)
      .fetchOne()
      .map(a => Db.delete(a))
    Ok
  }

  /** addPersonBet Form mapping */
  case class PersonBet(name: String, betType: String, betId: Long)
  val personBetForm = Form(
    mapping(
      "name" -> text,
      "betType" -> text,
      "betId" -> longNumber
    )(PersonBet.apply)(PersonBet.unapply)
  )

  /**
   * POST
   * Place a bet made by a person
   */
  def addPersonBet = Action { implicit request =>
    val personBet = personBetForm.bindFromRequest().get
    val model = Db.save(new models.PersonBet(personBet.name, personBet.betType))

    Db.query[models.Bet]
      .whereEqual("id", personBet.betId)
      .fetchOne()
      .map(a => a.copy(peopleBets = a.peopleBets :+ model))
      .map(a => Db.save(a))

    Redirect(routes.Application.index)
  }

  /** Add new bet form mapping */
  case class Bet(title: String, description: String)
  val betForm = Form(
    mapping(
      "title" -> text,
      "description" -> text
    )(Bet.apply)(Bet.unapply)
  )

  /**
   * POST
   * Add a new bet
   */
  def addBet = Action { implicit request =>
    val bet = betForm.bindFromRequest().get
    Db.save(models.Bet(bet.title, bet.description, List()))
    Redirect(routes.Application.index)
  }
}
package controllers

import models.BetType
import play.Logger
import play.api.data._
import play.api.data.Forms._
import play.api.mvc._
import play.db.ebean.Model.Finder
import scala.collection.JavaConverters._

object Application extends Controller {

  /**
   * GET
   * Main page displaying all bets
   */
  def index = Action { implicit request =>
    val bets: java.util.List[models.Bet] = new Finder[Long, models.Bet](classOf[Long], classOf[models.Bet]).all()
    Ok(views.html.index(bets.asScala.toList))
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
    val bet = new Finder[Long, models.Bet](classOf[Long], classOf[models.Bet]).byId(betId)
    Ok(views.html.placeBet(bet))
  }

  /**
   * GET
   * Bet details page
   */
  def getBetDetails(betId: Long) = Action { implicit request =>
    val bet = new Finder[Long, models.Bet](classOf[Long], classOf[models.Bet]).byId(betId)
    Ok(views.html.betDetails(bet))
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
    val model = new models.PersonBet(personBet.name, BetType.fromString(personBet.betType))
    val bet = new Finder[Long, models.Bet](classOf[Long], classOf[models.Bet]).byId(personBet.betId)
    bet.peopleBets :+ model
    model.save()
    bet.save()

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
    new models.Bet(bet.title, bet.description).save()
    Redirect(routes.Application.index)
  }
}
package controllers

import play.api.mvc._

object Application extends Controller {

  /** Main page displaying all bets */
  def index = Action { implicit request =>
    Ok(views.html.index(""))
  }

  /** Create a new bet */
  def createNew = Action { implicit request =>
    Ok(views.html.index(""))
  }

  /** Login / signup page */
  def login = Action { implicit request =>
    Ok(views.html.index(""))
  }
}
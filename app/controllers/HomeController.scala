package controllers

import javax.inject._
import play.api._
import play.api.mvc._
import models._

@Singleton
class HomeController @Inject() extends Controller {

    def index = Action {
        Ok(views.html.index("kik"))
    }

    def login = Action {

        Ok(views.html.login())
    }
    def register = Action {

        Ok(views.html.registration(Disciplines.getAll, Facultees.getAll))
    }
}

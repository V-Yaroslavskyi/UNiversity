import play.api.Application
import play.api.GlobalSettings
import play.api.Play.current
import play.api.db.DB

import scala.slick.driver.PostgresDriver.simple._

import models._

object Global extends GlobalSettings{

    override def onStart(add:Application){

        lazy val database = Database.forConfig("mydb")


    }


}

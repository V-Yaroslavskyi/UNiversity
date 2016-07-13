package models


import play.api.data.Form
import play.api.data.Forms._

import slick.driver.PostgresDriver.simple._

case class Admin(id:Int, login:String, password:String)


case class adminData(name: String, password:String)

object adminForm {

    val form = Form(

        mapping(
            "login" -> nonEmptyText,
            "password" -> nonEmptyText
        )(adminData.apply)(adminData.unapply)

    )
}

class Admins(tag: Tag) extends Table[Admin](tag, "admin") {

    def id = column[Int]("id", O.PrimaryKey)

    def login = column[String]("login")

    def pass = column[String]("password")


    def * = (id, login, pass)<>(Admin.tupled, Admin.unapply)

}

object Admins{

    val admins = TableQuery[Admins]
// inject
    def findApplicant(userData: adminData): Option[Admin] ={

        Database.forConfig("mydb") withSession { implicit session =>
            admins.filter(_.login === userData.name).filter( _.pass === userData.password).firstOption

        }

    }

    def getAll: List[Admin] = {
        Database.forConfig("mydb") withSession { implicit session =>
            admins.list
        }
    }

}

    


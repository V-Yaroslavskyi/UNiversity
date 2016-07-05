package models

import play.api.Play.current
import play.api.db.DB
import play.api.data.Forms._
import play.api.data.Form

import slick.driver.PostgresDriver.simple._

case class UserData(name: String, surname:String, password:String)

object userForm {

    val form = Form(

        mapping(
            "name" -> nonEmptyText,
            "surname" -> nonEmptyText,
            "password" -> nonEmptyText
        )(UserData.apply)(UserData.unapply)

    )
}
case class RegData(name:String,
                   surname:String,
                   zno_ukr:Int,
                   zno_math:Int,
                   discipline3:String,
                   zno_3rd:Int,
                   att:Int,
                   passwordsignup:String,
                   passwordsignup_confirm:String,
                   facultee:String)

object regForm {

    val form = Form(
        mapping(
            "name" -> nonEmptyText,
            "surname" -> nonEmptyText,
            "zno_ukr" -> number(min = 100, max = 200),
            "zno_math" -> number(min = 100, max = 200),
            "discipline3" -> nonEmptyText,
            "zno_3rd" -> number(min = 100, max = 200),
            "att" -> number(min = 100, max = 200),
            "passwordsignup" -> nonEmptyText,
            "passwordsignup_confirm" -> nonEmptyText,
            "facultee" -> nonEmptyText
        )(RegData.apply)(RegData.unapply)
    )
}


case class Applicant(id: Int, name: String, surname: String, password: String,
                     zno_ukr: Int, zno_math: Int, zno_3rd_id: Int, zno_3rd_points: Int, attestate: Int,
                     facultee_id: Int, status: Option[Boolean])


class Applicants(tag: Tag) extends Table[Applicant](tag, "applicants") {

    def id = column[Int]("id", O.PrimaryKey, O.AutoInc)

    def name = column[String]("name")

    def surname = column[String]("surname")

    def password = column[String]("password")

    def zno_ukr = column[Int]("zno_ukr")

    def zno_math = column[Int]("zno_math")

    def zno_3rd_id = column[Int]("zno_3rd_id")

    def zno_3rd_points = column[Int]("zno_3rd_points")

    def attestate = column[Int]("attestate")

    def facultee_id = column[Int]("facultee_id")

    def status = column[Option[Boolean]]("status")

    def facultee = foreignKey("fk_facultee", facultee_id, Facultees.facultees)(_.id)

    def zno_3rd = foreignKey("fk_zno", zno_3rd_id, Disciplines.disciplines)(_.id)

    def * = (id, name, surname, password, zno_ukr, zno_math, zno_3rd_id, zno_3rd_points, attestate,
        facultee_id, status) <>(Applicant.tupled, Applicant.unapply)


}

object Applicants {
    val applicants = TableQuery[Applicants]

    val dbConfig = Database.forConfig("mydb")


    def addApplicant(newApp:Applicant):Unit = {
        Database.forConfig("mydb") withSession { implicit session =>

            applicants += newApp

        }
    }

    def findApplicant(userData: UserData): Option[Applicant] ={

        Database.forConfig("mydb") withSession { implicit session =>
            println(userData)
            applicants.filter(_.name === userData.name).filter(_.surname === userData.surname)
                .filter( _.password === userData.password).firstOption

        }

    }

    def getAll: List[Applicant] = {
        Database.forConfig("mydb") withSession { implicit session =>
            applicants.list
        }
    }

    def getInfo = {
        val kek = applicants.filter {
            _.status === true
        }
    }
}
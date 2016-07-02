package models

import java.util.UUID

import play.api.db.DB
import play.api.Play.current
import slick.driver.PostgresDriver.simple._

case class Discipline(id:Int, name:String)


class Disciplines(tag: Tag) extends Table[Discipline](tag, "disciplines") {

    def id = column[Int]("id", O.PrimaryKey)

    def name = column[String]("name")

    def * = (id, name)<>(Discipline.tupled, Discipline.unapply)

}

object Disciplines{

    val disciplines = TableQuery[Disciplines]

    def getAll: List[Discipline] = {
        Database.forConfig("mydb") withSession { implicit session =>
            disciplines.list
        }
    }

}

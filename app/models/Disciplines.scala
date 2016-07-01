package models

import play.api.Play.current
import play.api.db.DB
import slick.driver.PostgresDriver.simple._

case class Discipline(id:Int, name:String)


class Disciplines(tag: Tag) extends Table[Discipline](tag, "Discipline") {

    def id = column[Int]("id", O.PrimaryKey)

    def name = column[String]("name")

    def * = (id, name)<>(Discipline.tupled, Discipline.unapply)

}
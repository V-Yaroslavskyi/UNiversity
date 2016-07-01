package models

import java.util.UUID

import slick.driver.PostgresDriver.simple._

case class Discipline(id:UUID, name:String)


class Disciplines(tag: Tag) extends Table[Discipline](tag, "Disciplines") {

    def id = column[UUID]("id", O.PrimaryKey)

    def name = column[String]("name")

    def * = (id, name)<>(Discipline.tupled, Discipline.unapply)

}

object Disciplines{

    val disciplines = TableQuery[Disciplines]

}

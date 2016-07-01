package models

import java.util.UUID
import slick.driver.PostgresDriver.simple._

case class Facultee(id: UUID, name: String, quote:Int, zno_3rd_id:UUID)

class Facultees(tag: Tag) extends Table[Facultee](tag, "Facultees") {

    def id = column[UUID]("id", O.PrimaryKey)

    def name = column[String]("name")

    def quote = column[Int]("quote")

    def zno_3rd_id = column[UUID]("zno_3rd_id")

    def zno_3rd = foreignKey("fk_zno", zno_3rd_id, Disciplines.disciplines)(_.id)

    def * = (id, name, quote, zno_3rd_id)<>(Facultee.tupled, Facultee.unapply)

}

object Facultees{
    val facultees = TableQuery[Facultees]
}
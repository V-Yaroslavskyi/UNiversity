package models
import java.util.UUID

import scala.slick.lifted.ForeignKeyQuery
import slick.driver.PostgresDriver.simple._

case class Applicant(id:UUID, name:String, surname:String, password:String,
                     zno_ukr:Int, zno_math:Int, zno_3rd_id:UUID, zno_3rd_points:Int, attestate:Int,
                     facultee_id:UUID, point_sum:Int, status:Boolean)

class Applicants(tag: Tag) extends Table[Applicant](tag, "Applicants") {

    def id = column[UUID]("id", O.PrimaryKey)
    def name = column[String]("name")
    def surname = column[String]("surname")
    def password = column[String]("password")
    def zno_ukr = column[Int]("zno_ukr")
    def zno_math = column[Int]("zno_math")
    def zno_3rd_id = column[UUID]("zno_3rd_id")
    def zno_3rd_points = column[Int]("zno_3rd_points")
    def attestate = column[Int]("attestate")
    def facultee_id = column[UUID]("facultee_id")
    def point_sum = column[Int]("point_sum")
    def status = column[Boolean]("status")

    def facultee = foreignKey("fk_facultee", facultee_id, Facultees.facultees)(_.id)
    def zno_3rd = foreignKey("fk_zno", zno_3rd_id, Disciplines.disciplines)(_.id)

    def * = (id, name, surname, password, zno_ukr, zno_math, zno_3rd_id, zno_3rd_points, attestate,
             facultee_id, point_sum, status) <> (Applicant.tupled, Applicant.unapply)


}
object Applicants{
    val applicants = TableQuery[Applicants]
}
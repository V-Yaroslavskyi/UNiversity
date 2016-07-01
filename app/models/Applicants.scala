package models
import slick.driver.PostgresDriver.simple._

case class Applicant(id:Int, name:String, surname:String, password:String,
                      zno_ukr:Int, zno_math:Int, zno_3rd_id:Int, zno_3rd_points:Int, attestate:Int,
                      facultee_id:Int, point_sum:Int, status:Boolean)

class Applicants(tag: Tag) extends Table[Applicant](tag, "Applicant") {

    def id = column[Int]("id", O.PrimaryKey)
    def name = column[String]("name")
    def surname = column[String]("surname")
    def password = column[String]("password")
    def zno_ukr = column[Int]("zno_ukr")
    def zno_math = column[Int]("zno_math")
    def zno_3rd_id = column[Int]("zno_3rd_id")
    def zno_3rd_points = column[Int]("zno_3rd_points")
    def attestate = column[Int]("attestate")
    def facultee_id = column[Int]("facultee_id")
    def point_sum = column[Int]("point_sum")
    def status = column[Boolean]("status")
    def * = (id, name, surname, password, zno_ukr, zno_math, zno_3rd_id, zno_3rd_points,
        attestate, facultee_id, point_sum, status)<>(Applicant.tupled, Applicant.unapply)
}

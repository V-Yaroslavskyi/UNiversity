package dao

import javax.inject.{Inject, Singleton}

import models._
import play.api.db.slick.DatabaseConfigProvider
import play.api.db.slick.HasDatabaseConfig
import slick.driver.JdbcProfile

import scala.concurrent.{ExecutionContext, Future}
import slick.lifted.Tag
import slick.driver.PostgresDriver.api._

trait FaculteeComponent{

    class Facultees(tag: Tag) extends Table[Facultee](tag, "facultees") {

        def id = column[Int]("id", O.PrimaryKey)

        def name = column[String]("name")

        def quote = column[Int]("quote")

        def zno_3rd_id = column[Int]("zno_3rd_id")

        def * = (id, name, quote, zno_3rd_id)<>(Facultee.tupled, Facultee.unapply)

    }
}

@Singleton
class FaculteeDAO @Inject() (protected val dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext) extends FaculteeComponent
    with DisciplineComponent
    with HasDatabaseConfig[JdbcProfile]{

    val dbConfig = dbConfigProvider.get[JdbcProfile]

    val facultees = TableQuery[Facultees]

    def getFaculteeName(app: Applicant): Future[Option[Facultee]] ={

            db.run(facultees.filter(_.id === app.facultee_id).result.headOption)
        }

    def getAll: Future[Seq[Facultee]] = {

        val query = for (fac <- facultees) yield fac
        db.run(query.result)
    }


}

package dao

import javax.inject.Inject

import com.google.inject.Singleton
import models._
import play.api.db.slick.DatabaseConfigProvider
import play.api.db.slick.HasDatabaseConfig
import slick.driver.JdbcProfile

import scala.concurrent.{ExecutionContext, Future}
import slick.lifted.Tag
import slick.driver.PostgresDriver.api._


trait DisciplineComponent{

    class Disciplines(tag: Tag) extends Table[Discipline](tag, "disciplines") {

        def id = column[Int]("id", O.PrimaryKey)

        def name = column[String]("name")

        def * = (id, name)<>(Discipline.tupled, Discipline.unapply)

    }

}

@Singleton
class DisciplineDAO @Inject() (protected val dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext) extends DisciplineComponent
    with HasDatabaseConfig[JdbcProfile]{

    val dbConfig = dbConfigProvider.get[JdbcProfile]

    val disciplines = TableQuery[Disciplines]

    def getAll: Future[Seq[Discipline]] = {

        val query = for (disc <- disciplines) yield disc
        db.run(query.result)
    }

}

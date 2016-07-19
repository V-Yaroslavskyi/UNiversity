package dao

import javax.inject.{Inject, Singleton}

import models._
import play.api.db.slick.DatabaseConfigProvider
import play.api.db.slick.HasDatabaseConfig
import slick.driver.JdbcProfile

import scala.concurrent.{ExecutionContext, Future}
import slick.lifted.Tag
import slick.driver.PostgresDriver.api._



trait AdminComponent{

    class Admins(tag: Tag) extends Table[Admin](tag, "admin") {

        def id = column[Int]("id", O.PrimaryKey)

        def login = column[String]("login")

        def pass = column[String]("password")


        def * = (id, login, pass)<>(Admin.tupled, Admin.unapply)

    }

}


@Singleton
class AdminDAO @Inject() (protected val dbConfigProvider: DatabaseConfigProvider )
                         (implicit ec: ExecutionContext)
    extends AdminComponent
    with HasDatabaseConfig[JdbcProfile]{

    val dbConfig = dbConfigProvider.get[JdbcProfile]
    val admins = TableQuery[Admins]

    def findAdmin(userData: adminData): Future[Option[Admin]] ={

        db.run(admins.filter(_.login === userData.name).filter( _.pass === userData.password).result.headOption)

    }


}

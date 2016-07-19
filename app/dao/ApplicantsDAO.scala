package dao

import javax.inject.{Inject, Singleton}

import models.{Applicant, Facultee, UserData}
import play.api.db.slick.DatabaseConfigProvider
import play.api.db.slick.HasDatabaseConfig
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import slick.driver.JdbcProfile

import scala.concurrent.{ExecutionContext, Future}
import slick.lifted.Tag
import slick.driver.PostgresDriver.api._


trait ApplicantsComponent{

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

        def * = (id, name, surname, password, zno_ukr, zno_math, zno_3rd_id, zno_3rd_points, attestate,
            facultee_id, status) <>(Applicant.tupled, Applicant.unapply)

    }

}


@Singleton
class ApplicantsDAO @Inject() (protected val dbConfigProvider: DatabaseConfigProvider, faculteeDAO: FaculteeDAO, disciplineDAO: DisciplineDAO)
//                              (implicit ec: ExecutionContext)
    extends ApplicantsComponent
    with HasDatabaseConfig[JdbcProfile]{
    val dbConfig = dbConfigProvider.get[JdbcProfile]

    val applicants = TableQuery[Applicants]

//    def enroll: Future[Unit] ={
//
//        db.run(applicants.map(a => a.status).update(Some(false)))
//        db.run(applicants.map(a => a.status).update(Some(false)))
//        val query = for {f <- faculteeDAO.facultees
//                                val id = f.id
//                                val quote = f.quote
//            val q1 = applicants.filter(_.facultee_id === 1)
//            val q2 = q1.map(a => (a.zno_3rd_points + a.zno_math + a.zno_ukr + a.attestate, a.status))
//            val q3 = q1.sortBy(_.attestate.desc)
//            val q4 = q3.take(2)
//            val q5 = q4.map(x => x.status)}
//            q5.update(Some(true))

//        db.run(query.result)

//    }

    def addApplicant(newApp:Applicant):Future[Unit] = {

        db.run(applicants += newApp).map(_ => ())

    }

    def appFac:Future[Seq[(Applicant, String, String)]] = {

        val query = for {a <- applicants
                         f <- faculteeDAO.facultees if a.facultee_id === f.id
                         d <- disciplineDAO.disciplines if a.zno_3rd_id === d.id}
                    yield (a, f.name, d.name)

        db.run(query.result)
    }

    def findApplicant(userData: UserData): Future[Option[(Applicant, String)]] = {

//        val query = for{app <- applicants if app.name.toString() == userData.name &&
//                                             app.surname.toString() == userData.surname &&
//                                             app.password.toString() == userData.password
//        fac <- faculteeDAO.facultees if  app.facultee_id == fac.id}

    val query = for{
        app <- applicants if app.name === userData.name &&
                             app.surname === userData.surname &&
                             app.password === userData.password
        fac <- faculteeDAO.facultees if  app.facultee_id === fac.id}

        yield (app, fac.name)


        db.run(query.result.headOption)

    }


    def getAll: Future[Seq[Applicant]] = {

        val query = for (app <- applicants) yield app
        db.run(query.result)
    }

}

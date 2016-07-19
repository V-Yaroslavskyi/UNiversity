package controllers

import javax.inject._

import play.api.mvc._
import models._
import dao._

import scala.concurrent.{ExecutionContext, Future}

// async handling response inject session config db

@Singleton
class HomeController @Inject()(applicantsDAO: ApplicantsDAO, faculteeDAO: FaculteeDAO, disciplineDAO: DisciplineDAO,
                               adminDAO: AdminDAO)
                              (implicit ec: ExecutionContext)
    extends Controller {

    def enroll = Action{
//        applicantsDAO.enroll
        Redirect(routes.HomeController.adminAuth())
    }

    def adminIndex = Action{
        Ok(views.html.admin())
    }

    def adminAuth = Action.async {
        implicit request =>
            adminForm.form.bindFromRequest.fold(
                formWithErrors => {
                    Future(BadRequest(views.html.error("formWithErrors")))
                },
                adminData => {

                    for {
                     user <- adminDAO.findAdmin(adminData)
                     app  <- applicantsDAO.appFac
                    }
                        yield
                            if (user.isDefined)
                                Ok(views.html.adminPanel(app))
                            else
                                Ok(views.html.error("Користувача з таким іменем/паролем не існує"))
                }
            )

    }

    def index = Action {
        Redirect(routes.HomeController.login())
    }

    def login = Action.async {
        implicit request => {
            val name = request.session.get("name")
            val surname = request.session.get("surname")
            val pw = request.session.get("password")
            def user = if (name.isDefined && surname.isDefined && pw.isDefined)
                          applicantsDAO.findApplicant(UserData(name.get, surname.get, pw.get))
                       else Future(None)

            user.map(x => if (x.isDefined) Ok(views.html.privatepage(x.get)) else Ok(views.html.login()))
        }
    }

    def register = Action.async {
        for {
        ds <- disciplineDAO.getAll
        fc <- faculteeDAO.getAll
        }
        yield
            Ok(views.html.registration(ds, fc))
    }

    def loginHandler = Action.async {
        implicit request =>
        userForm.form.bindFromRequest.fold(
            formWithErrors => {
                Future(BadRequest(views.html.error("formWithErrors")))
            },
            userData => {
                def user = applicantsDAO.findApplicant(userData)
                user.onComplete(x => println(x))
                user.map(x => if (x.isDefined){
                    Ok(views.html.privatepage(x.get))
                        .withSession("surname" -> userData.surname, "name" -> userData.name, "password" -> userData.password)
                }  else Ok(views.html.error("Користувача з таким іменем/паролем не існує")))

//                val user = applicantsDAO.findApplicant(userData)
//                user.map(x => if (x.isDefined)
//                    Ok(views.html.error("Користувача з таким іменем/паролем не існує"))
//
//                else
//                    Ok(views.html.privatepage(x, Facultees.getFaculteeName(user.get.facultee_id).get))
//                        .withSession("surname" -> userData.surname, "name" -> userData.name, "password" -> userData.password)
//            )
            }
        )
    }
    def registrationHandler = Action.async {

        implicit request =>
            regForm.form.bindFromRequest.fold(
                formWithErrors => {
                    Future(BadRequest(views.html.error("Cталася помилка, введіть корректні дані")))
                },
                RegData => {
                    if (RegData.passwordsignup != RegData.passwordsignup_confirm)
                        Future(Ok(views.html.error("Паролі не співпадають.")))
                    else{
                        val newApp = Applicant(0, RegData.name, RegData.surname, RegData.passwordsignup,
                            RegData.zno_ukr, RegData.zno_math, RegData.discipline3.toInt, RegData.zno_3rd,
                            RegData. att, RegData.facultee.toInt, None)
                        applicantsDAO.addApplicant(newApp)
                        Future(Redirect(routes.HomeController.login()))
                    }
                }
            )
    }

}

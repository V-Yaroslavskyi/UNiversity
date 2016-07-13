package controllers

import javax.inject._

import play.api.mvc._
import models._
// async handling response inject session config db

@Singleton
class HomeController @Inject() extends Controller {

    def enroll = Action{
        Applicants.enroll
        Redirect(routes.HomeController.adminAuth())
    }

    def adminIndex = Action{
        Ok(views.html.admin())
    }

    def adminAuth = Action{
        implicit request =>
            adminForm.form.bindFromRequest.fold(
                formWithErrors => {

                    BadRequest(views.html.error("formWithErrors"))
                },
                adminData => {
                    val user = Admins.findApplicant(adminData)
                    if (user.isEmpty){
                        Ok(views.html.error("Користувача з таким іменем/паролем не існує"))
                    }
                    else{
                        Ok(views.html.adminPanel(Applicants.appFac))
                    }
                }
            )

    }

    def index = Action {
        Redirect(routes.HomeController.login())
    }

    def login = Action {
        implicit request => {
            val name = request.session.get("name")
            val surname = request.session.get("surname")
            val pw = request.session.get("password")
            if (name.isDefined && surname.isDefined && pw.isDefined) {
                val user = Applicants.findApplicant(UserData(name.get, surname.get, pw.get))
                if (user.isDefined)
                    Ok(views.html.privatepage(user.get, Facultees.getFaculteeName(user.get.facultee_id).get))
                else
                    Ok(views.html.login())
            }
            else
                Ok(views.html.login())
        }
    }
    def register = Action {

        Ok(views.html.registration(Disciplines.getAll, Facultees.getAll))
    }

    def loginHandler = Action {
        implicit request =>
        userForm.form.bindFromRequest.fold(
            formWithErrors => {
                BadRequest(views.html.error("formWithErrors"))
            },
            userData => {
                val user = Applicants.findApplicant(userData)
                if (user.isEmpty){
                    Ok(views.html.error("Користувача з таким іменем/паролем не існує"))
                }
                else{
                    Ok(views.html.privatepage(user.get, Facultees.getFaculteeName(user.get.facultee_id).get))
                        .withSession("surname" -> userData.surname, "name" -> userData.name, "password" -> userData.password)
                }
            }
        )
    }
    def registrationHandler = Action {

        implicit request =>
            regForm.form.bindFromRequest.fold(
                formWithErrors => {
                    BadRequest(views.html.error("Cталася помилка, введіть корректні дані"))
                },
                RegData => {
                    if (RegData.passwordsignup != RegData.passwordsignup_confirm)
                        Ok(views.html.error("Паролі не співпадають."))
                    else{
                        val newApp = Applicant(0, RegData.name, RegData.surname, RegData.passwordsignup,
                            RegData.zno_ukr, RegData.zno_math, RegData.discipline3.toInt, RegData.zno_3rd,
                            RegData. att, RegData.facultee.toInt, None)
                        Applicants.addApplicant(newApp)
                        Redirect(routes.HomeController.login())
                    }
                }
            )
    }

}

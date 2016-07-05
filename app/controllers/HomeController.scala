package controllers

import javax.inject._

import play.api.mvc._
import models._


@Singleton
class HomeController @Inject() extends Controller {


    def index = Action {
        Redirect(routes.HomeController.login())
    }

    def login = Action {

        Ok(views.html.login())
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
                    println(user)
                    Ok(views.html.error("Користувача з таким іменем/паролем не існує"))
                }
                else{
                    Ok(views.html.privatepage(user.get, Facultees.getFaculteeName(user.get.facultee_id).get))
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

package models

import play.api.data.Forms._
import play.api.data.Form

case class UserData(name: String, surname:String, password:String)

object userForm {

    val form = Form(

        mapping(
            "name" -> nonEmptyText,
            "surname" -> nonEmptyText,
            "password" -> nonEmptyText
        )(UserData.apply)(UserData.unapply)

    )
}
case class RegData(name:String,
                   surname:String,
                   zno_ukr:Int,
                   zno_math:Int,
                   discipline3:String,
                   zno_3rd:Int,
                   att:Int,
                   passwordsignup:String,
                   passwordsignup_confirm:String,
                   facultee:String)

object regForm {

    val form = Form(
        mapping(
            "name" -> nonEmptyText,
            "surname" -> nonEmptyText,
            "zno_ukr" -> number(min = 100, max = 200),
            "zno_math" -> number(min = 100, max = 200),
            "discipline3" -> nonEmptyText,
            "zno_3rd" -> number(min = 100, max = 200),
            "att" -> number(min = 100, max = 200),
            "passwordsignup" -> nonEmptyText,
            "passwordsignup_confirm" -> nonEmptyText,
            "facultee" -> nonEmptyText
        )(RegData.apply)(RegData.unapply)
    )
}


case class Applicant(id: Int, name: String, surname: String, password: String,
                     zno_ukr: Int, zno_math: Int, zno_3rd_id: Int, zno_3rd_points: Int, attestate: Int,
                     facultee_id: Int, status: Option[Boolean])

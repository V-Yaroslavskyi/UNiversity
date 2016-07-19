package models


import play.api.data.Form
import play.api.data.Forms._

case class Admin(id:Int, login:String, password:String)

case class adminData(name: String, password:String)

object adminForm {

    val form = Form(

        mapping(
            "login" -> nonEmptyText,
            "password" -> nonEmptyText
        )(adminData.apply)(adminData.unapply)

    )
}

    


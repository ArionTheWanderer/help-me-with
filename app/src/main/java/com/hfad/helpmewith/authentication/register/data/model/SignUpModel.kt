package com.hfad.helpmewith.authentication.register.data.model

data class SignUpModel (
    var firstName: String,
    var lastName: String,
    var login: String,
    var password: String,
    var isTutor: Boolean,
    var signUpTutorsInfoModel: SignUpTutorsInfoModel? = null
)

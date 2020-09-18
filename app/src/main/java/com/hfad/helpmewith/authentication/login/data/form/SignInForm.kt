package com.hfad.helpmewith.authentication.login.data.form

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class SignInForm (
    @SerializedName("login")
    @Expose
    var login: String,
    @SerializedName("password")
    @Expose
    var password: String
)

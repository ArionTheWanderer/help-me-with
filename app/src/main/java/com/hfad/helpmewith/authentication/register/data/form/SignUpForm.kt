package com.hfad.helpmewith.authentication.register.data.form

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class SignUpForm (
    @SerializedName("firstName")
    @Expose
    val firstName: String,
    @SerializedName("lastName")
    @Expose
    val lastName: String,
    @SerializedName("password")
    @Expose
    val password: String,
    @SerializedName("login")
    @Expose
    val login: String,
    @SerializedName("isTutor")
    @Expose
    val isTutor: Boolean,
    @SerializedName("isNotRemote")
    @Expose
    val isNotRemote: Boolean,
    @SerializedName("city")
    @Expose
    val city: String?,
    @SerializedName("tutorsSubjects")
    @Expose
    val tutorsSubjects: MutableList<SignUpTutorsSubjectForm>? = null
)

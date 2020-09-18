package com.hfad.helpmewith.authentication.register.data.form

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class SignUpTutorsSubjectForm (
    @SerializedName("subject")
    @Expose
    val subject: String,
    @SerializedName("experience")
    @Expose
    val experience: Long,
    @SerializedName("hourlyFee")
    @Expose
    val hourlyFee: Long
)

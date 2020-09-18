package com.hfad.helpmewith.app.data.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class TokenResponse (
    @SerializedName("token")
    @Expose
    val token: String
)
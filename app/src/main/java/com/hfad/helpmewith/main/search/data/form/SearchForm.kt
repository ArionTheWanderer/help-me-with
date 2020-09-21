package com.hfad.helpmewith.main.search.data.form

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class SearchForm (
    @SerializedName("subject")
    @Expose
    val subject: String,
    @SerializedName("maxHourlyFee")
    @Expose
    val maxHourlyFee: Long?,
    @SerializedName("isNotRemote")
    @Expose
    val isNotRemote: Boolean,
    @SerializedName("city")
    @Expose
    val city: String?
)

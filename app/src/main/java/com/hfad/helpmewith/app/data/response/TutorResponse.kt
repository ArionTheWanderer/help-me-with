package com.hfad.helpmewith.app.data.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class TutorResponse (
    @SerializedName("userDto")
    @Expose
    val userInfo: UserInfoResponse,
    @SerializedName("isNotRemote")
    @Expose
    val isNotRemote: Boolean?,
    @SerializedName("city")
    @Expose
    val city: String?,
    @SerializedName("tutorsSubjects")
    @Expose
    val tutorsSubjects: List<TutorsSubjectResponse>?
)
data class UserInfoResponse (
    @SerializedName("id")
    @Expose
    val id: Long,
    @SerializedName("firstName")
    @Expose
    val firstName: String,
    @SerializedName("lastName")
    @Expose
    val lastName: String,
    @SerializedName("login")
    @Expose
    val login: String,
    @SerializedName("isTutor")
    @Expose
    val isTutor: Boolean,
    @SerializedName("rating")
    @Expose
    val rating: Double?,
    @SerializedName("reviewDtoList")
    @Expose
    val reviews: List<ReviewResponse>?
)

data class ReviewResponse (
    @SerializedName("text")
    @Expose
    val text: String,
    @SerializedName("rating")
    @Expose
    val rating: Long
)

data class TutorsSubjectResponse (
    @SerializedName("id")
    @Expose
    val id: Long,
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

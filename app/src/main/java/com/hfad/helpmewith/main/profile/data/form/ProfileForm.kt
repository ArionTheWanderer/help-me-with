package com.hfad.helpmewith.main.profile.data.form

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ProfileForm (
    @SerializedName("firstName")
    @Expose
    val firstName: String?,
    @SerializedName("lastName")
    @Expose
    val lastName: String?,
    @SerializedName("oldPassword")
    @Expose
    val oldPassword: String?,
    @SerializedName("newPassword")
    @Expose
    val newPassword: String?,
    @SerializedName("isTutor")
    @Expose
    val isTutor: Boolean?,
    @SerializedName("isNotRemote")
    @Expose
    val isNotRemote: Boolean?,
    @SerializedName("city")
    @Expose
    val city: String?,
    @SerializedName("tutorsSubjects")
    @Expose
    val tutorsSubjects: List<ProfileTutorsSubjectForm>?
)

data class ProfileTutorsSubjectForm (
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

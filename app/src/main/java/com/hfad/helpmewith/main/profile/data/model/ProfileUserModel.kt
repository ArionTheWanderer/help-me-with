package com.hfad.helpmewith.main.profile.data.model

data class ProfileUserModel (
    val firstName: String,
    val lastName: String,
    val oldPassword: String?,
    val newPassword: String?,
    val isTutor: Boolean
)

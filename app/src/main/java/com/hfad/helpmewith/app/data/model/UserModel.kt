package com.hfad.helpmewith.app.data.model

data class UserModel (
    val id: Long,
    val firstName: String,
    val lastName: String,
    val login: String,
    val isTutor: Boolean,
    val rating: Double?,
    val reviews: List<ReviewModel>?
)

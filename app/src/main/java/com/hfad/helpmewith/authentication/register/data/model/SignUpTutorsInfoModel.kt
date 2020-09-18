package com.hfad.helpmewith.authentication.register.data.model

data class SignUpTutorsInfoModel (
    var isNotRemote: Boolean?,
    var city: String?,
    var subjects: MutableList<SignUpTutorsSubjectModel>? = null
)

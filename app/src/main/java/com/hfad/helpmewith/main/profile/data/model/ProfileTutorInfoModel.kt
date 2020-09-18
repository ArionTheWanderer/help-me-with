package com.hfad.helpmewith.main.profile.data.model

data class ProfileTutorInfoModel (
    var isNotRemote: Boolean?,
    var city: String?,
    var tutorsSubjects: MutableList<ProfileTutorsSubjectModel>? = null
)

package com.hfad.helpmewith.main.profile.data.repository

import com.hfad.helpmewith.app.data.model.UserWrapperModel
import com.hfad.helpmewith.app.data.response.TutorResponse
import com.hfad.helpmewith.main.profile.data.model.ProfileUserWrapperModel

interface ProfileRepository {
    suspend fun sendEditRequest(profileUserWrapperModel: ProfileUserWrapperModel): UserWrapperModel?
    suspend fun getProfileInfo(): UserWrapperModel?
}

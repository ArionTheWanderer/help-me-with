package com.hfad.helpmewith.main.profile.data.repository

import com.hfad.helpmewith.app.data.model.UserWrapperModel
import com.hfad.helpmewith.main.profile.data.model.ProfileUserWrapperModel
import com.hfad.helpmewith.util.DataState
import kotlinx.coroutines.flow.Flow

interface ProfileRepository {
    suspend fun sendEditRequest(profileUserWrapperModel: ProfileUserWrapperModel): Flow<DataState<UserWrapperModel>>
    suspend fun getProfileInfo(): Flow<DataState<UserWrapperModel>>
    suspend fun deleteUserFromDB()
}

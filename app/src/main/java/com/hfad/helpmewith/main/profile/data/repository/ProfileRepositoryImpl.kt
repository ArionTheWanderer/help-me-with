package com.hfad.helpmewith.main.profile.data.repository

import com.hfad.helpmewith.app.data.mapper.TutorResponseMapper
import com.hfad.helpmewith.app.data.model.UserWrapperModel
import com.hfad.helpmewith.app.data.response.TutorResponse
import com.hfad.helpmewith.authentication.register.data.mapper.SignUpMapper
import com.hfad.helpmewith.authentication.register.data.network.SignUpService
import com.hfad.helpmewith.authentication.register.data.repository.SignUpRepository
import com.hfad.helpmewith.main.profile.data.mapper.ProfileFormMapper
import com.hfad.helpmewith.main.profile.data.model.ProfileUserWrapperModel
import com.hfad.helpmewith.main.profile.data.network.ProfileService
import com.hfad.helpmewith.util.SessionManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ProfileRepositoryImpl
@Inject constructor(
    private val profileService: ProfileService,
    private val tutorResponseMapper: TutorResponseMapper,
    private val profileFormMapper: ProfileFormMapper,
    private val sessionManager: SessionManager
): ProfileRepository {

    override suspend fun sendEditRequest(profileUserWrapperModel: ProfileUserWrapperModel): UserWrapperModel? {
        val profileForm = profileFormMapper.mapToEntity(profileUserWrapperModel)
        val profileResponse = withContext(Dispatchers.IO) {
            profileService.editProfile(profileForm)
        }

        return if (profileResponse.isSuccessful) {
            val tutorResponse = profileResponse.body()
            if (tutorResponse != null) {
                tutorResponseMapper.mapFromEntity(tutorResponse)
            } else {
                null
            }
        } else {
            null
        }
    }

    override suspend fun getProfileInfo(): UserWrapperModel? {
        val profileResponse = withContext(Dispatchers.IO) {
            profileService.getProfile()
        }

        return if (profileResponse.isSuccessful) {
            val tutorResponse = profileResponse.body()
            if (tutorResponse != null) {
                tutorResponseMapper.mapFromEntity(tutorResponse)
            } else {
                null
            }
        } else {
            null
        }

    }
}

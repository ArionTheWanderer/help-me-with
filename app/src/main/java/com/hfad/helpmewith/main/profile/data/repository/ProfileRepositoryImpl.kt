package com.hfad.helpmewith.main.profile.data.repository

import android.util.Log
import com.hfad.helpmewith.Constants
import com.hfad.helpmewith.app.data.db.dao.UserDao
import com.hfad.helpmewith.app.data.mapper.TutorCacheMapper
import com.hfad.helpmewith.app.data.mapper.TutorResponseMapper
import com.hfad.helpmewith.app.data.model.LoggedUser
import com.hfad.helpmewith.app.data.model.UserWrapperModel
import com.hfad.helpmewith.main.profile.data.mapper.ProfileFormMapper
import com.hfad.helpmewith.main.profile.data.model.ProfileUserWrapperModel
import com.hfad.helpmewith.main.profile.data.network.ProfileService
import com.hfad.helpmewith.util.DataState
import com.hfad.helpmewith.util.SessionManager
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ProfileRepositoryImpl
@Inject constructor(
    private val profileService: ProfileService,
    private val tutorResponseMapper: TutorResponseMapper,
    private val profileFormMapper: ProfileFormMapper,
    private val cacheMapper: TutorCacheMapper,
    private val userDao: UserDao,
    private val loggedUser: LoggedUser,
    private val sessionManager: SessionManager
): ProfileRepository {

    override suspend fun sendEditRequest(profileUserWrapperModel: ProfileUserWrapperModel): Flow<DataState<UserWrapperModel>> = flow {
        emit(DataState.Loading)
        delay(1000)
        val profileForm = profileFormMapper.mapToEntity(profileUserWrapperModel)
        try{
            val profileResponse = profileService.editProfile(profileForm)
            if (profileResponse.isSuccessful) {
                val tutorResponse = profileResponse.body()
                if (tutorResponse != null) {
                    val userModel = tutorResponseMapper.mapFromEntity(tutorResponse)
                    Log.d(Constants.PROFILE_TAG, "userModel: ${userModel.userInfo.id} + ${userModel.userInfo.firstName}")
                    val userId = userModel.userInfo.id
                    val user = cacheMapper.mapToEntity(userModel)
                    userDao.deleteUser(userId)
                    userDao.insertUser(user)
                    val cachedUser = userDao.getUser(userId)
                    if (cachedUser != null) {
                        val userModelFromDB = cacheMapper.mapFromEntity(cachedUser)
                        loggedUser.userWrapperModel = userModelFromDB
                        Log.d(Constants.PROFILE_TAG, "cachedUser: ${cachedUser.id} + ${cachedUser.firstName}")
                        emit(DataState.Success(userModelFromDB))
                    } else {
                        emit(DataState.Error("User has not been saved in DB"))
                    }
                } else {
                    emit(DataState.Error("Response body is empty"))
                }
            } else {
                emit(DataState.Error("Bad request"))
            }
        }catch (e: Exception){
            emit(DataState.Error(e.message ?: "error message is empty; error: ${e.cause.toString()}"))
        }
    }

    override suspend fun getProfileInfo(): Flow<DataState<UserWrapperModel>> = flow {
        emit(DataState.Loading)
        delay(1000)
        try{
            val profileResponse = profileService.getProfile()
            if (profileResponse.isSuccessful) {
                val tutorResponse = profileResponse.body()
                if (tutorResponse != null) {
                    val userModel = tutorResponseMapper.mapFromEntity(tutorResponse)
                    Log.d(Constants.PROFILE_TAG, "userModel: ${userModel.userInfo.id} + ${userModel.userInfo.firstName}")
                    val userId = userModel.userInfo.id
                    val user = cacheMapper.mapToEntity(userModel)
                    userDao.deleteUser(userId)
                    userDao.insertUser(user)
                    val cachedUser = userDao.getUser(userId)
                    if (cachedUser != null) {
                        val userModelFromDB = cacheMapper.mapFromEntity(cachedUser)
                        loggedUser.userWrapperModel = userModelFromDB
                        Log.d(Constants.PROFILE_TAG, "cachedUser: ${cachedUser.id} + ${cachedUser.firstName}")
                        emit(DataState.Success(userModelFromDB))
                    } else {
                        emit(DataState.Error("User has not been saved in DB"))
                    }
                } else {
                    emit(DataState.Error("Response body is empty"))
                }
            } else {
                emit(DataState.Error("Bad request"))
            }
        }catch (e: Exception){
            emit(DataState.Error(e.message ?: "error message is empty; error: ${e.cause.toString()}"))
        }
    }

    override suspend fun deleteUserFromDB() {
        val userWrapperModel = loggedUser.userWrapperModel
        if (userWrapperModel != null) {
            val userId = userWrapperModel.userInfo.id
            Log.d(
                Constants.PROFILE_TAG,
                "UserId from LoggedUser object: $userId"
            )
            val deletedRows = userDao.deleteUser(userId)
            Log.d(
                Constants.PROFILE_TAG,
                "User from db was deleted; number of deleted rows: $deletedRows"
            )
            loggedUser.userWrapperModel = null
        }
        Log.d(Constants.PROFILE_TAG, "Token value: ${sessionManager.fetchAuthToken()}")
        sessionManager.deleteToken()
        Log.d(Constants.PROFILE_TAG, "Token value: ${sessionManager.fetchAuthToken()}")
    }
}

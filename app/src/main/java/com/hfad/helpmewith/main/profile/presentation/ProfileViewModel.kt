package com.hfad.helpmewith.main.profile.presentation

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.hfad.helpmewith.app.data.model.TutorInfoModel
import com.hfad.helpmewith.app.data.model.UserModel
import com.hfad.helpmewith.app.data.model.UserWrapperModel
import com.hfad.helpmewith.app.data.response.TutorResponse
import com.hfad.helpmewith.main.profile.data.model.ProfileUserWrapperModel
import com.hfad.helpmewith.main.profile.data.qualifier.ProfileRepositoryMain
import com.hfad.helpmewith.main.profile.data.repository.ProfileRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class ProfileViewModel
@ViewModelInject
constructor(
    @ProfileRepositoryMain private val profileRepository: ProfileRepository,
    @Assisted private val savedStateHandle: SavedStateHandle
): ViewModel(){
    private val _userInfo = MutableLiveData<UserModel>()
    private val _tutorInfo = MutableLiveData<TutorInfoModel>()
    private val _isCorrectOldPassword = MutableLiveData<Boolean>()

    val userInfo: LiveData<UserModel>
        get() = _userInfo

    val tutorInfo: LiveData<TutorInfoModel>
        get() = _tutorInfo

    val isCorrectOldPassword: LiveData<Boolean>
        get() = _isCorrectOldPassword

    fun getProfile() = viewModelScope.launch {
        val userWrapperModel = profileRepository.getProfileInfo()
        if (userWrapperModel != null) {
            _userInfo.postValue(userWrapperModel.userInfo)
            _tutorInfo.postValue(userWrapperModel.tutorInfo)
        }
    }

    fun editProfile(profileUserWrapperModel: ProfileUserWrapperModel) = viewModelScope.launch {
        val userWrapperModel = profileRepository.sendEditRequest(profileUserWrapperModel)
        if (userWrapperModel != null) {
            _isCorrectOldPassword.postValue(true)
            _userInfo.postValue(userWrapperModel.userInfo)
            _tutorInfo.postValue(userWrapperModel.tutorInfo)
        } else {
            _isCorrectOldPassword.postValue(false)
        }
    }
}

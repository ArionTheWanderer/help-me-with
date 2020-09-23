package com.hfad.helpmewith.main.profile.presentation

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.hfad.helpmewith.app.data.model.UserWrapperModel
import com.hfad.helpmewith.main.profile.data.model.ProfileUserWrapperModel
import com.hfad.helpmewith.main.profile.data.qualifier.ProfileRepositoryMain
import com.hfad.helpmewith.main.profile.data.repository.ProfileRepository
import com.hfad.helpmewith.util.DataState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class ProfileViewModel
@ViewModelInject
constructor(
    @ProfileRepositoryMain private val profileRepository: ProfileRepository,
    @Assisted private val savedStateHandle: SavedStateHandle
): ViewModel(){
    private val _userWrapperInfo = MutableLiveData<DataState<UserWrapperModel>>()

    val userWrapperInfo: LiveData<DataState<UserWrapperModel>>
        get() = _userWrapperInfo

    fun getProfile() = viewModelScope.launch {
        profileRepository.getProfileInfo()
            .onEach { dataState ->
                _userWrapperInfo.value = dataState
            }.launchIn(viewModelScope)
    }

    fun editProfile(profileUserWrapperModel: ProfileUserWrapperModel) = viewModelScope.launch {
        profileRepository.sendEditRequest(profileUserWrapperModel)
            .onEach { dataState ->
                _userWrapperInfo.value = dataState
            }.launchIn(viewModelScope)
    }

    fun deleteUserFromDB() = viewModelScope.launch {
        profileRepository.deleteUserFromDB()
    }
}

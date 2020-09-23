package com.hfad.helpmewith.authentication.register.presentation

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.hfad.helpmewith.authentication.register.data.model.SignUpModel
import com.hfad.helpmewith.authentication.register.data.qualifier.SignUpRepositoryMain
import com.hfad.helpmewith.authentication.register.data.repository.SignUpRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class SignUpViewModel
@ViewModelInject
constructor(
    @SignUpRepositoryMain private val signUpRepository: SignUpRepository,
    @Assisted private val savedStateHandle: SavedStateHandle
): ViewModel(){
    private val _isAuthenticated = MutableLiveData<Boolean>()

    val isAuthenticated: LiveData<Boolean>
        get() = _isAuthenticated

    fun sendSignUpRequest(signUpModel: SignUpModel) = viewModelScope.launch {
        val result = signUpRepository.sendSignUpRequest(signUpModel)
        _isAuthenticated.postValue(result)
    }
}

package com.hfad.helpmewith.authentication.login.presentation

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.hfad.helpmewith.authentication.login.data.model.SignInModel
import com.hfad.helpmewith.authentication.login.data.qualifier.SignInRepositoryMain
import com.hfad.helpmewith.authentication.login.data.repository.SignInRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class SignInViewModel
@ViewModelInject
constructor(
    @SignInRepositoryMain private val signInRepository: SignInRepository,
    @Assisted private val savedStateHandle: SavedStateHandle
): ViewModel() {

    private val _isAuthenticated = MutableLiveData<Boolean>()

    val isAuthenticated: LiveData<Boolean>
        get() = _isAuthenticated

    fun postLogin(login: String, password: String) = viewModelScope.launch {
            val signInModel = SignInModel(login, password)
            val result = signInRepository.postLogin(signInModel)
            _isAuthenticated.postValue(result)
    }
}

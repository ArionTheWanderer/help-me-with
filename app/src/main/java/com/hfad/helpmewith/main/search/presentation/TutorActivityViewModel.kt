package com.hfad.helpmewith.main.search.presentation

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.hfad.helpmewith.app.data.model.UserWrapperModel
import com.hfad.helpmewith.main.search.data.qualifier.SearchRepositoryMain
import com.hfad.helpmewith.main.search.data.repository.SearchRepository
import com.hfad.helpmewith.util.DataState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class TutorActivityViewModel
@ViewModelInject
constructor(
    @SearchRepositoryMain private val searchRepository: SearchRepository,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _dataState: MutableLiveData<DataState<UserWrapperModel>> = MutableLiveData()

    val dataState: LiveData<DataState<UserWrapperModel>>
        get() = _dataState

    fun findTutor(id: Long) = viewModelScope.launch {
        searchRepository.getTutor(id)
            .onEach { dataState ->
                _dataState.value = dataState
            }.launchIn(viewModelScope)
    }
}

package com.hfad.helpmewith.main.search.data.repository

import com.hfad.helpmewith.app.data.model.UserWrapperModel
import com.hfad.helpmewith.main.search.data.model.SearchModel
import com.hfad.helpmewith.util.DataState
import kotlinx.coroutines.flow.Flow

interface SearchRepository {
    suspend fun getTutors(searchModel: SearchModel): Flow<DataState<List<UserWrapperModel>>>

    suspend fun getTutor(id: Long): Flow<DataState<UserWrapperModel>>
}

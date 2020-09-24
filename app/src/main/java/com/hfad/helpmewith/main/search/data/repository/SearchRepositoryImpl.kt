package com.hfad.helpmewith.main.search.data.repository

import com.hfad.helpmewith.app.data.mapper.TutorResponseMapper
import com.hfad.helpmewith.app.data.model.UserWrapperModel
import com.hfad.helpmewith.main.search.data.mapper.SearchFormMapper
import com.hfad.helpmewith.main.search.data.model.SearchModel
import com.hfad.helpmewith.main.search.data.network.SearchService
import com.hfad.helpmewith.util.DataState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SearchRepositoryImpl
@Inject
constructor(
private val searchService: SearchService,
private val tutorsResponseMapper: TutorResponseMapper,
private val searchFormMapper: SearchFormMapper
): SearchRepository {

    override suspend fun getTutors(searchModel: SearchModel): Flow<DataState<List<UserWrapperModel>>> = flow {
        emit(DataState.Loading)
        delay(1000)
        try {
            val tutorsForm = searchFormMapper.mapToEntity(searchModel)
            val tutorsResponse = searchService.findTutors(tutorsForm.subject,
                tutorsForm.maxHourlyFee, tutorsForm.isNotRemote, tutorsForm.city)
            if (tutorsResponse.isSuccessful) {
                val tutorsBody = tutorsResponse.body()
                if (tutorsBody != null) {
                    val tutorsModel = tutorsBody.let {
                        tutorsResponseMapper.mapFromEntityList(
                            it
                        )
                    }
                    emit(DataState.Success(tutorsModel))
                } else {
                    emit(DataState.Error("Body is empty"))
                }
            } else {
                emit(DataState.Error("Unsuccessful response"))
            }
        } catch (e: Exception){
            emit(DataState.Error(e.message ?: "error message is empty; error: ${e.cause.toString()}"))
        }
    }

    override suspend fun getTutor(id: Long): Flow<DataState<UserWrapperModel>>  = flow {
        emit(DataState.Loading)
        delay(1000)
        try {
            val tutorResponse = withContext(Dispatchers.IO) {
                searchService.findTutor(id)
            }
            if (tutorResponse.isSuccessful) {
                val tutorResponseBody = tutorResponse.body()
                if (tutorResponseBody != null) {
                    val tutorModel = tutorResponseBody.let {
                        tutorsResponseMapper.mapFromEntity(
                            it
                        )
                    }
                    emit(DataState.Success(tutorModel))
                } else {
                    emit(DataState.Error("Body is empty"))
                }
            } else {
                emit(DataState.Error("Bad request"))
            }
        } catch (e: Exception){
            emit(DataState.Error(e.message ?: "error message is empty; error: ${e.cause.toString()}"))
        }
    }
}

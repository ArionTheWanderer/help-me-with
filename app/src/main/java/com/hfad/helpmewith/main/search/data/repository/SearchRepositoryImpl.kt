package com.hfad.helpmewith.main.search.data.repository

import com.hfad.helpmewith.app.data.mapper.TutorResponseMapper
import com.hfad.helpmewith.app.data.model.UserWrapperModel
import com.hfad.helpmewith.main.search.data.mapper.SearchFormMapper
import com.hfad.helpmewith.main.search.data.model.SearchModel
import com.hfad.helpmewith.main.search.data.network.SearchService
import com.hfad.helpmewith.util.DataState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
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
    }
}

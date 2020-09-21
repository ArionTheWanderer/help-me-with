package com.hfad.helpmewith.main.search.data.network

import com.hfad.helpmewith.app.data.response.TutorResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchService {

    @GET("/tutors")
    suspend fun findTutors(
        @Query("subject") subject: String,
        @Query("maxHourlyFee") maxHourlyFee: Long?,
        @Query("isNotRemote") isNotRemote: Boolean,
        @Query("city") city: String?,
    ): Response<List<TutorResponse>>
}

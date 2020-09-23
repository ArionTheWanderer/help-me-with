package com.hfad.helpmewith.main.search.data.network

import com.hfad.helpmewith.Constants
import com.hfad.helpmewith.app.data.response.TutorResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface SearchService {

    @GET(Constants.URL_SEARCH_TUTORS)
    suspend fun findTutors(
        @Query("subject") subject: String,
        @Query("maxHourlyFee") maxHourlyFee: Long?,
        @Query("isNotRemote") isNotRemote: Boolean,
        @Query("city") city: String?,
    ): Response<List<TutorResponse>?>

    @GET(Constants.URL_SEARCH_TUTOR)
    suspend fun findTutor(
        @Path("id") id: Long
    ): Response<TutorResponse?>
}

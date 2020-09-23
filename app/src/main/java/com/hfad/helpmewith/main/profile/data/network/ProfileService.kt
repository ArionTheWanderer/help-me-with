package com.hfad.helpmewith.main.profile.data.network

import com.hfad.helpmewith.Constants
import com.hfad.helpmewith.app.data.response.TutorResponse
import com.hfad.helpmewith.main.profile.data.form.ProfileForm
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT

interface ProfileService {
    @PUT(Constants.URL_PROFILE)
    suspend fun editProfile(@Body profileForm: ProfileForm): Response<TutorResponse>

    @GET(Constants.URL_PROFILE)
    suspend fun getProfile(): Response<TutorResponse>
}

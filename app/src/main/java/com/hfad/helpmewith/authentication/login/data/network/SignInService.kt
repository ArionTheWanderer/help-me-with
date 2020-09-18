package com.hfad.helpmewith.authentication.login.data.network

import com.hfad.helpmewith.app.data.response.TokenResponse
import com.hfad.helpmewith.authentication.login.data.form.SignInForm
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface SignInService {
    @POST("/signIn")
    suspend fun signIn(@Body signInForm: SignInForm): Response<TokenResponse>
}

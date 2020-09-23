package com.hfad.helpmewith.authentication.register.data.network

import com.hfad.helpmewith.Constants
import com.hfad.helpmewith.app.data.response.TokenResponse
import com.hfad.helpmewith.authentication.register.data.form.SignUpForm
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface SignUpService {
    @POST(Constants.URL_SIGN_UP)
    suspend fun signUp(@Body signUpForm: SignUpForm): Response<TokenResponse>
}
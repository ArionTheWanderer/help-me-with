package com.hfad.helpmewith.authentication.register.data.repository

import com.hfad.helpmewith.authentication.register.data.model.SignUpModel

interface SignUpRepository {
    suspend fun sendSignUpRequest(signUpModel: SignUpModel): Boolean
}

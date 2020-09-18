package com.hfad.helpmewith.authentication.login.data.repository

import com.hfad.helpmewith.authentication.login.data.model.SignInModel

interface SignInRepository {
    suspend fun postLogin(signInModel: SignInModel): Boolean
}

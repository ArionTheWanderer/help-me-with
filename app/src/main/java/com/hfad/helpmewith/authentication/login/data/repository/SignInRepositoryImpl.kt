package com.hfad.helpmewith.authentication.login.data.repository

import android.util.Log
import com.hfad.helpmewith.Constants
import com.hfad.helpmewith.authentication.login.data.network.SignInService
import com.hfad.helpmewith.authentication.login.data.mapper.SignInMapper
import com.hfad.helpmewith.authentication.login.data.model.SignInModel
import com.hfad.helpmewith.util.SessionManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SignInRepositoryImpl
@Inject constructor(
    private val signInService: SignInService,
    private val signInMapper: SignInMapper,
    private val sessionManager: SessionManager
): SignInRepository {

    override suspend fun postLogin(signInModel: SignInModel): Boolean {
        val signInForm = signInMapper.mapToEntity(signInModel)
        val signInResponse = withContext(Dispatchers.IO) {
            signInService.signIn(signInForm)
        }

        return if (signInResponse.isSuccessful) {
            val token = signInResponse.body()!!.token
            sessionManager.saveAuthToken(token)
            Log.d(Constants.TOKEN_LOG, "TOKEN VALUE: ${sessionManager.fetchAuthToken()}")
            true
        } else {
            false
        }

        /*val response = withContext(Dispatchers.IO) {
            googleService.getGoogle()
        }

        return response.toString().isNotEmpty()*/
    }
}

package com.hfad.helpmewith.authentication.register.data.repository

import android.util.Log
import com.hfad.helpmewith.Constants
import com.hfad.helpmewith.authentication.register.data.mapper.SignUpMapper
import com.hfad.helpmewith.authentication.register.data.model.SignUpModel
import com.hfad.helpmewith.authentication.register.data.network.SignUpService
import com.hfad.helpmewith.util.SessionManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SignUpRepositoryImpl
@Inject constructor(
    private val signUpService: SignUpService,
    private val signUpMapper: SignUpMapper,
    private val sessionManager: SessionManager
): SignUpRepository {

    override suspend fun sendSignUpRequest(signUpModel: SignUpModel): Boolean {
        val signUpForm = signUpMapper.mapToEntity(signUpModel)
        return try {
            val signUpResponse = withContext(Dispatchers.IO) {
                signUpService.signUp(signUpForm)
            }
            if (signUpResponse.isSuccessful) {
                val token = signUpResponse.body()!!.token
                sessionManager.saveAuthToken(token)
                Log.d(Constants.TOKEN_LOG, "TOKEN VALUE: ${sessionManager.fetchAuthToken()}")
                true
            } else {
                false
            }
        } catch (e: Exception) {
            false
        }

    }
}

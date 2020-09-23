package com.hfad.helpmewith.util

import android.content.Context
import android.content.SharedPreferences
import com.hfad.helpmewith.Constants

class SessionManager
    constructor(context: Context) {
    private var prefs: SharedPreferences = context.getSharedPreferences(Constants.TOKEN_PREFS, Context.MODE_PRIVATE)

    fun saveAuthToken(token: String) {
        val editor = prefs.edit()
        editor.putString(Constants.TOKEN_VALUE, token)
        editor.apply()
    }

    fun fetchAuthToken(): String? {
        return prefs.getString(Constants.TOKEN_VALUE, null)
    }

    fun deleteToken() {
        val editor = prefs.edit()
        editor.remove(Constants.TOKEN_VALUE)
        editor.apply()
    }
}

package org.sopt.play.data

import android.content.Context

class LocalStorage(context: Context) {

    private val preferences =
        context.applicationContext.getSharedPreferences(
            "play_local_storage",
            Context.MODE_PRIVATE,
        )

    fun saveLogin(userName: String) {
        preferences.edit()
            .putBoolean("is_logged_in", true)
            .putString("user_name", userName)
            .apply()
    }

    fun isLoggedIn(): Boolean {
        return preferences.getBoolean("is_logged_in", false)
    }

    fun getUserName(): String {
        return preferences.getString("user_name", "").orEmpty()
    }

    fun saveAuthorizationCode(authorizationCode: String) {
        preferences.edit()
            .putString("authorization_code", authorizationCode)
            .apply()
    }

    fun getAuthorizationCode(): String {
        return preferences.getString("authorization_code", "").orEmpty()
    }
}
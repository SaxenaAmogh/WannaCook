package com.example.wannacook

import android.content.Context
import android.content.SharedPreferences

class UserManager(context: Context) {

    companion object {
        private const val PREF_NAME = "UserPrefs"
        private const val KEY_USER_NAME = "userName"
        private const val KEY_EMAIL = "email"
    }

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    private val editor: SharedPreferences.Editor = sharedPreferences.edit()

    // Save user information
    fun saveUserInfo(userName: String, email: String) {
        editor.putString(KEY_USER_NAME, userName)
        editor.putString(KEY_EMAIL, email)
        editor.apply() // Save data asynchronously
    }

    // Retrieve user information
    fun getUserInfo(): Map<String, String?> {
        val userName = sharedPreferences.getString(KEY_USER_NAME, null)
        val email = sharedPreferences.getString(KEY_EMAIL, null)
        return mapOf(
            "userName" to userName,
            "email" to email
        )
    }

    // Clear user information
    fun clearUserInfo() {
        editor.clear()
        editor.apply() // Clear all data asynchronously
    }
}

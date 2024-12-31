package com.example.wannacook

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class UserManager(context: Context) {

    companion object {
        private const val PREF_NAME = "UserPrefs"
        private const val KEY_USER_NAME = "userName"
        private const val KEY_EMAIL = "email"
        private const val KEY_LIKED = "liked"
    }

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    private val editor: SharedPreferences.Editor = sharedPreferences.edit()
    private val gson = Gson()

    // Save user information
    fun saveUserInfo(userName: String, email: String, liked: List<Int>) {
        editor.putString(KEY_USER_NAME, userName)
        editor.putString(KEY_EMAIL, email)
        editor.putString(KEY_LIKED, gson.toJson(liked))
        editor.apply() // Save data asynchronously
    }

    // Update liked list
    fun updateLikes(newValue: Int? = null, removeValue: Int? = null) {
        // Get the current list of likes
        val likedJson = sharedPreferences.getString(KEY_LIKED, null)
        val liked: MutableList<Int> = if (likedJson != null) {
            gson.fromJson(likedJson, object : TypeToken<MutableList<Int>>() {}.type)
        } else {
            mutableListOf()
        }

        // Append the new value if provided
        if (newValue != null) {
            liked.add(newValue)
        }

        // Remove the specific value if provided
        if (removeValue != null) {
            liked.remove(removeValue)
        }

        // Save the updated list back to SharedPreferences
        editor.putString(KEY_LIKED, gson.toJson(liked))
        editor.apply()
    }


    // Retrieve user information
    fun getUserInfo(): Map<String, Any?> {
        val userName = sharedPreferences.getString(KEY_USER_NAME, null)
        val email = sharedPreferences.getString(KEY_EMAIL, null)
        val likedJson = sharedPreferences.getString(KEY_LIKED, null)
        val liked: List<Int> = if (likedJson != null) {
            gson.fromJson(likedJson, object : TypeToken<List<Int>>() {}.type)
        } else {
            emptyList()
        }
        return mapOf(
            "userName" to userName,
            "email" to email,
            "liked" to liked
        )
    }

    // Retrieve liked list as List<Int>
    fun getLikedList(): List<Int> {
        val likedJson = sharedPreferences.getString(KEY_LIKED, null)
        return if (likedJson != null) {
            gson.fromJson(likedJson, object : TypeToken<List<Int>>() {}.type)
        } else {
            emptyList()
        }
    }

    // Clear user information
    fun clearUserInfo() {
        editor.clear()
        editor.apply() // Clear all data asynchronously
    }
}

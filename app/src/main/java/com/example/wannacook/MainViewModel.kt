package com.example.wannacook

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore

class MainViewModel : ViewModel() {
    private val _recipes = mutableStateOf<List<Recipe>>(emptyList())
    val recipes: State<List<Recipe>> get() = _recipes

    // State to handle loading status
    private val _isLoading = mutableStateOf(true)
    val isLoading: State<Boolean> get() = _isLoading

    init {
        fetchRecipes()
    }

    private fun fetchRecipes() {
        val firestore = FirebaseFirestore.getInstance()
        val recipesRef = firestore.collection("recipes")

        recipesRef.get().addOnSuccessListener { querySnapshot ->
            val fetchedRecipes = querySnapshot.documents.mapNotNull { document ->
                val id = (document.get("id") as? Long)?.toInt() ?: return@mapNotNull null
                val images = document.get("images") as? List<String> ?: emptyList()
                val ingredients = document.get("ingredients") as? List<String> ?: emptyList()
                val likes = (document.get("likes") as? Long)?.toInt() ?: 0
                val name = document.get("name") as? String ?: "Unknown"
                val recipe = document.get("recipe") as? List<String> ?: emptyList()
                val type = document.get("type") as? String ?: "Unknown"

                Recipe(id, images, ingredients, likes, name, recipe, type)
            }
            _recipes.value = fetchedRecipes
            _isLoading.value = false
        }.addOnFailureListener { e ->
            println("Error fetching recipes: ${e.message}")
            _isLoading.value = false
        }
    }
}
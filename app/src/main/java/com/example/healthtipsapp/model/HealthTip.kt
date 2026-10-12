package com.example.healthtipsapp.model

enum class TipCategory {
    Nutrition,
    Fitness,
    Sleep,
    Hydration,
    Stress,
    Wellness
}

data class HealthTip(
    val id: Int,
    val title: String,
    val category: TipCategory,
    val description: String,
    val benefit: String,
    val duration: String,
    val difficulty: String,
    val isFavorite: Boolean = false
)

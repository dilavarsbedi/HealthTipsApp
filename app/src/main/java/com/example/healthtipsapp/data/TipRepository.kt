package com.example.healthtipsapp.data

import com.example.healthtipsapp.model.HealthTip
import com.example.healthtipsapp.model.TipCategory

object TipRepository {
    fun getTips(): List<HealthTip> = listOf(
        HealthTip(
            id = 1,
            title = "Start Your Morning With Water",
            category = TipCategory.Hydration,
            description = "Drink a glass of water within 30 minutes of waking up to rehydrate your body and improve focus for the morning.",
            benefit = "Better hydration, improved energy, and more alertness.",
            duration = "1 minute",
            difficulty = "Easy"
        ),
        HealthTip(
            id = 2,
            title = "Add More Greens to Meals",
            category = TipCategory.Nutrition,
            description = "Include leafy greens, vegetables, or fruit in at least one meal each day to support immunity and overall health.",
            benefit = "Higher nutrient intake and stronger heart health.",
            duration = "5 minutes",
            difficulty = "Easy"
        ),
        HealthTip(
            id = 3,
            title = "Take a 10-Minute Walk",
            category = TipCategory.Fitness,
            description = "A short walk after lunch or dinner can boost circulation, improve mood, and support weight management.",
            benefit = "Improved cardiovascular health and reduced stress.",
            duration = "10 minutes",
            difficulty = "Easy"
        ),
        HealthTip(
            id = 4,
            title = "Protect Your Sleep Routine",
            category = TipCategory.Sleep,
            description = "Set a regular bedtime and dim lights 30 minutes before sleep to help your body prepare for rest.",
            benefit = "Better recovery, memory, and emotional balance.",
            duration = "30 minutes",
            difficulty = "Medium"
        ),
        HealthTip(
            id = 5,
            title = "Practice a 2-Minute Breathing Reset",
            category = TipCategory.Stress,
            description = "Take slow breaths for a few minutes during stressful moments to calm your nervous system and lower tension.",
            benefit = "Lower anxiety and better focus.",
            duration = "2 minutes",
            difficulty = "Easy"
        ),
        HealthTip(
            id = 6,
            title = "Choose Whole Foods Over Snacks",
            category = TipCategory.Nutrition,
            description = "Replace ultra-processed snacks with fruit, yogurt, nuts, or whole grains for steady energy and better satiety.",
            benefit = "More balanced nutrition and reduced sugar crashes.",
            duration = "5 minutes",
            difficulty = "Easy"
        ),
        HealthTip(
            id = 7,
            title = "Stretch Your Neck and Shoulders",
            category = TipCategory.Wellness,
            description = "Do gentle stretches after desk work to relieve tightness and improve posture during the day.",
            benefit = "Less muscle tension and improved comfort.",
            duration = "3 minutes",
            difficulty = "Easy"
        ),
        HealthTip(
            id = 8,
            title = "Get Outside for Morning Light",
            category = TipCategory.Wellness,
            description = "Spend 10 minutes outdoors in daylight to support your circadian rhythm and improve alertness.",
            benefit = "Better sleep quality and mood regulation.",
            duration = "10 minutes",
            difficulty = "Easy"
        ),
        HealthTip(
            id = 9,
            title = "Build a Strength Routine",
            category = TipCategory.Fitness,
            description = "Include simple strength exercises 2-3 times per week to improve bone density, muscle function, and metabolism.",
            benefit = "Greater strength, balance, and long-term vitality.",
            duration = "20 minutes",
            difficulty = "Medium"
        ),
        HealthTip(
            id = 10,
            title = "Limit Screen Time Before Bed",
            category = TipCategory.Sleep,
            description = "Reduce late-night phone or tablet use to improve melatonin production and help you fall asleep faster.",
            benefit = "Improved sleep quality and better recovery.",
            duration = "15 minutes",
            difficulty = "Medium"
        ),
        HealthTip(
            id = 11,
            title = "Keep a Water Bottle Nearby",
            category = TipCategory.Hydration,
            description = "Having water within reach encourages regular sipping and reduces dehydration during busy days.",
            benefit = "Better focus, energy, and skin hydration.",
            duration = "1 minute",
            difficulty = "Easy"
        ),
        HealthTip(
            id = 12,
            title = "Plan One Healthy Habit Each Day",
            category = TipCategory.Wellness,
            description = "Choose one realistic health action every day to build lasting routines without feeling overwhelmed.",
            benefit = "Consistency and sustainable lifestyle change.",
            duration = "5 minutes",
            difficulty = "Easy"
        )
    )
}

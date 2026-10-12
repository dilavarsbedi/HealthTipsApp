package com.example.healthtipsapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.healthtipsapp.ui.HealthTipsApp
import com.example.healthtipsapp.ui.theme.HealthTipsAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HealthTipsAppTheme {
                HealthTipsApp()
            }
        }
    }
}

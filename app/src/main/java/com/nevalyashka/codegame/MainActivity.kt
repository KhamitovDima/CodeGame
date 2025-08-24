package com.nevalyashka.codegame

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.nevalyashka.codegame.presentation.features.game.GameScreen
import com.nevalyashka.codegame.presentation.theme.CodeGameTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CodeGameTheme {
                GameScreen()
            }
        }
    }
}

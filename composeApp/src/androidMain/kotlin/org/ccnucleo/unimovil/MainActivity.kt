package org.ccnucleo.unimovil
// composeApp/src/androidMain/kotlin/.../MainActivity.kt
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import org.ccnucleo.unimovil.App

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // Ya no le pasamos ningún módulo
            App()
        }
    }
}
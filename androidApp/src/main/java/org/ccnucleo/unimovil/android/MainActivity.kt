// C:/Users/jose/Desktop/Proyectos/UNIMovil/androidApp/src/main/java/org/ccnucleo/unimovil/android/MainActivity.kt

package org.ccnucleo.unimovil.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import org.ccnucleo.unimovil.App // <-- Importa tu Composable principal desde :composeApp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // Llama al Composable definido en el módulo compartido
            App()
        }
    }
}

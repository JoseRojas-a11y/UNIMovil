package di

// shared/src/androidMain/kotlin/di/PlatformModule.kt
import com.liftric.kvault.KVault
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val platformModule = module {
    // Android requiere el contexto para crear el EncryptedSharedPreferences
    single { KVault(androidContext(), "ScrapingVault") }
}
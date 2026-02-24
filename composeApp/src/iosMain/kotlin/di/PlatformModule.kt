package di

// shared/src/iosMain/kotlin/di/PlatformModule.kt
import com.liftric.kvault.KVault
import org.koin.dsl.module

val platformModule = module {
    // iOS usa el Keychain directamente, no necesita contexto
    single { KVault("ScrapingVault") }
}
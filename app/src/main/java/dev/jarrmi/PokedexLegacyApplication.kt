package dev.jarrmi

import android.app.Application
import dev.jarrmi.pokedex.di.appModule
import org.koin.core.context.startKoin

class PokedexLegacyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(appModule)
        }
    }
}

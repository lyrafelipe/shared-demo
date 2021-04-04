package com.lyrafelipe.shareddemo

import android.app.Application
import com.lyrafelipe.shareddemo.remote.remoteModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class SharedDemoApp : Application() {

    private val sharedDemoModules = listOf(
        remoteModule,
    )

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@SharedDemoApp)
            modules(sharedDemoModules)
        }
    }
}


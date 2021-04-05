package com.lyrafelipe.shareddemo

import android.app.Application
import com.lyrafelipe.shareddemo.remote.remoteModule
import com.lyrafelipe.shareddemo.repos.reposModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext
import org.koin.core.context.startKoin

class SharedDemoApp : Application() {

    private val sharedDemoModules = listOf(
        remoteModule,
        reposModule
    )

    override fun onCreate() {
        super.onCreate()

        GlobalContext.getOrNull() ?: startKoin {
            androidLogger()
            androidContext(this@SharedDemoApp)
            modules(sharedDemoModules)
        }
    }
}

package br.com.emesistemas.topk.app

import android.app.Application
import br.com.emesistemas.topk.di.*
import com.bumptech.glide.Glide
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(
                listOf(
                    databaseModule,
                    daoModule,
                    apiModule,
                    repositoryModule,
                    viewModelModule,
                    uiModule
                )
            )
        }
    }

    override fun onTrimMemory(level: Int) {
        super.onTrimMemory(level)
        Glide.get(this).clearMemory()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        Glide.get(this).clearMemory()
    }
}
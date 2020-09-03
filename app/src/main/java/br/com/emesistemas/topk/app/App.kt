package br.com.emesistemas.topk.app

import android.app.Application
import android.content.Context
import br.com.emesistemas.topk.di.*
import com.bumptech.glide.Glide
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        context = this
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

    companion object {

        private lateinit var context: Context

        fun getMockRepos(): String {
            return try {
                val inputStream = context.assets.open("repo_list_success.json")
                inputStreamToString(inputStream)
            } catch (e: IOException) {
                throw RuntimeException(e)
            }
        }

        private fun inputStreamToString(
            inputStream: InputStream
        ): String {
            val builder = StringBuilder()
            val reader = InputStreamReader(inputStream, "UTF-8")
            reader.readLines().forEach {
                builder.append(it)
            }
            return builder.toString()
        }
    }
}
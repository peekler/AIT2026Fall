package hu.ait.lifecycledemo

import android.app.Application
import android.util.Log

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Log.d("TAG_LIFE", "Application created")


    }
}
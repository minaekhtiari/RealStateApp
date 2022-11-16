package com.example.realestateapp
import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class Application:Application() {


    companion object {
        //var prefs: SharedPreferences? = null
    }

    override fun onCreate() {
        super.onCreate()

      //  prefs= PreferenceManager.getDefaultSharedPreferences(applicationContext)
    }
}
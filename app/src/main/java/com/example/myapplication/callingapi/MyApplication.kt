package com.example.myapplication.callingapi

import android.app.Application
import com.orhanobut.hawk.Hawk
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Hawk.init(this).build()

        // Initialize Timber
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree()) // Debug tree for development builds
        } else {
            Timber.plant(ReleaseTree()) // Custom tree for production builds
        }
    }


    private class ReleaseTree : Timber.Tree() {
        override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
            if (priority == android.util.Log.ERROR || priority == android.util.Log.WARN) {
                // Log ERRORs and WARNs to a crash reporting tool like Firebase
            }
        }
    }

}

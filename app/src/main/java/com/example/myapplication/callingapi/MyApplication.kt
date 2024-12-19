package com.example.myapplication.callingapi

import android.app.Application

import com.orhanobut.hawk.Hawk
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Hawk.init(this).build()
    }

}

package com.example.cypresssoftproject.app

import android.app.Application
import android.content.Context
import com.example.cypresssoftproject.utils.PrefUtils

class AppController : Application() {
    lateinit var objSharedPref: PrefUtils
    private val TAG = "AppController"

    override fun onCreate() {
        super.onCreate()
        objSharedPref = PrefUtils(this@AppController)
        instance = this
    }

    companion object {
        lateinit var instance: AppController

        fun getContext(): Context {
            return instance
        }
    }

    fun getAppContext(): AppController {
        return instance
    }

}



package com.example.cypresssoftproject.app

import android.app.Application
import android.content.Context
import com.example.cypresssoftproject.utils.ConnectivityReceiver

class AppController : Application(){
    override fun onCreate() {
        super.onCreate()
        instance = this
    }
    companion object {
        lateinit var instance: AppController

    }

    fun setConnectivityListener(listener: ConnectivityReceiver.ConnectivityReceiverListener) {
        ConnectivityReceiver.connectivityReceiverListener = listener
    }
}
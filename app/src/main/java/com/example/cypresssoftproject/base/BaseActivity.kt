package com.example.cypresssoftproject.base

import android.R
import android.annotation.SuppressLint
import android.app.*
import android.content.Context
import android.content.ContextWrapper
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import com.android.volley.toolbox.NetworkImageView
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import com.example.cypresssoftproject.database.CypressSoftDatabase
import com.example.cypresssoftproject.model.images.DashboardImagesResponse
import com.example.cypresssoftproject.service.RetrofitService
import com.example.cypresssoftproject.utils.NetworkHelper
import com.example.cypresssoftproject.utils.PrefUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.*
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
import java.util.*
import com.android.volley.toolbox.ImageRequest

import com.android.volley.RequestQueue
import com.android.volley.Response


open class BaseActivity : DataBindingActivity() {
    val TAG = "ET--" + javaClass.simpleName
    open lateinit var objSharedPref: PrefUtils

    private lateinit var mActivity: Activity

    lateinit var retrofitService: RetrofitService
    lateinit var networkHelper: NetworkHelper
    lateinit var db: CypressSoftDatabase

    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBaseComponants(this)
        retrofitService = RetrofitService.getInstance()
        networkHelper = NetworkHelper(this)
        db = CypressSoftDatabase.getDatabaseData(this)
    }

    private fun initBaseComponants(activityBase: BaseActivity) {
        objSharedPref = PrefUtils(this@BaseActivity)
        mActivity = this@BaseActivity
        globalActivity = this@BaseActivity

    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var globalActivity: Activity
    }

    override fun attachBaseContext(newBase: Context) {
        val sPrefs = PrefUtils(newBase)
        // val sPrefs = newBase.getSharedPreferences("HaramainSharedPref", Context.MODE_PRIVATE)
        val languageCode = sPrefs.getString("key_lang")
        super.attachBaseContext(newBase)
        setApplicationLanguage("en")
//        ContextWrapper.wrap(newBase, locale)
    }

    private fun setApplicationLanguage(newLanguage: String?) {
        val activityRes = resources
        val activityConf = activityRes.configuration
        val newLocale = Locale(newLanguage)
        activityConf.setLocale(newLocale)

        activityRes.updateConfiguration(activityConf, activityRes.displayMetrics)
        val applicationRes: Resources = applicationContext.resources
        val applicationConf = applicationRes.configuration
        applicationConf.setLocale(newLocale)
        applicationRes.updateConfiguration(applicationConf, applicationRes.displayMetrics)
    }


    override fun onDestroy() {
        super.onDestroy()
        logD("objSharedPref", "onDestroy")
    }

    fun progressBarTouchable(touchable: Boolean) {
        if (touchable)
            window.clearFlags(
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
            )
        else
            window.setFlags(
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
            )
    }

    fun logI(tag: String, msg: String) {
        Log.i(TAG, "$tag: $msg")
    }

    fun logE(tag: String, msg: String) {
        Log.e(TAG, "$tag: $msg")
    }

    fun logD(tag: String, msg: String) {
        Log.d(TAG, "$tag: $msg")
    }

    fun logW(tag: String, msg: String) {
        Log.w(TAG, "$tag: $msg")
    }

    fun getImageUri(bitmap: Bitmap): Uri {
        // Get the context wrapper
        val wrapper = ContextWrapper(this)

        // Initialize a new file instance to save bitmap object
        var file = wrapper.getDir("Images", Context.MODE_PRIVATE)
        file = File(file, "${UUID.randomUUID()}.jpg")

        try {
            // Compress the bitmap and save in jpg format
            val stream: OutputStream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
            stream.flush()
            stream.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return Uri.parse(file.absolutePath)
    }
}
package com.example.cypresssoftproject.design.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.example.cypresssoftproject.R
import com.example.cypresssoftproject.base.BaseActivity
import com.example.cypresssoftproject.databinding.ActivitySplashBinding
import com.example.cypresssoftproject.design.dashboard.DashboardActivity

class SplashActivity : BaseActivity() {
    val binding: ActivitySplashBinding by binding(R.layout.activity_splash)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.lifecycleOwner = this

        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, DashboardActivity::class.java))
            finishAffinity()

        }, 2000)
    }
}
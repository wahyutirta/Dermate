package com.example.dermate.ui.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.dermate.MainActivity
import com.example.dermate.R
import com.example.dermate.databinding.ActivitySplashBinding
import com.example.dermate.ui.welcomeslider.WelcomeSliderActivity
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashActivity : AppCompatActivity() {

    private lateinit var binding : ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        GlobalScope.launch {
            delay(2000L)
            startActivity(Intent(this@SplashActivity,WelcomeSliderActivity::class.java))
            finish()
        }
    }
}
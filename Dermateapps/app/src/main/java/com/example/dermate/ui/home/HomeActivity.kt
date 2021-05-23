package com.example.dermate.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.dermate.R
import com.example.dermate.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.bottomNavView.apply {
            background = null
            menu.getItem(1).isEnabled = false
        }
    }
}
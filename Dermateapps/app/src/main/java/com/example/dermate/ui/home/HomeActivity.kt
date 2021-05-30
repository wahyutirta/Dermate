package com.example.dermate.ui.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.dermate.databinding.ActivityHomeBinding
import com.example.dermate.ui.pickimage.ImagePickerActivity

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
        binding.openClassifier.setOnClickListener {
            startActivity(Intent(this@HomeActivity,ImagePickerActivity::class.java))
        }
    }
}
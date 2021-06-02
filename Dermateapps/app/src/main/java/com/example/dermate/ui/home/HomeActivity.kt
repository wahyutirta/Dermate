package com.example.dermate.ui.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.dermate.R
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

        supportActionBar?.apply {
            displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
            setDisplayShowCustomEnabled(true)
            setCustomView(R.layout.title_action_bar)
            elevation = 10f
        }


        val navController = findNavController(R.id.nav_host)
        val appBarConfiguration = AppBarConfiguration(setOf(R.id.home, R.id.setting))
        binding.bottomAppBar.setupWithNavController(navController,appBarConfiguration)
        binding.bottomNavView.setupWithNavController(navController)

        binding.openClassifier.setOnClickListener {
            startActivity(Intent(this@HomeActivity, ImagePickerActivity::class.java))
        }

    }

}
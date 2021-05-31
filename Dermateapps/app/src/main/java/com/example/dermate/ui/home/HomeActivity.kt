package com.example.dermate.ui.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.dermate.R
import com.example.dermate.databinding.ActivityHomeBinding
import com.example.dermate.ui.pickimage.ImagePickerActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

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

        //val bottomNavView: BottomNavigationView = binding.bottomNavView
        val navController = findNavController(R.id.nav_host)
        val appBarConfiguration = AppBarConfiguration(setOf(R.id.home, R.id.setting))
        //setupActionBarWithNavController(navController, appBarConfiguration)
        binding.bottomAppBar.setupWithNavController(navController,appBarConfiguration)
        binding.bottomNavView.setupWithNavController(navController)

        binding.openClassifier.setOnClickListener {
            startActivity(Intent(this@HomeActivity, ImagePickerActivity::class.java))
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {

        }
        return super.onOptionsItemSelected(item)
    }
}
package com.example.dermate.ui.welcomeslider

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.dermate.MainActivity
import com.example.dermate.PreferenceManager
import com.example.dermate.R
import com.example.dermate.databinding.ActivityWelcomeSliderBinding
import com.example.dermate.ui.welcomeslider.pagefragment.FirstPageFragment
import com.example.dermate.ui.welcomeslider.pagefragment.SecondPageFragment
import com.example.dermate.ui.welcomeslider.pagefragment.ThirdPageFragment

class WelcomeSliderActivity : AppCompatActivity() {

    private lateinit var binding : ActivityWelcomeSliderBinding
    private val fragmentList  = ArrayList<Fragment>()
    private lateinit var preferenceManager :PreferenceManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /*
        //remove this part when developing app
        preferenceManager = PreferenceManager(this)
        if (!preferenceManager.isFirstTimeLaunch()){
            startMainActivity()
            finish()
        }
         */

        binding = ActivityWelcomeSliderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = SliderAdapter(this)
        binding.viewPagerIntro.adapter = adapter

        fragmentList.addAll(listOf(
            FirstPageFragment(),SecondPageFragment(),ThirdPageFragment()
        ))
        adapter.setFragmentList(fragmentList)
        binding.indicatorLayout.apply {
            setIndicatorCount(adapter.itemCount)
            selectCurrentPosition(0)
        }
        binding.tvNext.setOnClickListener(clickListener)
        binding.tvSkip.setOnClickListener(clickListener)
        register()
    }

    private val clickListener = View.OnClickListener {
        when(it.id){
            R.id.tv_next ->{
                val position = binding.viewPagerIntro.currentItem
                if (position<fragmentList.lastIndex){
                    binding.viewPagerIntro.currentItem =position +1
                }else{
                    preferenceManager.setIsFirstTimeLaunch(false)
                    startMainActivity()
                }
            }
            R.id.tv_skip->{
                preferenceManager.setIsFirstTimeLaunch(false)
                startMainActivity()
            }
        }
    }

    private fun startMainActivity() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    private fun register(){
        binding.apply {
            viewPagerIntro.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
                override fun onPageSelected(position: Int) {
                    indicatorLayout.selectCurrentPosition(position)
                    if (position<fragmentList.lastIndex){
                        tvSkip.visibility = View.VISIBLE
                        tvNext.text = "Next"

                    }else{
                        tvSkip.visibility = View.GONE
                        tvNext.text = "Get Started"
                    }

                }
            })
        }
    }
}
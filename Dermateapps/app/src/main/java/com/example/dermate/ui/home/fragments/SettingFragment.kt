package com.example.dermate.ui.home.fragments

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.transition.AutoTransition
import androidx.transition.TransitionManager
import com.example.dermate.R
import com.example.dermate.databinding.FragmentSettingBinding
import com.example.dermate.utils.PreferenceManager


class SettingFragment : Fragment() {

    private var _binding: FragmentSettingBinding? = null
    private val binding get() = _binding!!

    private lateinit var appPref: PreferenceManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSettingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        appPref = PreferenceManager(requireContext())
        binding.switchDarkMode.isChecked = appPref.isDarkMode()

        binding.switchDarkMode.setOnCheckedChangeListener { _, isChecked ->
            when (isChecked) {
                true -> {
                    appPref.setDarkMode(true)

                    binding.apply {
                        darkModeMessage.visibility = View.VISIBLE
                        systemMessage.visibility = View.VISIBLE
                    }
                }
                false -> {
                    appPref.setDarkMode(false)
                    binding.apply {
                        darkModeMessage.visibility = View.VISIBLE
                        systemMessage.visibility = View.VISIBLE
                    }
                }

                }
            }
            binding.changeLanguage.setOnClickListener {
                val setting = Intent(Settings.ACTION_LOCALE_SETTINGS)
                startActivity(setting)
                onPause()
            }
            binding.collapseExpand.setOnClickListener {
                if (binding.hiddenLayout.visibility == View.VISIBLE) {

                    // The transition of the hiddenView is carried out
                    //  by the TransitionManager class.
                    // Here we use an object of the AutoTransition
                    // Class to create a default transition.
                    TransitionManager.beginDelayedTransition(
                        binding.baseCardview,
                        AutoTransition()
                    )
                    binding.hiddenLayout.visibility = View.GONE
                    binding.collapseExpand.setImageResource(R.drawable.ic_collapsed)
                } else {
                    TransitionManager.beginDelayedTransition(
                        binding.baseCardview,
                        AutoTransition()
                    )
                    binding.hiddenLayout.visibility = View.VISIBLE
                    binding.collapseExpand.setImageResource(R.drawable.ic_expanded)
                }
            }
        }


    }
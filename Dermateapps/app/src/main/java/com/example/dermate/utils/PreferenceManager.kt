@file:Suppress("SpellCheckingInspection")

package com.example.dermate.utils

import android.content.Context
import android.content.SharedPreferences

class PreferenceManager(context: Context) {

    companion object{
        private const val PREF_NAME = "introslider"
        private const val FIRST_TIME_LAUNCH = "IsFirstTimeLaunch"
        private const val PRIVATE_MODE = 0
    }


    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(PREF_NAME,PRIVATE_MODE)
    private val editor : SharedPreferences.Editor =sharedPreferences.edit()

    fun setIsFirstTimeLaunch(state: Boolean){
        editor.putBoolean(FIRST_TIME_LAUNCH,state)
        editor.commit()
    }
    fun isFirstTimeLaunch():Boolean{
        return sharedPreferences.getBoolean(FIRST_TIME_LAUNCH,true)
    }


}
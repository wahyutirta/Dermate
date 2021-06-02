package com.example.dermate.ui.welcomeslider

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class SliderAdapter(fragment : FragmentActivity) : FragmentStateAdapter(fragment) {
    private val fragmentList = ArrayList<Fragment>()

    override fun getItemCount(): Int =fragmentList.size

    override fun createFragment(position: Int): Fragment = fragmentList[position]

    fun setFragmentList(list : List<Fragment>){
        fragmentList.clear()
        fragmentList.addAll(list)
        notifyDataSetChanged()
    }
}
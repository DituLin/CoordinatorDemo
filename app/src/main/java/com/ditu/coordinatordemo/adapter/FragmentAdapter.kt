package com.ditu.coordinatordemo.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.ditu.coordinatordemo.fragment.NewsFragment

/**
 *=======================================
 *
 * Created by Ditu on 4/7/21
 *=======================================
 */
class FragmentAdapter(fragmentManager: FragmentManager,val list: List<String>) : FragmentPagerAdapter(fragmentManager) {

    override fun getCount(): Int  =  list.size

    override fun getItem(position: Int): Fragment {
       return NewsFragment.newInstance(list[position])
    }
}
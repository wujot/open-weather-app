package com.husar.openweather.adapter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.husar.openweather.R
import com.husar.openweather.ui.SearchCityFragment
import com.husar.openweather.ui.SearchCoordinatorsFragment

class SearchLocationPagerAdapter(private val context: Context?, fm: FragmentManager): FragmentStatePagerAdapter(fm) {

    private val fragmentList = listOf(
        SearchCityFragment.newInstance(),
        SearchCoordinatorsFragment.newInstance()
    )

    override fun getItem(position: Int): Fragment = fragmentList[position]

    override fun getCount(): Int = fragmentList.size

    override fun getPageTitle(position: Int): String? =
        context?.resources?.getStringArray(R.array.fragment_titles)?.get(position)
}
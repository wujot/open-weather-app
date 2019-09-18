package com.husar.openweather.ui


import android.os.Bundle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.husar.openweather.R
import com.husar.openweather.adapter.SearchLocationPagerAdapter
import kotlinx.android.synthetic.main.fragment_location_add.*


class LocationSearchFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_location_add, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fragmentManager?.let {
            val fragmentAdapter = SearchLocationPagerAdapter(context, it)
            pager_main.adapter = fragmentAdapter
            tab_main.setupWithViewPager(pager_main)
        }
    }
}

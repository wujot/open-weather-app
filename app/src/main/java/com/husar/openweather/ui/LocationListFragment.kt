package com.husar.openweather.ui


import android.os.Bundle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.husar.openweather.adapter.WeatherListAdapter
import com.husar.openweather.databinding.FragmentLocationListBinding
import com.husar.openweather.model.WeatherResponse
import com.husar.openweather.utility.reObserve
import com.husar.openweather.viewmodel.LocationListViewModel
import kotlinx.android.synthetic.main.fragment_location_list.*
import org.koin.android.viewmodel.ext.android.viewModel

class LocationListFragment : Fragment() {

    private lateinit var binding: FragmentLocationListBinding
    private val viewModel: LocationListViewModel by viewModel()
    private val listAdapter = WeatherListAdapter(arrayListOf())

    private val buttonAddObserver = Observer<Boolean> {isClicked ->
        if (isClicked) {
            val action = LocationListFragmentDirections.actionLocationListFragmentToLocationAddFragment()
            Navigation.findNavController(binding.buttonAddLocation).navigate(action)
        }
    }

    private val weathersObserver = Observer<List<WeatherResponse>> { list ->
        list?.let {
            listAdapter.updateWeatherList(it)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLocationListBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.buttonAddObservable.reObserve(this, buttonAddObserver)
        viewModel.weathersObservable.reObserve(this, weathersObserver)
        viewModel.refresh()

        weatherlList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = listAdapter
        }
    }

    override fun onResume() {
        super.onResume()

        viewModel.refresh()
    }
}

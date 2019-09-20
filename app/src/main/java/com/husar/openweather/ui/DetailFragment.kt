package com.husar.openweather.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import com.husar.openweather.R
import com.husar.openweather.data.model.WeatherRecord
import com.husar.openweather.databinding.FragmentDetailBinding
import com.husar.openweather.utility.reObserve
import com.husar.openweather.viewmodel.DetailViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class DetailFragment : Fragment() {

    var weather: WeatherRecord? = null
    private val viewModel: DetailViewModel by viewModel()
    private lateinit var binding: FragmentDetailBinding

    private val loadingObserver = Observer<Boolean> {isLoading ->
        binding.loadingLayout.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private val weatherObserver = Observer<WeatherRecord> { data ->
        binding.weather = data
        Toast.makeText(context, "Current weather successfully updated.", Toast.LENGTH_SHORT).show()
    }

    private val loadErrorObserver = Observer<Boolean> {isError ->
        Toast.makeText(context, "Problems occurred.\nPlease check your internet connection\nand try again.", Toast.LENGTH_SHORT).show()
    }

    private val deleteWeatherObserver = Observer<Boolean> { isDelete ->
        if (isDelete) {
            Toast.makeText(context, "Current weather location had been successfully deleted.", Toast.LENGTH_SHORT).show()
            val action = DetailFragmentDirections.actionDetailFragmentToLocationListFragment()
            Navigation.findNavController(binding.cancelItemAction).navigate(action)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.loadingObservable.reObserve(this, loadingObserver)
        viewModel.loadErrorObservable.reObserve(this, loadErrorObserver)
        viewModel.weatherObservable.reObserve(this, weatherObserver)
        viewModel.deleteWeatherObservable.reObserve(this, deleteWeatherObserver)

        arguments?.let {
            weather = DetailFragmentArgs.fromBundle(it).weather
            viewModel.currentWeather.value = weather
            binding.weather = weather
        }

        binding.refreshLayout.setOnRefreshListener {
            binding.loadingLayout.visibility = View.VISIBLE
            viewModel.refresh()
            binding.refreshLayout.isRefreshing = false
        }
    }
}

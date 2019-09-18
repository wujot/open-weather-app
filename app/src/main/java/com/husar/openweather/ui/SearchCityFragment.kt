package com.husar.openweather.ui


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.husar.openweather.databinding.FragmentSearchCityBinding
import com.husar.openweather.model.WeatherResponse
import com.husar.openweather.utility.reObserve
import com.husar.openweather.viewmodel.SearchCityViewModel
import kotlinx.android.synthetic.main.fragment_search_city.*
import org.koin.android.viewmodel.ext.android.viewModel


class SearchCityFragment : Fragment() {

    companion object {
        fun newInstance(): SearchCityFragment {
            return SearchCityFragment()
        }
    }

    private lateinit var binding: FragmentSearchCityBinding
    private val viewModel: SearchCityViewModel by viewModel()
    var weather: WeatherResponse? = null

    private val loadingObserver = Observer<Boolean> {isLoading ->
        binding.loadingLayout.visibility = if (isLoading) View.VISIBLE else View.GONE
        if (isLoading) {
            loadErrorView.visibility = View.GONE
            cardView.visibility = View.GONE
        }
    }

    private val loadErrorObserver = Observer<Boolean> {isError ->
        loadErrorView.visibility = if (isError) View.VISIBLE else View.GONE
        Toast.makeText(context, "Problems occurred.\nPlease check your internet connection\nand try again.", Toast.LENGTH_SHORT).show()
    }

    private val weatherObserver = Observer<WeatherResponse> { data ->
        data?.let {
            weather = data
            binding.weather = weather
            cardView.visibility = View.VISIBLE
            Toast.makeText(context, "Current weather successfully added.", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchCityBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.loadingObservable.reObserve(this, loadingObserver)
        viewModel.loadErrorObservable.reObserve(this, loadErrorObserver)
        viewModel.weatherObservable.reObserve(this, weatherObserver)
    }
}

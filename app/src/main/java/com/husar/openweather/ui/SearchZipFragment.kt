package com.husar.openweather.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import com.husar.openweather.data.model.WeatherRecord
import com.husar.openweather.databinding.FragmentSearchZipBinding
import com.husar.openweather.utility.reObserve
import com.husar.openweather.viewmodel.SearchZipViewModel
import kotlinx.android.synthetic.main.fragment_search_zip.*
import org.koin.android.viewmodel.ext.android.viewModel

class SearchZipFragment : Fragment() {

    companion object {
        fun newInstance(): SearchZipFragment {
            return SearchZipFragment()
        }
    }

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

    private val weatherObserver = Observer<WeatherRecord> { data ->
        data?.let {
            weather = data
            binding.weather = weather
            cardView.visibility = View.VISIBLE
            Toast.makeText(context, "Current weather successfully added.", Toast.LENGTH_SHORT).show()
        }
    }

    private lateinit var binding: FragmentSearchZipBinding
    private val viewModel: SearchZipViewModel by viewModel()
    var weather: WeatherRecord? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchZipBinding.inflate(inflater, container, false)
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

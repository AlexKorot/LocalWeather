package com.GriVlad

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.GriVlad.databinding.FragmentFrontSideBinding
import com.GriVlad.databinding.FragmentWeatherBinding


class WeatherFragment : BaseFragment(){
   lateinit var binding: FragmentWeatherBinding
   private val dataModel:DataModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding= FragmentWeatherBinding.inflate(inflater)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvTime.text=TimeManager.getCurrentTime()
        val act=requireActivity() as MainActivity
        dataModel.weatherResponse.observe(act,{weatherResponse->
            binding.apply {
            tvCity.text=weatherResponse.name!!.toString()+"("+weatherResponse.sys!!.country+")"
             tvTemperature.text= weatherResponse.main!!.temp.toInt().toString()+"\u00B0"
             tvDescription.text=weatherResponse.weather[0].description
                Log.e("myLog","$weatherResponse.weather[0].description")
             tvSunRise.text=TimeManager.epochConvector(weatherResponse.sys!!.sunrise)
             tvSunSet.text=TimeManager.epochConvector(weatherResponse.sys!!.sunset)
             tvHuminity.text=weatherResponse.main!!.humidity.toString()
             tvPresure.text=weatherResponse.main!!.pressure.toString()
             tvWind.text=weatherResponse.wind!!.speed.toString()
        }

         val icon=weatherResponse.weather[0].icon
          iconSelect(icon!!)
        })

    }
  fun iconSelect(icon:String) {
        when (icon) {
            "01d" -> binding.imageViewIcon.setImageResource(R.drawable.ic__01d)
            "02d" -> binding.imageViewIcon.setImageResource(R.drawable.ic__02d)
            "03d" -> binding.imageViewIcon.setImageResource(R.drawable.ic__3d)
            "04d" -> binding.imageViewIcon.setImageResource(R.drawable.ic__4d)
            "09d" -> binding.imageViewIcon.setImageResource(R.drawable.ic__09d)
            "10d" -> binding.imageViewIcon.setImageResource(R.drawable.ic__10d)
            "11d" -> binding.imageViewIcon.setImageResource(R.drawable.ic__11d)
            "13d" -> binding.imageViewIcon.setImageResource(R.drawable.ic__13d)
            "01n" -> binding.imageViewIcon.setImageResource(R.drawable.ic__01n)
            "02n" -> binding.imageViewIcon.setImageResource(R.drawable.ic__02n)
            "03n" -> binding.imageViewIcon.setImageResource(R.drawable.ic__03n)
            "04n" -> binding.imageViewIcon.setImageResource(R.drawable.ic__04n)
            "09n" -> binding.imageViewIcon.setImageResource(R.drawable.ic__09n)
            "10n" -> binding.imageViewIcon.setImageResource(R.drawable.ic__10n)
            "11n" -> binding.imageViewIcon.setImageResource(R.drawable.ic__11n)
            "13n" -> binding.imageViewIcon.setImageResource(R.drawable.ic__13n)
        }
        Log.d("myLog", "$icon")
    }

    companion object {

        @JvmStatic
        fun newInstance() = WeatherFragment()
            }
    }

package com.GriVlad

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class DataModel :ViewModel(){
     val weatherResponse:MutableLiveData<WeatherResponse> by
            lazy { MutableLiveData<WeatherResponse>() }
}
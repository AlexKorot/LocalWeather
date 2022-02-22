package com.GriVlad

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Looper
import android.provider.Settings
import android.util.Log
import android.view.View

import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat

import com.GriVlad.databinding.ActivityMainBinding
import com.google.android.gms.location.*
import com.google.android.gms.location.FusedLocationProviderClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {



    var permissionHelper = PermissionHelper(this)
    lateinit var mFusedLocationClient: FusedLocationProviderClient
    private var weatherData: TextView? = null
    var lat: String? = null
    var lon: String? = null
    lateinit var binding:ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        binding.tvTime.text=TimeManager.getCurrentTime()
        getLastLocation()
        binding.button.setOnClickListener { getCurrentData() }



    }


    internal fun getCurrentData() {
        binding.cardVievDetail.visibility=View.VISIBLE
        binding.cardDet.visibility=View.VISIBLE
        val retrofit = Retrofit.Builder()
            .baseUrl(BaseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(WeatherService::class.java)
        val call = service.getCurrentWeatherData(lat.toString(), lon.toString(), units, AppId, lang)
        call.enqueue(object : Callback<WeatherResponse> {
            override fun onResponse(
                call: Call<WeatherResponse>,
                response: Response<WeatherResponse>
            ) {
                if (response.code() == 200) {
                    val weatherResponse = response.body()!!
                    binding.apply{
                    tvCity.text=
                        weatherResponse.name!!.toString()+"("+weatherResponse.sys!!.country+")"
                    tvTemperature.text=weatherResponse.main!!.temp.toInt().toString()+"\u00B0"
                    tvDescription.text=weatherResponse.weather[0].description
                    imSunrise.visibility=View.VISIBLE
                    tvSunRise.text=TimeManager.epochConvector(weatherResponse.sys!!.sunrise)
                    imSunset.visibility=View.VISIBLE
                    tvSunSet.text=TimeManager.epochConvector(weatherResponse.sys!!.sunset)
                    tvHuminity.text=weatherResponse.main!!.humidity.toString()
                    tvPresure.text=weatherResponse.main!!.pressure.toString()
                    tvWind.text=weatherResponse.wind!!.speed.toString()
                }

                    val icon=weatherResponse.weather[0].icon
                    iconSelect(icon!!)


                }

            }

            override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                weatherData!!.text = t.message
                Toast.makeText(this@MainActivity,"${t.message}",Toast.LENGTH_SHORT).show()
            }
        })
    }


    companion object {

        var BaseUrl = "http://api.openweathermap.org/"
        var AppId = "df3fd74e3565fd2f6f5039158ad93564"
        var lang = "ru"
        var units = "metric"
    }
    @SuppressLint("MissingPermission")
    private fun getLastLocation() {
        if (permissionHelper.checkPermissions()) {
            if (isLocationEnabled()) {

                mFusedLocationClient.lastLocation.addOnCompleteListener(this) { task ->
                    var location: Location? = task.result
                    if (location == null) {
                        requestNewLocationData()
                    } else {
                        lat = location.latitude.toString()
                        lon = location.longitude.toString()
                    }
                }
            } else {
                Toast.makeText(this, "Turn on location", Toast.LENGTH_LONG).show()
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(intent)
            }
        } else {
            requestPermissions()
        }
    }

    @SuppressLint("MissingPermission")
    private fun requestNewLocationData() {
        var mLocationRequest = LocationRequest()
        mLocationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        mLocationRequest.interval = 1000
        mLocationRequest.fastestInterval =1000
        mLocationRequest.numUpdates = 1

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        mFusedLocationClient!!.requestLocationUpdates(
            mLocationRequest, mLocationCallback,
            Looper.myLooper()!!
        )
    }

    private val mLocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            var mLastLocation: Location = locationResult.lastLocation
            lat = mLastLocation.latitude.toString()
            lon = mLastLocation.longitude.toString()
        }
    }

    private fun isLocationEnabled(): Boolean {
        var locationManager: LocationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }



    private fun requestPermissions() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION),
            PermissionHelper.PERMISSION_ID
        )
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PermissionHelper.PERMISSION_ID) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                getLastLocation()
            }
        }
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

}
package com.GriVlad

import android.util.Log
import java.text.SimpleDateFormat
import java.util.*

object TimeManager {
    const val DEF_TIME_FORMAT="HH:mm E dd L yyyy"
    fun getCurrentTime():String{

        val formatter= SimpleDateFormat(DEF_TIME_FORMAT, Locale.getDefault())
        return formatter.format(Calendar.getInstance().time)
    }
    fun epochConvector(epoch:Long):String {
        val date =
            SimpleDateFormat("HH:mm").format(java.util.Date (epoch * 1000)).toString()
        Log.e("myLog","$date")
        return date
    }
}
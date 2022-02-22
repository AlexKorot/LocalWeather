package com.GriVlad

import android.Manifest
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat

class PermissionHelper( act:MainActivity) {

          val act=act




    fun checkPermissions(): Boolean {
        if (ActivityCompat.checkSelfPermission(
                act,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                act,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            return true
        }
        return false
    }

    private fun requestPermissions() {
        ActivityCompat.requestPermissions(
            act,
            arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION),
            PERMISSION_ID
        )
    }


companion object{
    const val PERMISSION_ID = 42
}



}
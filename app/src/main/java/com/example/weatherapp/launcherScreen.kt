package com.example.weatherapp

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.location.LocationRequest
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.location.LocationRequest.Builder
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.*

class launcherScreen : AppCompatActivity() {
    lateinit var mfusedlocation : FusedLocationProviderClient
    private val myRequestCode = 101
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launcher_screen)
        
        mfusedlocation = LocationServices.getFusedLocationProviderClient(this)
        
        getLastLocation()

        
        Handler(Looper.getMainLooper()).postDelayed({
             var intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        },2000)
    }


    private fun getLastLocation() {
        if(checkPermission()){
            if(locationEnable()){
                mfusedlocation.lastLocation.addOnCompleteListener {
                    task ->
                    var location: Location? = task.result
                    if(location == null){
                        newLocation()
                    }else{

                       Handler(Looper.getMainLooper()).postDelayed({
                           val intent = Intent(this, MainActivity::class.java)
                           intent.putExtra("lat", location.latitude.toString())
                           intent.putExtra("long", location.longitude.toString())
                           startActivity(intent)
                           finish()
                       },2000)
                    }
                }
            }else{
                Toast.makeText(this, "Please turn On your GPS",Toast.LENGTH_LONG).show()
            }
        }else{
            requestPermission()
        }
    }

    @SuppressLint("MissingPermission")
    private fun newLocation() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {

            var locationRequest = Builder(1000L)
            locationRequest.setQuality(LocationRequest.QUALITY_HIGH_ACCURACY)
            locationRequest.setIntervalMillis(0)
            locationRequest.setMaxUpdateDelayMillis(0)
            locationRequest.setMaxUpdates(1)
            locationRequest.build()
        } else {
            var locationRequest = LocationRequest()
            locationRequest.priority = LocationRequest.QUALITY_HIGH_ACCURACY
            locationRequest.interval = 0
            locationRequest.fastestInterval = 0
            locationRequest.numUpdates = 1
        }

        mfusedlocation = LocationServices.getFusedLocationProviderClient(this)
        mfusedlocation.requestLocationUpdates(LocationRequest(),locationCallBack, Looper.myLooper())
    }
    private val locationCallBack = object:LocationCallback(){
        override fun onLocationResult(p0: LocationResult) {
            var lastLocation: Location? = p0.lastLocation
        }
    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION,android.Manifest.permission.ACCESS_COARSE_LOCATION), myRequestCode)
    }

    private fun checkPermission(): Boolean {
        if(
            ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED ||
            ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
        ) {
            return true
        }
        return false
    }
    private fun locationEnable(): Boolean {
        var locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == myRequestCode){
            if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                getLastLocation()
            }
        }
    }
}



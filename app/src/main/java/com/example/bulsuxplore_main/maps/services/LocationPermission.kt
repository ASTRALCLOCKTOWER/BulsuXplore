//package com.example.bulsuxplore_main.maps.services
//
//import android.Manifest
//import android.content.Intent
//import android.content.IntentSender
//import android.content.pm.PackageManager
//import android.util.Log
//import android.widget.TextView
//
//import androidx.core.app.ActivityCompat
//import androidx.core.content.ContextCompat
//import androidx.core.location.LocationManagerCompat.getCurrentLocation
//import androidx.fragment.app.Fragment
//import com.example.bulsuxplore_main.maps.NavigationFragment.Companion.REQUEST_LOCATION
//import com.google.android.gms.common.api.ApiException
//import com.google.android.gms.common.api.ResolvableApiException
//import com.google.android.gms.location.LocationRequest
//import com.google.android.gms.location.LocationServices
//import com.google.android.gms.location.LocationSettingsRequest
//import com.google.android.gms.location.LocationSettingsStatusCodes
//import com.google.android.gms.common.api.Response
//import com.google.android.gms.location.FusedLocationProviderClient
//import com.google.android.gms.location.LocationSettingsResponse
//
//// CLASS FUNCTION: TO REQUEST PERMISSION FOR DEVICE LOCATION ACCESS
//class LocationPermission(private val fragment: Fragment) {
//
//
//    lateinit var locationRequest: LocationRequest
//    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
//
//    // Request location permission
//    fun requestLocPermissions() {
//
//        if (ActivityCompat.checkSelfPermission(
//                fragment.requireActivity(),
//                Manifest.permission.ACCESS_FINE_LOCATION
//            )
//            == PackageManager.PERMISSION_GRANTED
//        ) {
//            //when permission granted: check gps
//            checkGPS()
//        } else {
//            //when permission is denied
//            ActivityCompat.requestPermissions(fragment.requireActivity(), arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),100)
//
//        }
//    }
//
//    private fun checkGPS() {
//        locationRequest = LocationRequest.create()
//        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
//        locationRequest.interval = 5000
//        locationRequest.fastestInterval = 2000
//
//        val builder = LocationSettingsRequest.Builder()
//            .addLocationRequest(locationRequest)
//
//        builder.setAlwaysShow(true)
//
//        val result = LocationServices.getSettingsClient(
//            fragment.requireActivity().applicationContext
//        )
//
//            .checkLocationSettings(builder.build())
//
//        result.addOnCompleteListener { task ->
//            try {
//                //when the GPS is on
//                val response = task.getResult(
//                    ApiException::class.java)
//
//            }catch(e : ApiException){
//                // when the GPS if off
//                e.printStackTrace()
//
//                when(e.statusCode){
//                    LocationSettingsStatusCodes.RESOLUTION_REQUIRED -> try {
//                        // send request to enable enable GPS
//                        val resolveApiException = e as ResolvableApiException
//                        resolveApiException.startResolutionForResult(fragment.requireActivity(),200)
//
//                    } catch (sendIntentException : IntentSender.SendIntentException){
//
//
//                    }
//                    LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE -> {
//                        // when setting is unavailable
//
//                   }
//                }
//            }
//        }
//    }
//
//}
//
//
//
//// WHAT TO CODE:
////1. prompt on display to ask permission to use the device location
////        - when yes: automatically turn on the user location
////        - when no: do not allow user to access map or maybe allow them to view offline bulsu map siguro
//
////2. Check GPS:
////   when gps off: ask permission &  pop out display the reason why it is needed
////      when permission denied: ask again for permission
//

package com.example.realestateapp

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.example.realestateapp.cons.Cons
import com.example.realestateapp.cons.Cons.PERMISSION_REQUEST_ACCESS_LOCATION
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint




@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val sharedLatitude = Cons.PREFERENCE_LAT
    private val sharedLongitude = Cons.PREFERENCE_L0NG
    private val sharedPreferences by lazy{
        getSharedPreferences("${BuildConfig.APPLICATION_ID}_sharedPreferences",
            Context.MODE_PRIVATE)
    }

    private lateinit var navController: NavController
    //private latitude var dataStore: DataStore<Preferences>
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Enable support for Splash Screen API for
        // proper Android 12+ support
        installSplashScreen()

        setContentView(R.layout.activity_main)
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)


        //Navigation
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.mainContainer) as NavHostFragment
        navController = navHostFragment.navController
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        setupWithNavController(bottomNavigationView, navController)


         getCurrentLocation()

        //  viewModel =   ViewModelProvider(this).get(MainViewModel::class.java)

//        viewModel.house.observe(this, Observer {
//        Log.d("DATA", it.get(0).city+"")
//        })


    }



    private fun getCurrentLocation() {

        if (checkPermissions()) {
            if (isLocationEnabled()) {
                if (ActivityCompat.checkSelfPermission(
                        this,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                        this,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED
                ) {

                    return
                }
                fusedLocationProviderClient.lastLocation.addOnCompleteListener(this) { task ->
                    val location: Location? = task.result
                    if (location == null) {

                    } else {

                       sharedPreferences.edit()
                           .putInt(sharedLatitude,location.latitude.toInt()).apply()

                        sharedPreferences.edit()
                            .putInt(sharedLongitude,location.longitude.toInt()).apply()




//                       lifecycleScope.launch {
//                        writeDouble(
//                             "LONG",location.longitude
//                           )
//                           writeDouble("LAT",location.latitude)
//
//
//
//                       }
                    }
                }
            } else {
                ///setting open here
                Toast.makeText(applicationContext, "Turn on location", Toast.LENGTH_LONG).show()
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(intent)
            }


        } else {

            ///request Permission here
            requestPermission()
        }

    }


    private fun checkPermissions(): Boolean {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
            == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            return true
        }
        return false
    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            ),
            PERMISSION_REQUEST_ACCESS_LOCATION
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            getCurrentLocation()
        }
    }

    private fun isLocationEnabled(): Boolean {
        val locationManager: LocationManager =
            getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                || locationManager.isProviderEnabled((LocationManager.NETWORK_PROVIDER))
    }


//    private suspend fun save(key: String, value: Double) {
//        val dataStoreKey = preferencesKey<String>(key)
//        dataStore.edit { settings ->
//            settings[dataStoreKey] = value.toString()
//        }
//    }

//    private suspend fun read(key: String): String? {
//        val dataStoreKey = preferencesKey<String>(key)
//        val preferences = dataStore.data.first()
//        return preferences[dataStoreKey]
//    }

//    suspend fun Context.writeDouble(key: String, value: Double) {
//        dataStore.edit { pref -> pref[doublePreferencesKey(key)] = value }
//    }



}



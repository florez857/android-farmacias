package com.example.misfarmacias

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import kotlinx.android.synthetic.main.activity_host.*

class HostActivity : AppCompatActivity() {

    val REQUEST_LOCATION_PERMISSION:Int=1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_host)

                setSupportActionBar(toolbar)
        // obtengo el navController de el host
        val navController = findNavController(R.id.fragment_host)
        //obtengo el bottom nav y lo configuro para que interacciones con el controlador del host

        val appBarConfiguration=AppBarConfiguration(setOf(R.id.ubicacionFragment,R.id.farmaciasFragment,R.id.deTurnoFragment))
        bottom_nav_view.setupWithNavController(navController)



//        setupActionBarWithNavController(navController,appBarConfiguration)
        enableMyLocation()
       // setupBottomNavMenu(navController)
    }



    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode){

            REQUEST_LOCATION_PERMISSION -> if (grantResults.size>0 && grantResults.get(0)== PackageManager.PERMISSION_GRANTED){
                enableMyLocation()
            }

        }
    }

    override fun onBackPressed() {
        super.onBackPressed()

        Log.d("boton","presionado atras")
    }

    fun enableMyLocation(){
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)  == PackageManager.PERMISSION_GRANTED){
           // mMap.uiSettings.isMyLocationButtonEnabled=true
           // mMap.isMyLocationEnabled=true
            // mMap.clear()

        }else{
            ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),REQUEST_LOCATION_PERMISSION)
        }


    }
    /*private fun setupBottomNavMenu(navController: NavController) {
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_nav_view)
        bottomNav?.setupWithNavController(navController)
    }*/
}

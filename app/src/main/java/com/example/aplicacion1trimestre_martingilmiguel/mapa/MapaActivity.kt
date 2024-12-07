package com.example.aplicacion1trimestre_martingilmiguel.mapa

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.commit
import com.example.aplicacion1trimestre_martingilmiguel.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions

class MapaActivity : AppCompatActivity(), OnMapReadyCallback {

    private val LOCATION_CODE = 1000
    private lateinit var mapa: GoogleMap
    private val locationPermissionRequest = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permisos ->
        if (permisos[Manifest.permission.ACCESS_COARSE_LOCATION] == true || permisos[Manifest.permission.ACCESS_FINE_LOCATION] == true) {
            gestionarLocalizacion()
        } else
            Toast.makeText(this, "No hay permisos de ubicación", Toast.LENGTH_SHORT).show()
    }

    private fun gestionarLocalizacion() {
        // Para comprobar si una var lateint esta inicializada ::
        if (!::mapa.isInitialized) return
        // Importar el manifest android
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
            && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mapa.isMyLocationEnabled = true
            mapa.uiSettings.isMyLocationButtonEnabled = true
        } else {
            // Vamos a pedir los permisos
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)
                || ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_COARSE_LOCATION)) {
                mostrarExplicacion()
            } else {
                escogerPermisos()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_mapa)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        iniciarFragmentoMapa()
    }

    private fun iniciarFragmentoMapa() {
        val fragment = SupportMapFragment()
        fragment.getMapAsync(this)
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            add(R.id.mapaFragment, fragment)
        }
    }

    override fun onMapReady(p0: GoogleMap) {
        mapa = p0
        mapa.uiSettings.isZoomControlsEnabled = true
        ponermarcadorEmpresa(LatLng(36.8504511,-2.4656217))  // Ubicación del marcador
        ponermarcadorEmpresa(LatLng(36.8504511,-20.4656217))  // Ubicación del marcador
        gestionarLocalizacion()
    }



    private fun ponermarcadorEmpresa(latLng: LatLng) {
        // Crear el marcador en la ubicación especificada
        val markerOptions = MarkerOptions()
            .position(latLng)
            .title("Nuestras Oficinas")

        // Añadir el marcador al mapa
        mapa.addMarker(markerOptions)

        // Mover la cámara para centrar el mapa en el marcador
        mapa.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f))  // El 15f es el nivel de zoom
    }

    //------permisos-------------------------------------------------------------------
    private fun mostrarExplicacion() {
        AlertDialog.Builder(this)
            .setTitle("Permiso requerido")
            .setMessage("Por favor, habilita los permisos de ubicación en la configuración de la aplicación.")
            .setNegativeButton("Cancelar") { dialog, _ ->
                dialog.dismiss()
            }
            .setCancelable(false)
            .setPositiveButton("Aceptar") { dialog, _ ->
                startActivity(Intent(Settings.ACTION_APPLICATION_SETTINGS))
                dialog.dismiss()
            }
            .create()
            .dismiss()
    }

    private fun escogerPermisos() {
        locationPermissionRequest.launch(
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION)
        )
    }
    //-------------------------------------
}

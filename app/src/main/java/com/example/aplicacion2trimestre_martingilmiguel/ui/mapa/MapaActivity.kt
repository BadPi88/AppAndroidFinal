package com.example.aplicacion2trimestre_martingilmiguel.ui.mapa

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
import com.example.aplicacion2trimestre_martingilmiguel.R
import com.example.aplicacion2trimestre_martingilmiguel.databinding.ActivityMapaBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions


class MapaActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var binding: ActivityMapaBinding

    private lateinit var mapa: GoogleMap
    private val locationPermissionRequest =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permisos ->
            if (permisos[Manifest.permission.ACCESS_COARSE_LOCATION] == true || permisos[Manifest.permission.ACCESS_FINE_LOCATION] == true) {
                gestionarLocalizacion()
            } else
                Toast.makeText(this, "No hay permisos de ubicación", Toast.LENGTH_SHORT).show()
        }

    private fun gestionarLocalizacion() {

        if (!::mapa.isInitialized) return
        // Importar el manifest android
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
            && ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            mapa.isMyLocationEnabled = true
            mapa.uiSettings.isMyLocationButtonEnabled = true
        } else {
            //Pedir los permisos
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
                || ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            ) {
                mostrarExplicacion()
            } else {
                escogerPermisos()
            }

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapaBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        listenerBotones()
        iniciarFragmentoMapa()
    }

    private fun listenerBotones() {
        binding.btnOfi1.setOnClickListener {
            zoomEnOficinaCentral()
        }

        binding.btnOfi2.setOnClickListener {
            zoomEnOficinaRegional()
        }
    }

    private fun zoomEnOficinaRegional() {
        val oficinaCentral = LatLng(40.449821, -3.701948)

        mapa.moveCamera(CameraUpdateFactory.newLatLngZoom(oficinaCentral, 15f))

    }

    private fun zoomEnOficinaCentral() {
        val oficinaRegional = LatLng(36.8504511, -2.4656217)
        mapa.moveCamera(CameraUpdateFactory.newLatLngZoom(oficinaRegional, 15f))
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
        ponermarcadorEmpresa(LatLng(36.8504511, -2.4656217))
        ponermarcadorEmpresa(LatLng(40.449821, -3.701948))
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
        mapa.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f))
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
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        )
    }
    //-------------------------------------
}

package com.example.aplicacion2trimestre_martingilmiguel.ui

import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.aplicacion2trimestre_martingilmiguel.R
import com.example.aplicacion2trimestre_martingilmiguel.databinding.ActivitySensorLuzBinding

class SensorLuzActivity : AppCompatActivity(), SensorEventListener {
    private lateinit var binding: ActivitySensorLuzBinding

    // Sensor de luz
    private lateinit var sensorManager: SensorManager
    private var sensorLuz: Sensor? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySensorLuzBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Inicializar SensorManager y obtener el sensor de luz
        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        sensorLuz = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)

        if (sensorLuz == null) {
            binding.tvResLuz.text = "Sensor de luz no disponible"
        }

        setListeners()
    }

    private fun setListeners() {
        binding.btnSalir.setOnClickListener {
            finish()
        }
    }

    override fun onResume() {
        super.onResume()
        sensorLuz?.let {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_NORMAL)
        }
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event?.sensor?.type == Sensor.TYPE_LIGHT) {
            val nivelLuz = event.values[0]

            val mensaje = when {
                nivelLuz < 50 -> "Necesitas más luz!"
                nivelLuz > 1000 -> "Hay demasiada luz!"
                else -> "¡Ideal para la vista!"
            }

            binding.tvResLuz.text = "Nivel de luz: ${String.format("%.2f", nivelLuz)} Lux\n$mensaje"

            // Ajustar brillo de la imagen del Sol
            ajustarBrilloImagen(nivelLuz)
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        // No se usa
    }

    // setScale(r, g, b, a) modifica la intensidad de RGB y a es la transparencia).
    private fun ajustarBrilloImagen(lux: Float) {
        val brillo = calcularBrilloImagen(lux)

        val colorMatrix = ColorMatrix()
        colorMatrix.setScale(brillo, brillo, brillo, 1f)

        val colorFilter = ColorMatrixColorFilter(colorMatrix)
        binding.ivBombilla.colorFilter = colorFilter
    }

    private fun calcularBrilloImagen(lux: Float): Float {
        if (lux < 10) {
            return 0.3f
        } else if (lux < 50) {
            return 0.5f
        } else if (lux < 200) {
            return 0.7f
        } else if (lux < 1000) {
            return 1.0f
        } else {
            return 1.5f
        }
    }

}

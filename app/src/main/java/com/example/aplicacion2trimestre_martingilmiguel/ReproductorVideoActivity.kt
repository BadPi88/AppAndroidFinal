package com.example.aplicacion2trimestre_martingilmiguel

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.MediaController
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.aplicacion2trimestre_martingilmiguel.databinding.ActivityReproductorVideoBinding

class ReproductorVideoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityReproductorVideoBinding
    private lateinit var mediaController: MediaController
    private var rutaVideo = ""
    private var posicion = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReproductorVideoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mediaController = MediaController(this)
        binding.videoView.setMediaController(mediaController)
        mediaController.setAnchorView(binding.videoView)

        if (savedInstanceState != null) {
            posicion = savedInstanceState.getInt("POSICION")
            rutaVideo = savedInstanceState.getString("RUTA", "")
            reproducirVideo()
        }

        binding.btnPlay.setOnClickListener {
            reproducirVideo()
        }
    }



    //
    private fun reproducirVideo() {
        val idVideo = R.raw.pocketbase_vid1
        val rutaVideo = "android.resource://$packageName/$idVideo"
        val uri = Uri.parse(rutaVideo)
        try {
            binding.videoView.setVideoURI(uri)
            binding.videoView.requestFocus()
            binding.videoView.start()
        } catch (e: Exception) {
            Log.d("Error: ", e.message.toString())
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("POSICION", binding.videoView.currentPosition)
        outState.putString("RUTA", rutaVideo)
    }

    override fun onPause() {
        super.onPause()
        if (binding.videoView.isPlaying) {
            posicion = binding.videoView.currentPosition
            binding.videoView.pause()
        }
    }

    override fun onResume() {
        super.onResume()
        if (rutaVideo.isNotEmpty()) {
            reproducirVideo()
        }
    }
}

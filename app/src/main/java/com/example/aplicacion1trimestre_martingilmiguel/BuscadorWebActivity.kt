package com.example.aplicacion1trimestre_martingilmiguel

import android.graphics.Bitmap
import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.aplicacion1trimestre_martingilmiguel.databinding.ActivityBuscadorWebBinding
import java.util.Locale

class BuscadorWebActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBuscadorWebBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityBuscadorWebBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        inicializarWebView()
        setListeners()
    }

    private fun inicializarWebView() {
        binding.webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest?
            ): Boolean {
                return false
            }

            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                binding.swipe.isRefreshing = true
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                binding.swipe.isRefreshing = false
            }
        }

        binding.webView.webChromeClient = WebChromeClient()

        // Enable JavaScript
        binding.webView.settings.javaScriptEnabled = true
        binding.webView.loadUrl("https://www.google.es")
    }

//----------------------------------------------------------
    private fun setListeners() {
        binding.swipe.setOnRefreshListener {
            binding.webView.reload()
        }

        binding.searchView.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                val busqueda = query.toString().trim().lowercase(Locale.ROOT)
                if (android.util.Patterns.WEB_URL.matcher(busqueda).matches()) {
                    binding.webView.loadUrl(busqueda)
                    return true
                }
                val url = "https://www.google.com/search?q=${busqueda}"
                binding.webView.loadUrl(url)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })
    }
//---------------------------------------------
    override fun onBackPressed() {
        if (binding.webView.canGoBack()) {
            binding.webView.goBack()
        } else {
            super.onBackPressed()
        }
    }
}
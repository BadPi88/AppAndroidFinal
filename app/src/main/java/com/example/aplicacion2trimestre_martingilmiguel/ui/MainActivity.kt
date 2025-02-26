package com.example.aplicacion2trimestre_martingilmiguel.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.aplicacion2trimestre_martingilmiguel.R
import com.example.aplicacion2trimestre_martingilmiguel.databinding.ActivityMainBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    // registerForActivityResult para manejar la respuesta del inicio de sesión con Google
    private val responseLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == RESULT_OK) { // Si el usuario acepta el inicio de sesión
                val datos = GoogleSignIn.getSignedInAccountFromIntent(it.data)
                try {
                    val cuenta = datos.getResult(ApiException::class.java)
                    if (cuenta != null) {
                        val credenciales = GoogleAuthProvider.getCredential(cuenta.idToken, null)

                        // Autenticamos al usuario con Firebase
                        FirebaseAuth.getInstance().signInWithCredential(credenciales)
                            .addOnCompleteListener {
                                if (it.isSuccessful) {
                                    irActivityApp()
                                }
                            }
                            .addOnFailureListener {

                                Toast.makeText(this, it.message.toString(), Toast.LENGTH_SHORT)
                                    .show()
                            }
                    }
                } catch (e: ApiException) {
                    Log.d(
                        "ERROR de API:",
                        e.message.toString()
                    ) // Log en caso de error con la API de Google
                }
            }
            if (it.resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "El usuario cancelo", Toast.LENGTH_SHORT).show()
            }
        }

    private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth
    private var email = ""
    private var pass = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        title = "PocketBase"
        auth = Firebase.auth

        setListeners()
    }

    // Configura los eventos de los botones
    private fun setListeners() {
        binding.btnReset.setOnClickListener {
            limpiar() // Limpia los campos de entrada
        }
        binding.btnLogin.setOnClickListener {
            login()
        }
        binding.btnRegister.setOnClickListener {
            registrar()
        }
        binding.btnGoogle.setOnClickListener {
            loginGoogle()
        }
    }

    // Inicia sesión con Google
    private fun loginGoogle() {
        val googleConf = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.api_key_google))
            .requestEmail()
            .build()
        val googleClient = GoogleSignIn.getClient(this, googleConf)

        // Evita el inicio de sesión automático
        googleClient.signOut()

        // Lanza la actividad de inicio de sesión de Google
        responseLauncher.launch(googleClient.signInIntent)
    }

    // Registra un nuevo usuario con email y contraseña
    private fun registrar() {
        if (!datosCorrectos()) return

        // Si el registro es exitoso, iniciamos sesión automáticamente
        auth.createUserWithEmailAndPassword(email, pass)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    login()
                }
            }
            .addOnFailureListener {
                Toast.makeText(this, it.message.toString(), Toast.LENGTH_SHORT).show()
            }
    }

    // Inicia sesión con email y contraseña
    private fun login() {
        if (!datosCorrectos()) return

        auth.signInWithEmailAndPassword(email, pass)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    //Si se inicia sesión pasa a la siguiente actividad
                    irActivityApp()
                }
            }
            .addOnFailureListener {
                Toast.makeText(this, it.message.toString(), Toast.LENGTH_SHORT).show()
            }
    }

    // Verifica que los datos de inicio de sesión sean válidos
    private fun datosCorrectos(): Boolean {
        email = binding.etEmail.text.toString().trim()
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.etEmail.error = "Se esperaba una dirección de email correcta."
            return false
        }
        pass = binding.etPass.text.toString().trim()
        if (pass.length < 6) {
            binding.etPass.error = "Error, la contraseña debe tener al menos 6 caracteres"
            return false
        }
        return true
    }

    private fun limpiar() {
        binding.etPass.setText("")
        binding.etEmail.setText("")
    }

    private fun irActivityApp() {
        startActivity(Intent(this, AppActivity::class.java))
    }

    override fun onStart() {

        super.onStart()
        val usuario = auth.currentUser
        if (usuario != null) irActivityApp()
    }
}

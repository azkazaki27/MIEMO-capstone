package com.capstone.miemo.ui.auth

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.capstone.miemo.R
import com.capstone.miemo.databinding.ActivitySigninBinding
import com.capstone.miemo.ui.ViewModelFactory
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.capstone.miemo.data.Result
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class RegisterActivity : AppCompatActivity() { private lateinit var binding: ActivitySigninBinding
    private val authViewModel: AuthViewModel by viewModels {
        ViewModelFactory(this)
    }

    private lateinit var auth: FirebaseAuth
    private lateinit var usernameEditText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySigninBinding.inflate(layoutInflater)
        setContentView(binding.root)
        usernameEditText = findViewById(R.id.ed_username)

        auth = FirebaseAuth.getInstance()

        setupView()
    }

    private fun setupView() {
        hideSystemUI()

        binding.btnRegister.setOnClickListener {
            register()

        }
        binding.tvRegisterToLogin.setOnClickListener { gotoLogin() }
    }

    private fun hideSystemUI() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }

    private fun register() {
        binding.progressBar.visibility = View.VISIBLE
        val username = binding.edUsername.text.toString()
        val email = binding.edEmail.text.toString()
        val password = binding.edPassword.text.toString()

        // Use createUserWithEmailAndPassword to register a new user
        authViewModel.register(username, email, password).observe(this) { result ->
            if (result != null) {
                when (result) {
                    is Result.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }

                    is Result.Success -> {
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(
                            this,
                            result.data,
                            Toast.LENGTH_SHORT
                        ).show()
                        // Go to Login
                        gotoLogin()
                    }

                    is Result.Error -> {
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(
                            this,
                            getString(R.string.error_message, result.error),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }

    private fun updateUI(user: FirebaseUser?) {
        if (user != null) {
            Toast.makeText(this, "Registration successful", Toast.LENGTH_SHORT).show()
            // You can add any additional logic here
            // For example, you might want to navigate to the login screen
            gotoLogin()
        }
    }

    private fun gotoLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }
}
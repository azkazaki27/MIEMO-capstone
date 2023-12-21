package com.capstone.miemo.ui.auth

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.core.content.ContextCompat.startActivity
import com.capstone.miemo.MainActivity
import com.capstone.miemo.R
import com.capstone.miemo.databinding.ActivityLoginBinding
import com.capstone.miemo.ui.ViewModelFactory
import com.capstone.miemo.data.Result
import com.capstone.miemo.data.local.entity.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    private lateinit var auth: FirebaseAuth

    private val authViewModel: AuthViewModel by viewModels {
        ViewModelFactory(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        setupView()
    }

    private fun setupView() {
        hideSystemUI()

        //loggingIn()

        binding.btnLogin.setOnClickListener { login() }
        binding.tvLoginToRegister.setOnClickListener { register() }
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

    private fun loggingIn() {
        authViewModel.isLoggedIn().observe(this) { isLoggedIn ->
            if (isLoggedIn) gotoMain()
        }
    }

    private fun login() {
        binding.progressBar.visibility = View.VISIBLE
        val email = binding.edEmail.text.toString()
        val password = binding.edPassword.text.toString()

        // Use createUserWithEmailAndPassword to register a new user
        if (email.isNotEmpty() && password.isNotEmpty()) {
            // Show progress bar
            binding.progressBar.visibility = View.VISIBLE

            // Perform login
            authViewModel.login(email, password).observe(this) { result ->
                when (result) {
                    is Result.Success -> {
                        // Hide progress bar
                        binding.progressBar.visibility = View.GONE

                        // Save user data
                        Log.d("LoginActivity", result.data.toString())
                        authViewModel.saveUser(result.data)

                        Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show()
                        gotoMain()
                    }

                    is Result.Error -> {
                        // Hide progress bar
                        binding.progressBar.visibility = View.GONE

                        // Display an error message
                        Toast.makeText(this, "Login failed", Toast.LENGTH_SHORT).show()
                    }

                    is Result.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }
                }
            }
        }
    }

    private fun updateUI(user: FirebaseUser?) {
        if (user != null) {
            Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show()
            gotoMain()
        }
    }

    private fun register() {
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
    }


    private fun gotoMain() {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }

}
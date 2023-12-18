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
            val sharedPreferences = getPreferences(Context.MODE_PRIVATE)
            val enteredUsername: String = usernameEditText.text.toString()
            with(sharedPreferences.edit()) {
                putString("username", enteredUsername)
                apply()
            }
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
        val email = binding.edEmail.text.toString()
        val password = binding.edPassword.text.toString()

        // Use createUserWithEmailAndPassword to register a new user
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Registration success
                    Log.d(TAG, "createUserWithEmail:success")
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    // Registration failed
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(
                        baseContext,
                        "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()
                    updateUI(null)
                }
                binding.progressBar.visibility = View.GONE
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
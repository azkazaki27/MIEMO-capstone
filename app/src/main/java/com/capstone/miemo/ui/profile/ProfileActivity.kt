package com.capstone.miemo.ui.profile
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.capstone.miemo.R
import com.capstone.miemo.databinding.ActivityProfileBinding
import com.capstone.miemo.ui.ViewModelFactory
import com.capstone.miemo.ui.auth.AuthViewModel
import com.capstone.miemo.ui.auth.LoginActivity
import com.capstone.miemo.ui.auth.RegisterActivity
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding

    private lateinit var tvProfile: TextView
    private lateinit var imgProfile: ImageView
    private lateinit var tvUsername: TextView
    private lateinit var usernameEditText: EditText
    private lateinit var btnLogout: Button
    private lateinit var btnSave: Button

    private val profileViewModel: ProfileViewModel by viewModels{
        ViewModelFactory(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        tvProfile = findViewById(R.id.tv_profile)
        imgProfile = findViewById(R.id.img_profile)
        tvUsername = findViewById(R.id.tv_username)
        usernameEditText = findViewById(R.id.usernameEditText)
        btnLogout = findViewById(R.id.btn_logout)
        btnSave = findViewById(R.id.btn_save)

        val sharedPreferences = getPreferences(MODE_PRIVATE)

        val savedUsername = sharedPreferences.getString("username", null)
        usernameEditText.setText(savedUsername)

        btnSave.setOnClickListener {
            // Get the entered username
            val enteredUsername: String = usernameEditText.text.toString()

            profileViewModel.getSession().observe(this){
                profileViewModel.updateUsername(it.userId, enteredUsername)
            }

            // Save the username in SharedPreferences
            with(sharedPreferences.edit()) {
                putString("username", enteredUsername)
                apply()
            }
            Toast.makeText(this, "Username saved successfully", Toast.LENGTH_SHORT).show()
            finish()
        }

        btnLogout.setOnClickListener {
            profileViewModel.logOut()
            login()
        }
    }
    private fun login() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }
}

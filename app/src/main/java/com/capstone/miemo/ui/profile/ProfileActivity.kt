package com.capstone.miemo.ui.profile
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.capstone.miemo.R
import com.capstone.miemo.databinding.ActivityProfileBinding
import com.capstone.miemo.ui.auth.RegisterActivity
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding
    private lateinit var auth: FirebaseAuth

    // Declare your UI elements here
    private lateinit var tvProfile: TextView
    private lateinit var imgProfile: ImageView
    private lateinit var tvUsername: TextView
    private lateinit var usernameEditText: EditText
    private lateinit var btnLogout: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        tvProfile = findViewById(R.id.tv_profile)
        imgProfile = findViewById(R.id.img_profile)
        tvUsername = findViewById(R.id.tv_username)
        usernameEditText = findViewById(R.id.usernameEditText)
        btnLogout = findViewById(R.id.btn_logout)

        val sharedPreferences = getPreferences(MODE_PRIVATE)
        val savedUsername = sharedPreferences.getString("username", "")
        binding.usernameEditText.hint = "${savedUsername}"

        btnLogout.setOnClickListener {
            Firebase.auth.signOut()
            register()
        }
    }
    private fun register() {
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
    }
}

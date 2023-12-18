package com.capstone.miemo.ui.profile
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.capstone.miemo.R

class ProfileActivity : AppCompatActivity() {

    // Declare your UI elements here
    private lateinit var tvProfile: TextView
    private lateinit var imgProfile: ImageView
    private lateinit var tvEmail: TextView
    private lateinit var usernameEditText: EditText
    private lateinit var btnLogout: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        tvProfile = findViewById(R.id.tv_profile)
        imgProfile = findViewById(R.id.img_profile)
        tvEmail = findViewById(R.id.tv_email)
        usernameEditText = findViewById(R.id.usernameEditText)
        btnLogout = findViewById(R.id.btn_logout)

        // Now you can use these variables to manipulate the UI as needed
        // For example, you can set text or click listeners

        // Example: Set a click listener for the logout button
        btnLogout.setOnClickListener {
            // Handle logout button click
            // Add your logic here
        }
    }
}

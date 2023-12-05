package com.capstone.miemo.ui.activity.login

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.capstone.miemo.MainActivity
import com.capstone.miemo.R
import com.capstone.miemo.databinding.ActivityLoginBinding
import com.capstone.miemo.ui.activity.signin.SigninActivity

class LoginActivity : AppCompatActivity(), View.OnClickListener {
    private val binding by lazy { ActivityLoginBinding.inflate(layoutInflater) }
    private val viewModel: LoginViewModel by viewModels { ViewModelFactory.getInstance(this) }
    private val fields: List<View> by lazy {
        listOf(
            binding.tvLogin,
            binding.edEmail,
            binding.edPassword,
            binding.btnLogin,
            binding.tvLoginToRegister
        )
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        supportActionBar?.hide()
        configureObserver()
        binding.btnLogin.setOnClickListener(this)
        binding.tvLoginToRegister.setOnClickListener(this)
        playAnimation(true)
        enableFields(true)
    }
    override fun onClick(v: View) {
        val b = binding
        when (v.id) {
            b.btnLogin.id          -> {
                if (!viewModel.inputIsValid(binding)) {
                    Toast.makeText(
                        this@LoginActivity,
                        getString(R.string.toast_login_invalid_input),
                        Toast.LENGTH_SHORT
                    ).show()
                    return
                }
                val email = b.edEmail.text.toString().trim()
                val password = b.edPassword.text.toString().trim()
                login(email, password)
            }
            b.tvLoginToRegister.id -> {
                val intent = Intent(this@LoginActivity, SigninActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
                startActivity(intent)
            }
        }
    }
    override fun onResume() {
        super.onResume()
        enableFields(true)
        playAnimation(true, 200)
    }
    override fun onPause() {
        super.onPause()
        enableFields(false)
        playAnimation(false, 0)
    }
    private fun login(email: String, password: String) {
        viewModel.login(email, password)
    }
    private fun configureObserver() = viewModel.loginStatus.observe(this) { result ->
        when (result) {
            is RequestResult.Loading -> {
                playAnimation(false, 150)
                enableFields(false)
                showLoading(true)
            }
            is RequestResult.Error   -> {
                Toast.makeText(
                    this@LoginActivity,
                    getString(R.string.toast_login_failed, result.error),
                    Toast.LENGTH_SHORT
                ).show()
                enableFields(true)
                playAnimation(true, 200)
                showLoading(false)
            }
            is RequestResult.Success -> {
                Toast.makeText(
                    this,
                    getString(R.string.toast_login_greetings, result.data.name),
                    Toast.LENGTH_SHORT
                ).show()
                playAnimation(true, 150)
                val user = result.data
                viewModel.addUser(user)
                viewModel.holdToken(user.token)
                val intent = Intent(this@LoginActivity, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
                finish()
            }
        }
    }
    private fun playAnimation(visible: Boolean, duration: Long = 500) {
        val targetValue = if (visible) 1f else 0f
        val b = binding
        val (
            titleAnim, emailAnim, passwdAnim, btnAnim, toRegister,
        ) = fields.map {
            ObjectAnimator.ofFloat(it, View.ALPHA, targetValue).setDuration(duration)
        }
        AnimatorSet().apply {
            playSequentially(titleAnim, emailAnim, passwdAnim, btnAnim, toRegister)
        }.start()
    }
    private fun showLoading(visible: Boolean) {
        binding.pbLogin.visibility = if (visible) View.VISIBLE else View.GONE
    }
    private fun enableFields(enable: Boolean) {
        fields.forEach {
            it.isEnabled = enable
        }
    }
}
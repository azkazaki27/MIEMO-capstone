package com.capstone.miemo.ui.activity.signin

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.capstone.miemo.R
import com.capstone.miemo.databinding.ActivitySigninBinding

class SigninActivity : AppCompatActivity(), View.OnClickListener
{
    private val viewModel: SigninViewModel by viewModels { ViewModelFactory.getInstance(this)
    }
    private val binding by lazy { ActivitySigninBinding.inflate(layoutInflater) }
    private val fields: List<View> by lazy {
        listOf(
            binding.tvRegister,
            binding.edUsername,
            binding.edEmail,
            binding.edPassword,
            binding.btnRegister,
            binding.tvRegisterToLogin
        )
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        supportActionBar?.hide()

        configureObserver()

        binding.btnRegister.setOnClickListener(this)
        binding.tvRegisterToLogin.setOnClickListener(this)
    }
    override fun onClick(v: View) {
        val b = binding
        when (v.id) {
            b.btnRegister.id       -> {
                if (!viewModel.inputIsValid(binding)) {
                    Toast.makeText(
                        this@SigninActivity,
                        getString(R.string.toast_register_invalid_input),
                        Toast.LENGTH_SHORT
                    ).show()
                    return
                }
                val name = b.edUsername.text.toString().trim()
                val email = b.edEmail.text.toString().trim()
                val password = b.edPassword.text.toString().trim()

                register(name, email, password)
            }
            b.tvRegisterToLogin.id -> {
                val intent = Intent(this@SigninActivity, LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
                startActivity(intent)
            }
        }
    }
    override fun onResume() {
        super.onResume()
        playAnimation(true, 200)
        enableFields(true)
    }
    override fun onPause() {
        super.onPause()
        playAnimation(false, 0)
        enableFields(false)
    }
    private fun register(name: String, email: String, password: String) {
        viewModel.register(name, email, password)
    }
    private fun configureObserver() {
        viewModel.registerStatus.observe(this) { result ->
            when (result) {
                is RequestResult.Loading -> {
                    playAnimation(false, 150)
                    showLoading(true)
                    enableFields(false)
                }
                is RequestResult.Error   -> {
                    Toast.makeText(
                        this@SigninActivity,
                        getString(R.string.toast_register_failed, result.error),
                        Toast.LENGTH_SHORT
                    )
                        .show()
                    playAnimation(true, 150)
                    showLoading(false)
                    enableFields(true)
                }
                is RequestResult.Success -> {
                    Toast.makeText(
                        this@SigninActivity,
                        getString(R.string.toast_register_success),
                        Toast.LENGTH_SHORT
                    )
                        .show()
                    playAnimation(true, 150)

                    val intent = Intent(this@SigninActivity, LoginActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(intent)
                    finish()
                }
            }
        }
    }

    private fun playAnimation(visible: Boolean, duration: Long = 450) {
        val targetValue = if (visible) 1f else 0f

        val animations = fields.map {
            ObjectAnimator.ofFloat(it, View.ALPHA, targetValue).setDuration(duration)
        }
        var _it = 0
        val titleAnim = animations[_it++]
        val nameAnim = animations[_it++]
        val emailAnim = animations[_it++]
        val passwdAnim = animations[_it++]
        val btnAnim = animations[_it++]
        val toLogin = animations[_it++]

        AnimatorSet().apply {
            playSequentially(titleAnim, nameAnim, emailAnim, passwdAnim, btnAnim, toLogin)
        }.start()
    }
    private fun showLoading(visible: Boolean) {
        binding.pbRegister.visibility = if (visible) View.VISIBLE else View.GONE
    }
    private fun enableFields(enable: Boolean) {
        fields.forEach {
            it.isEnabled = enable
        }
    }
}
package com.udeldev.storyapp.view.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import com.udeldev.storyapp.databinding.ActivityLoginBinding
import com.udeldev.storyapp.helper.TokenPreference
import com.udeldev.storyapp.helper.dataStore
import com.udeldev.storyapp.helper.factory.TokenFactory
import com.udeldev.storyapp.view.main.MainActivity
import com.udeldev.storyapp.view.welcome.WelcomeActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var activityLoginBinding: ActivityLoginBinding
    private lateinit var loginViewModel: LoginViewModel
    private var isError :Boolean? = null


    private fun initComponent() {
        activityLoginBinding = ActivityLoginBinding.inflate(layoutInflater)
        loginViewModel = obtainViewModel()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initComponent()
        setContentView(activityLoginBinding.root)

        loginViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        activityLoginBinding.editLoginPassword.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                activityLoginBinding.editLoginPasswordLayout.error = validPassword(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {}

        })

        activityLoginBinding.editLoginEmail.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                activityLoginBinding.editLoginEmailLayout.error = validEmail(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })

        activityLoginBinding.buttonLogin.setOnClickListener {
            activityLoginBinding.editLoginEmailLayout.error =
                validEmail(activityLoginBinding.editLoginEmail.text.toString())
            activityLoginBinding.editLoginPasswordLayout.error =
                validPassword(activityLoginBinding.editLoginPassword.text.toString())
            if(activityLoginBinding.editLoginEmailLayout.error != null || activityLoginBinding.editLoginPasswordLayout.error != null
            ) {
                Toast.makeText(this, "Please insert the valid data", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            loginViewModel.loginUser(
                activityLoginBinding.editLoginEmail.text.toString(),
                activityLoginBinding.editLoginPassword.text.toString()
            )

            loginViewModel.isError.observe(this){
                isError = it
            }
            loginViewModel.isLoading.observe(this) {
                if (isError == true){
                    AlertDialog.Builder(this).apply {
                        setTitle("Message")
                        setMessage("Can't Login, please check your data again")
                        setPositiveButton("Oke") { _, _ ->
                            val intent = Intent(context, WelcomeActivity::class.java)
                            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                            startActivity(intent)
                            finish()
                        }
                        create()
                        show()
                    }
                    return@observe
                }
                if (!it) {
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }
            }

        }

    }

    private fun validPassword(password: String): String? {
        if (password.length < 8) return "Password length less than 8"
        return null
    }

    private fun validEmail(email: String): String? {
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return "Invalid Email Address"
        }
        return null
    }

    private fun showLoading(isLoading: Boolean) {
        with(activityLoginBinding) {
            activityLoginBinding.progressLogin.visibility = if (isLoading) View.VISIBLE else View.GONE
            imageLoginIllustration.visibility = if (!isLoading) View.VISIBLE else View.GONE
            textLoginTitle.visibility = if (!isLoading) View.VISIBLE else View.GONE
            textLoginDesc.visibility = if (!isLoading) View.VISIBLE else View.GONE
            textLoginEmailLabel.visibility = if (!isLoading) View.VISIBLE else View.GONE
            editLoginEmailLayout.visibility = if (!isLoading) View.VISIBLE else View.GONE
            textLoginPasswordLabel.visibility = if (!isLoading) View.VISIBLE else View.GONE
            editLoginPasswordLayout.visibility = if (!isLoading) View.VISIBLE else View.GONE
            buttonLogin.visibility = if (!isLoading) View.VISIBLE else View.GONE
        }
    }

    private fun obtainViewModel(): LoginViewModel {
        val pref = TokenPreference.getInstance(application.dataStore)
        val factory = TokenFactory(pref)
        return ViewModelProvider(this, factory)[LoginViewModel::class.java]
    }
}
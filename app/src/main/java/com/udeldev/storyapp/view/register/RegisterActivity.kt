package com.udeldev.storyapp.view.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import com.udeldev.storyapp.R
import com.udeldev.storyapp.databinding.ActivityRegisterBinding
import com.udeldev.storyapp.view.welcome.WelcomeActivity

class RegisterActivity : AppCompatActivity() {

    private lateinit var activityRegisterBinding: ActivityRegisterBinding
    private lateinit var registerViewModel: RegisterViewModel
    private var isError :Boolean? = null

    private fun initComponent() {
        activityRegisterBinding = ActivityRegisterBinding.inflate(layoutInflater)
        registerViewModel = ViewModelProvider(this)[RegisterViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initComponent()
        setContentView(activityRegisterBinding.root)

        registerViewModel.isLoading.observe(this) {
            showLoading(it)
        }



        activityRegisterBinding.buttonRegister.setOnClickListener {
            activityRegisterBinding.editRegisterEmailLayout.helperText =
                validEmail(activityRegisterBinding.editRegisterEmail.text.toString())
            activityRegisterBinding.editRegisterPasswordLayout.helperText =
                validPassword(activityRegisterBinding.editRegisterPassword.text.toString())

            if (activityRegisterBinding.editRegisterEmailLayout.helperText != null || activityRegisterBinding.editRegisterPasswordLayout.helperText != null){
                Toast.makeText(this, "Please insert the valid data", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            registerViewModel.regisUser(
                activityRegisterBinding.editRegisterName.text.toString(),
                activityRegisterBinding.editRegisterEmail.toString(),
                activityRegisterBinding.editRegisterPassword.toString(),
            )

            registerViewModel.isError.observe(this){
                isError = it
            }
            registerViewModel.isLoading.observe(this){
                if (!it){
                    AlertDialog.Builder(this).apply {
                        setTitle("Message")
                        setMessage(if (isError == true) "Can't create account" else "Account created")
                        setPositiveButton("Oke") { _, _ ->
                            val intent = Intent(context, WelcomeActivity::class.java)
                            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                            startActivity(intent)
                            finish()
                        }
                        create()
                        show()
                    }
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
        activityRegisterBinding.progressRegister.visibility = if (isLoading) View.VISIBLE else View.GONE
        activityRegisterBinding.imageRegisterIllustration.visibility = if (!isLoading) View.VISIBLE else View.GONE
        activityRegisterBinding.textRegisterTitle.visibility = if (!isLoading) View.VISIBLE else View.GONE
        activityRegisterBinding.textRegisterNameLabel.visibility = if (!isLoading) View.VISIBLE else View.GONE
        activityRegisterBinding.editRegisterNameLayout.visibility = if (!isLoading) View.VISIBLE else View.GONE
        activityRegisterBinding.textRegisterEmailLabel.visibility = if (!isLoading) View.VISIBLE else View.GONE
        activityRegisterBinding.editRegisterEmailLayout.visibility = if (!isLoading) View.VISIBLE else View.GONE
        activityRegisterBinding.textRegisterPasswordLabel.visibility = if (!isLoading) View.VISIBLE else View.GONE
        activityRegisterBinding.editRegisterPasswordLayout.visibility = if (!isLoading) View.VISIBLE else View.GONE
        activityRegisterBinding.buttonRegister.visibility = if (!isLoading) View.VISIBLE else View.GONE
    }

}
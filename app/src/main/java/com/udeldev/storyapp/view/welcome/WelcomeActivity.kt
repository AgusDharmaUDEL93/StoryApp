package com.udeldev.storyapp.view.welcome

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.udeldev.storyapp.R
import com.udeldev.storyapp.databinding.ActivityWelcomeBinding
import com.udeldev.storyapp.view.login.LoginActivity
import com.udeldev.storyapp.view.register.RegisterActivity

class WelcomeActivity : AppCompatActivity() {

    private lateinit var activityWelcomeBinding: ActivityWelcomeBinding


    private fun initComponent(){
        activityWelcomeBinding = ActivityWelcomeBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initComponent()
        setContentView(activityWelcomeBinding.root)

        activityWelcomeBinding.buttonWelcomeToLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
        activityWelcomeBinding.buttonWelcomeToRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

    }
}
package com.udeldev.storyapp.view.add

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.udeldev.storyapp.R
import com.udeldev.storyapp.databinding.ActivityAddBinding

class AddActivity : AppCompatActivity() {

    private lateinit var activityAddBinding :ActivityAddBinding
    private fun initComponent(){
        activityAddBinding= ActivityAddBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)
    }
}
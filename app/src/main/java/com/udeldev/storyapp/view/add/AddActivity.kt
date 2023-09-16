package com.udeldev.storyapp.view.add

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import com.udeldev.storyapp.R
import com.udeldev.storyapp.databinding.ActivityAddBinding
import com.udeldev.storyapp.helper.CameraUtils.Companion.getImageUri

class AddActivity : AppCompatActivity() {

    private lateinit var activityAddBinding :ActivityAddBinding

    private var currentImageUri: Uri? = null

    private fun initComponent(){
        activityAddBinding= ActivityAddBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initComponent()
        setContentView(activityAddBinding.root)

        activityAddBinding.buttonAddGallery.setOnClickListener {
            startGallery()
        }
        activityAddBinding.buttonAddCamera.setOnClickListener {
            startCamera()
        }
    }

    private fun startCamera() {
        currentImageUri = getImageUri(this)
        launcherIntentCamera.launch(currentImageUri)
    }

    private val launcherIntentCamera = registerForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { isSuccess ->
        if (isSuccess) {
            showImage()
        }
    }

    private fun startGallery() {
        launcherGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private val launcherGallery = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            currentImageUri = uri
            showImage()
        } else {
            Log.d("Photo Picker", "No media selected")
        }
    }

    private fun showImage() {
        currentImageUri?.let {
            Log.d("Image URI", "showImage: $it")
            activityAddBinding.imageAddStory.setImageURI(it)
        }
    }

}
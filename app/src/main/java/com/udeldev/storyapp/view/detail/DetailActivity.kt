package com.udeldev.storyapp.view.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.udeldev.storyapp.databinding.ActivityDetailBinding
import com.udeldev.storyapp.helper.TokenPreference
import com.udeldev.storyapp.helper.dataStore
import com.udeldev.storyapp.helper.factory.TokenFactory
import com.udeldev.storyapp.model.response.ListStoryItem

class DetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_ID = "id"
    }

    private lateinit var activityDetailBinding: ActivityDetailBinding
    private lateinit var detailViewModel: DetailViewModel

    private fun initComponent() {
        activityDetailBinding = ActivityDetailBinding.inflate(layoutInflater)
        detailViewModel = obtainViewModel()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initComponent()
        setContentView(activityDetailBinding.root)

        val id = intent.getStringExtra(EXTRA_ID) ?: return
        detailViewModel.getStoryDetail(id)

        detailViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        detailViewModel.storyDetail.observe(this) {
            if (it != null) {
                setStoryDetail(it)
            }
        }
    }

    private fun setStoryDetail(listStoryItem: ListStoryItem) {
        with(activityDetailBinding) {
            textDetailTitle.text = listStoryItem.name
            textDetailCreatedAt.text = listStoryItem.createdAt
            textDetailDesc.text = listStoryItem.description
        }

        Glide.with(this)
            .load(listStoryItem.photoUrl ?: "https://i.stack.imgur.com/l60Hf.png")
            .into(activityDetailBinding.imageDetailStory)
    }

    private fun showLoading(isLoading: Boolean) {
        activityDetailBinding.progressDetail.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun obtainViewModel(): DetailViewModel {
        val pref = TokenPreference.getInstance(application.dataStore)
        val factory = TokenFactory(pref)
        return ViewModelProvider(this, factory)[DetailViewModel::class.java]
    }
}
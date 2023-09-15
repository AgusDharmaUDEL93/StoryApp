package com.udeldev.storyapp.view.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.udeldev.storyapp.R
import com.udeldev.storyapp.controller.adapter.StoryListAdapter
import com.udeldev.storyapp.databinding.ActivityMainBinding
import com.udeldev.storyapp.helper.TokenPreference
import com.udeldev.storyapp.helper.dataStore
import com.udeldev.storyapp.helper.factory.TokenFactory
import com.udeldev.storyapp.model.response.ListStoryItem
import com.udeldev.storyapp.view.add.AddActivity
import com.udeldev.storyapp.view.welcome.WelcomeActivity

class MainActivity : AppCompatActivity() {

    private lateinit var mainViewModel: MainViewModel
    private lateinit var activityMainBinding: ActivityMainBinding
    private lateinit var adapter : StoryListAdapter

    private fun initComponent (){
        mainViewModel = obtainViewModel()
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_logout, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menu_dropdown_logout -> mainViewModel.logoutSession()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initComponent()
        setContentView(activityMainBinding.root)

        activityMainBinding.recyclerMainStories.layoutManager = LinearLayoutManager(this)
        adapter = StoryListAdapter()
        activityMainBinding.recyclerMainStories.adapter = adapter

        mainViewModel.getSession().observe(this){ token ->
            if (token.isEmpty()){
                startActivity(Intent(this, WelcomeActivity::class.java))
                finish()
            }
        }

        mainViewModel.isLoading.observe(this){
            showLoading(it)
        }

        mainViewModel.getAllDataStory()
        mainViewModel.allStoryResponse.observe(this){
            setStoryListData(it.listStory)
        }

        activityMainBinding.buttonMainAdd.setOnClickListener {
            startActivity(Intent(this, AddActivity::class.java))
        }
    }

    private fun showLoading(isLoading:Boolean){
        activityMainBinding.progressMain.visibility = if (isLoading) View.VISIBLE else View.GONE

    }

    private fun setStoryListData (storyList: List<ListStoryItem?>?){
        adapter.setStoryList(storyList)
        activityMainBinding.recyclerMainStories.adapter = adapter
    }

    private fun obtainViewModel() : MainViewModel{
        val pref = TokenPreference.getInstance(application.dataStore)
        val factory = TokenFactory(pref)
        return ViewModelProvider(this, factory)[MainViewModel::class.java]
    }
}
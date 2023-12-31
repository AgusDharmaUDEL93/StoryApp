package com.udeldev.storyapp.controller.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.core.util.Pair
import androidx.core.app.ActivityOptionsCompat
import com.udeldev.storyapp.R
import com.udeldev.storyapp.model.response.ListStoryItem
import com.bumptech.glide.Glide
import com.udeldev.storyapp.view.detail.DetailActivity


class StoryListAdapter : RecyclerView.Adapter<StoryListAdapter.StoryListViewHolder>() {

    private var _storyList : List<ListStoryItem?>? = emptyList()

    @SuppressLint("NotifyDataSetChanged")
    fun setStoryList (value : List<ListStoryItem?>?){
        _storyList = value
        notifyDataSetChanged()
    }

    inner class StoryListViewHolder (itemView : View) : RecyclerView.ViewHolder(itemView){
        val imageStoryList : ImageView  = itemView.findViewById(R.id.image_story_list)
        val titleStoryList :TextView = itemView.findViewById(R.id.text_story_list_title)
        val descStoryList : TextView = itemView.findViewById(R.id.text_story_list_desc)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoryListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_story, parent, false)
        return StoryListViewHolder(view)
    }

    override fun getItemCount(): Int = _storyList?.size ?: 0

    override fun onBindViewHolder(holder: StoryListViewHolder, position: Int) {
        holder.titleStoryList.text = _storyList?.get(position)?.name
        holder.descStoryList.text = _storyList?.get(position)?.description
        Glide.with(holder.itemView.context)
            .load(_storyList?.get(position)?.photoUrl ?: "https://i.stack.imgur.com/l60Hf.png")
            .into(holder.imageStoryList)
        holder.itemView.setOnClickListener {
            val moveIntent = Intent(holder.itemView.context, DetailActivity::class.java)
            moveIntent.putExtra(DetailActivity.EXTRA_ID, _storyList?.get(position)?.id)
            val optionsCompat: ActivityOptionsCompat =
                ActivityOptionsCompat.makeSceneTransitionAnimation(
                    holder.itemView.context as Activity,
                    Pair(holder.imageStoryList, "image_detail_story"),
                    Pair(holder.titleStoryList, "text_detail_title"),
                    Pair(holder.descStoryList, "text_detail_desc"),
                )
            holder.itemView.context.startActivity(moveIntent, optionsCompat.toBundle())
        }
    }
}
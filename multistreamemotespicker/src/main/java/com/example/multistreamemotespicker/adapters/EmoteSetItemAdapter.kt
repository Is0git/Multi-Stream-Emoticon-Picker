package com.example.multistreamemotespicker.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.android.multistreamchat.chat.chat_emotes.EmotesManager
import com.android.multistreamchat.twitch_chat.chat_emotes.TwitchEmotesManager
import com.bumptech.glide.Glide
import com.example.multistreamemotespicker.R

class EmoteSetItemAdapter : RecyclerView.Adapter<EmoteSetItemAdapter.MyViewHolder>() {

    var emotesList: List<TwitchEmotesManager.TwitchEmote>? = null
    set(value) {
        field = value
        notifyDataSetChanged()
    }
    class MyViewHolder(view: View): RecyclerView.ViewHolder(view) {
        var emoteImage: ImageView = view.findViewById(R.id.emote_image)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
       val view = LayoutInflater.from(parent.context).inflate(R.layout.emotes_list_item_layout, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
      return emotesList?.count() ?: 0
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        Glide.with(holder.emoteImage).load(emotesList?.get(position)?.imageUrl).into(holder.emoteImage)
    }
}
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
import com.google.android.material.snackbar.BaseTransientBottomBar.LENGTH_SHORT
import com.google.android.material.snackbar.Snackbar

class EmoteSetItemAdapter<T : EmotesManager.Emote>(var onBindEmoteItem: (holder: MyViewHolder, dataItem: T) -> Unit) :
    RecyclerView.Adapter<EmoteSetItemAdapter.MyViewHolder>() {

    var emotesList: List<T>? = null
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var emoteImage: ImageView = view.findViewById(R.id.emote_image)

        lateinit var contextText: String

        init {
            view.setOnLongClickListener {
                Snackbar.make(emoteImage, contextText, LENGTH_SHORT).show().let { true }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.emotes_list_item_layout, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return emotesList?.count() ?: 0
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.contextText = emotesList?.get(position)?.code ?: "Kappa"
        onBindEmoteItem(holder, emotesList?.get(position)!!)
    }
}
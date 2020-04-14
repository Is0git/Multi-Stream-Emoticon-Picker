package com.example.multistreamemotespicker.adapters

import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.multistreamchat.chat.chat_emotes.EmotesManager
import com.example.multistreamemotespicker.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class EmotesViewpagerAdapter<T : EmotesManager.Emote>(
    var onBindHolder: (holder: MyViewHolder<T>?, item: List<T>?, position: Int) -> Unit,
    var onBindEmoteItem: (holder: EmoteSetItemAdapter.MyViewHolder, dataItem: T) -> Unit
) : RecyclerView.Adapter<EmotesViewpagerAdapter.MyViewHolder<T>>() {

    var data: MutableMap<String, List<T>>? = mutableMapOf()


    fun putData(data: String, list: List<T>) {
        this.data?.put(data, list)
        this.data?.put("search", mutableListOf())
        notifyDataSetChanged()
    }

    @Suppress("unchecked_cast")
    suspend fun searchItem(
        text: String,
        emotes: MutableMap<out Any?, out List<EmotesManager.Emote>>?
    ) {
        val list = mutableListOf<T>()
        emotes?.forEach {
            it.value.forEach { emote ->
                if (emote.code?.contains(text, true)!!) list.add(emote as T)
            }
        }
        data?.put("search", list)
        withContext(Dispatchers.Main) { notifyDataSetChanged() }
    }

    class MyViewHolder<T : EmotesManager.Emote>(
        view: View,
        onBindEmoteItem: (holder: EmoteSetItemAdapter.MyViewHolder, dataItem: T) -> Unit
    ) : RecyclerView.ViewHolder(view) {

        var adapter: EmoteSetItemAdapter<T>? = EmoteSetItemAdapter(onBindEmoteItem)

        var list: RecyclerView = view.findViewById(R.id.emotes_list)

        init {
            list.apply {
                adapter = this@MyViewHolder.adapter
                (layoutManager as GridLayoutManager).spanCount = calculateSpanCount()
            }
        }

        var emoteSetName: TextView = view.findViewById(R.id.emotes_set_name)

        private fun calculateSpanCount(): Int {
            val displayMetrics = itemView.context.resources.displayMetrics
            val px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 35f, displayMetrics)
            val spanCount = (displayMetrics.widthPixels / px).toInt()
            return spanCount.coerceAtMost(7)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder<T> {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.emotes_list, parent, false)
        return MyViewHolder(view, onBindEmoteItem)
    }

    override fun getItemCount(): Int {
        return data?.count() ?: 0
    }


    override fun onBindViewHolder(holder: MyViewHolder<T>, position: Int) {
        onBindHolder(
            holder,
            if (position == itemCount - 1) data?.get("search") else data?.values?.elementAt(position),
            position
        )
    }

}
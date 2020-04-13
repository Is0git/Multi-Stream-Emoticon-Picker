package com.example.multistreamemotespicker.adapters

import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.multistreamemotespicker.R

class EmotesViewpagerAdapter<K, T>(var onBindHolder: (holder: EmotesViewpagerAdapter.MyViewHolder<T>?, item: List<T>?, position: Int) -> Unit) : RecyclerView.Adapter<EmotesViewpagerAdapter.MyViewHolder<T>>() {

    var data: MutableMap<K, List<T>>? = mutableMapOf()


    fun putData(data: K, list: List<T>) {
        this.data?.put(data, list)
        notifyDataSetChanged()
    }

    class MyViewHolder<T>(view: View) : RecyclerView.ViewHolder(view) {

        var adapter: EmoteSetItemAdapter? = EmoteSetItemAdapter()

        var list: RecyclerView = view.findViewById(R.id.emotes_list)

        init {
            list.apply {
                adapter = this@MyViewHolder.adapter
                (layoutManager as GridLayoutManager).spanCount =calculateSpanCount()
            }

        }
        var emoteSetName: TextView = view.findViewById(R.id.emotes_set_name)

        private fun calculateSpanCount() : Int{
            val displayMetrics =   itemView.context.resources.displayMetrics
            val px =TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 35f, displayMetrics)
            val spanCount = (displayMetrics.widthPixels / px).toInt()
            return spanCount.coerceAtMost(7)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder<T> {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.emotes_list, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
      return data?.count() ?: 0
    }



    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)
    }

    override fun onBindViewHolder(holder: MyViewHolder<T>, position: Int) {
        onBindHolder(holder, data?.values?.elementAt(position), position)
    }

}
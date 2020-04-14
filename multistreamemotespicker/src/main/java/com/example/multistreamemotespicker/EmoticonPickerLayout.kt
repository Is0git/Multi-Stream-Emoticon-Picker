package com.example.multistreamemotespicker

import android.content.Context
import android.util.AttributeSet
import android.widget.SearchView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabItem
import com.google.android.material.tabs.TabLayout

class EmoticonPickerLayout : ConstraintLayout {

    constructor(context: Context?) : super(context) {
        init(context)
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        init(context, attrs)
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context, attrs)
    }

    var onSearchTextChange: ((text: String?) -> Unit)? = null

     var emotesSearchBar: SearchView? = null

     var emotesTabLayout: TabLayout? = null

     var emotesViewPager: ViewPager2? = null

    private fun init(context: Context?, attrs: AttributeSet? = null) {

        emotesSearchBar = SearchView(context).also {
            it.id = R.id.emote_search_bar
            it.setOnSearchClickListener { emotesViewPager?.setCurrentItem(emotesViewPager?.adapter?.itemCount?.minus(1) ?: 0, true)}
            it.setOnCloseListener { emotesViewPager?.setCurrentItem(0, true).let { false } }
            it.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    onSearchTextChange?.invoke(newText)
                    return false
                }

            })
        }

        emotesTabLayout = TabLayout(context!!).also {
            it.id = R.id.emotes_tab_layout
            it.tabMode = TabLayout.MODE_SCROLLABLE
        }

        emotesViewPager = ViewPager2(context).also { it.id = R.id.emotes_viewpager }

        addView(emotesSearchBar)
        addView(emotesTabLayout)
        addView(emotesViewPager)

        ConstraintSet().apply {
            clone(context, R.layout.emotes_layout)
            setConstraintSet(this)
        }

    }
}
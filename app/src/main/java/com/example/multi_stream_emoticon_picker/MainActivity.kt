package com.example.multi_stream_emoticon_picker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.android.multistreamchat.chat.Chat
import com.android.multistreamchat.chat.chat_parser.ChatParser
import com.android.multistreamchat.chat.listeners.DataListener
import com.android.multistreamchat.chat.listeners.EmoteStateListener
import com.android.multistreamchat.twitch_chat.chat_emotes.TwitchEmotesManager
import com.android.multistreamchat.twitch_chat.chat_parser.TwitchChatParser
import com.example.multi_stream_emoticon_picker.databinding.ActivityMainBinding
import com.example.multistreamemotespicker.adapters.EmoteSetItemAdapter
import com.example.multistreamemotespicker.adapters.EmotesViewpagerAdapter
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
        lateinit var emoteViewpagerAdapter: EmotesViewpagerAdapter<String, TwitchEmotesManager.TwitchEmote>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val chat = Chat.Builder()
                .autoConnect("is0xxx")
                .setClient(Chat.HOST, Chat.PORT)
                .setUserToken("7uyg0kooxcagt096sig5f2i023mrdk")
                .setUsername("is0xxx")
                .setChatParser(TwitchChatParser::class.java)
                .addDataListener(object :
                        DataListener {
                    override fun onReceive(message: ChatParser.Message) {

                    }
                })
                .addEmoteStateListener(object :
                        EmoteStateListener<Int, TwitchEmotesManager.TwitchEmote> {
                    override fun onStartFetch() {

                    }

                    override fun onEmotesFetched() {

                    }

                    override fun onDownload() {

                    }

                    override fun onFailed(throwable: Throwable?) {

                    }

                    override fun onComplete(emoteSet: List<TwitchEmotesManager.TwitchEmote>) {
                        emoteViewpagerAdapter.putData("Something", emoteSet)
                    }
                })
                .build(this)
       binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        emoteViewpagerAdapter = EmotesViewpagerAdapter { holder: EmotesViewpagerAdapter.MyViewHolder<TwitchEmotesManager.TwitchEmote>?, item: List<TwitchEmotesManager.TwitchEmote>?, position: Int ->
           val name: String = when(position) {
                0 -> "Global Emotes"
                1 -> "Bttw"
                else -> "Search"
            }
            holder?.emoteSetName?.text = name
            holder?.adapter?.emotesList = item
        }
        binding.emoticonPicker.apply {
            emotesViewPager?.adapter = emoteViewpagerAdapter
            TabLayoutMediator(emotesTabLayout!!, emotesViewPager!!) {tab, position ->
                tab.text = position.toString()
            }.attach()
        }

    }
}

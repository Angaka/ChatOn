package com.projects.venom04.chaton.mvp.views.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log.d
import android.view.ViewGroup
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.Query
import com.projects.venom04.chaton.R
import com.projects.venom04.chaton.extensions.inflate
import com.projects.venom04.chaton.mvp.models.Chat
import com.projects.venom04.chaton.mvp.presenters.main.MainPresenter
import com.projects.venom04.chaton.mvp.presenters.main.MainView
import com.projects.venom04.chaton.mvp.views.adapters.ChatsListHolder
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), MainView, ChatsListHolder.ChatListListener {

    private lateinit var mMainPresenter : MainPresenter

    private lateinit var mAdapter : FirebaseRecyclerAdapter<Chat, ChatsListHolder>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mMainPresenter = MainPresenter(this)
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onResume() {
        super.onResume()
        mMainPresenter.loadChats()
    }

    override fun onStop() {
        super.onStop()
        mAdapter.stopListening()
    }

    override fun getAllChats(query: Query) {
        val options = FirebaseRecyclerOptions.Builder<Chat>()
                .setQuery(query, Chat::class.java)
                .build()

        mAdapter = object : FirebaseRecyclerAdapter<Chat, ChatsListHolder>(options) {

            override fun onBindViewHolder(holder: ChatsListHolder, position: Int, chat: Chat) {
                d(TAG, chat.toString())
                holder.bind(chat)
            }

            override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ChatsListHolder {
                val view = parent?.inflate(R.layout.item_chat, false)
                val holder = ChatsListHolder(view)
                holder.setListener(this@MainActivity)
                return holder
            }
        }
        mAdapter.startListening()
        recyclerView_channels.adapter = mAdapter
    }

    override fun onSelectedChat(chat: Chat) {

    }

    companion object {
        val TAG = "MainActivity"
    }
}

package com.projects.venom04.chaton.mvp.views.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log.e
import android.view.View
import android.view.ViewGroup
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.Query
import com.projects.venom04.chaton.R
import com.projects.venom04.chaton.extensions.inflate
import com.projects.venom04.chaton.mvp.models.ChatMessage
import com.projects.venom04.chaton.mvp.presenters.chat.ChatPresenter
import com.projects.venom04.chaton.mvp.presenters.chat.ChatView
import com.projects.venom04.chaton.mvp.views.adapters.ChatHolder
import com.projects.venom04.chaton.utils.Constants
import kotlinx.android.synthetic.main.activity_chat.*
import org.jetbrains.anko.longToast

/**
 * Created by beau-oudong on 29/01/2018.
 */
class ChatActivity : AppCompatActivity(), ChatView, View.OnClickListener {

    private lateinit var mChatPresenter: ChatPresenter

    private lateinit var mAdapter: FirebaseRecyclerAdapter<ChatMessage, ChatHolder>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        recyclerView_messages.layoutManager = LinearLayoutManager(this)

        val childId = intent.extras.getString(Constants.CHILD_ID)
        mChatPresenter = ChatPresenter(this, childId)
        mChatPresenter.loadChatMessages()

        button_send_message.setOnClickListener(this)
    }

    override fun onStart() {
        super.onStart()
        mAdapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        mAdapter.stopListening()
    }

    override fun loadChat(query: Query) {
        val options = FirebaseRecyclerOptions.Builder<ChatMessage>()
                .setQuery(query, ChatMessage::class.java)
                .build()
        mAdapter = object : FirebaseRecyclerAdapter<ChatMessage, ChatHolder>(options) {
            private val CURRENT_USER = 0
            private val OTHER_USER = 1

            override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ChatHolder {
                when (viewType) {
                    CURRENT_USER -> {
                        return ChatHolder(parent?.inflate(R.layout.item_right_message)!!)
                    }
                    OTHER_USER -> {
                        return ChatHolder(parent?.inflate(R.layout.item_left_message)!!)
                    }
                }
                return onCreateViewHolder(parent, viewType)
            }

            override fun onBindViewHolder(holder: ChatHolder, position: Int, chatMessage: ChatMessage) {
                e(TAG, chatMessage.toString())
                holder.bind(chatMessage)
            }

            override fun getItemViewType(position: Int): Int {
                val chatMessage: ChatMessage = getItem(position)
                when (chatMessage.user) {
                    FirebaseAuth.getInstance().currentUser?.displayName -> {
                        return CURRENT_USER
                    }
                }
                return OTHER_USER
            }
        }
        recyclerView_messages.adapter = mAdapter
    }

    override fun showMessage(message: Int) {
        longToast(message)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.button_send_message -> {
                val messageToSend = textInputLayout_message.editText?.text.toString()
                if (messageToSend.trim().isNotEmpty())
                    mChatPresenter.sendMessage(messageToSend)
            }
        }
    }

    companion object {
        private val TAG = "ChatActivity"
    }
}
package com.projects.venom04.chaton.mvp.views.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.firebase.ui.database.FirebaseListAdapter
import com.firebase.ui.database.FirebaseListOptions
import com.google.firebase.database.Query
import com.projects.venom04.chaton.R
import com.projects.venom04.chaton.mvp.models.Chat
import com.projects.venom04.chaton.mvp.models.ChatMessage
import com.projects.venom04.chaton.mvp.presenters.chat.ChatPresenter
import com.projects.venom04.chaton.mvp.presenters.chat.ChatView
import com.projects.venom04.chaton.utils.Constants
import kotlinx.android.synthetic.main.activity_chat.*
import org.jetbrains.anko.longToast

/**
 * Created by beau-oudong on 29/01/2018.
 */
class ChatActivity : AppCompatActivity(), ChatView, View.OnClickListener {

    private lateinit var mChatPresenter: ChatPresenter

    private lateinit var mAdapter: FirebaseListAdapter<ChatMessage>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

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
        val options: FirebaseListOptions<ChatMessage> = FirebaseListOptions.Builder<ChatMessage>()
                .setQuery(query, ChatMessage::class.java)
                .build()

        mAdapter = object : FirebaseListAdapter<ChatMessage>(options) {
            override fun populateView(v: View?, chatMessage: ChatMessage?, position: Int) {

            }
        }
        listView_messages.adapter = mAdapter
    }

    override fun showMessage(message: Int) {
        longToast(message)
    }

    override fun sendMessage(message: String) {
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.button_send_message -> {
                val messageToSend = textInputLayout_message.editText?.text.toString()

            }
        }
    }

    companion object {
        private val TAG = "ChatActivity"
    }
}
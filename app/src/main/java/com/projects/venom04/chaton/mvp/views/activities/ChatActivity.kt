package com.projects.venom04.chaton.mvp.views.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log.e
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.Query
import com.projects.venom04.chaton.R
import com.projects.venom04.chaton.extensions.inflate
import com.projects.venom04.chaton.mvp.models.Chat
import com.projects.venom04.chaton.mvp.models.ChatMessage
import com.projects.venom04.chaton.mvp.presenters.chat.ChatPresenter
import com.projects.venom04.chaton.mvp.presenters.chat.ChatView
import com.projects.venom04.chaton.mvp.views.adapters.ChatHolder
import com.projects.venom04.chaton.utils.Constants
import kotlinx.android.synthetic.main.activity_chat.*
import org.jetbrains.anko.longToast
import org.jetbrains.anko.startActivityForResult

/**
 * Created by beau-oudong on 29/01/2018.
 */
class ChatActivity : AppCompatActivity(), ChatView, View.OnClickListener {

    private lateinit var mChatPresenter: ChatPresenter

    private lateinit var mAdapter: FirebaseRecyclerAdapter<ChatMessage, ChatHolder>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        val chat = intent.extras.get(Constants.CHAT) as Chat
        supportActionBar?.title = chat.name
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.stackFromEnd = true
        recyclerView_messages.layoutManager = linearLayoutManager

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

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> {
                finish()
            }
            R.id.settings -> {
                val childId = intent.extras.getString(Constants.CHILD_ID)
//                val intent = Intent(this@ChatActivity, ChatSettingsActivity::class.java)
//                intent.putExtra(Constants.CHILD_ID, childId)
//                startActivityForResult(intent, DELETE_CHAT)
                startActivityForResult<ChatSettingsActivity>(DELETE_CHAT, Constants.CHILD_ID to childId)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_chat, menu)
        return true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            DELETE_CHAT -> {
                when (resultCode) {
                    Activity.RESULT_OK -> {
                        val isDeleted = data?.extras?.getBoolean(IS_DELETED)
                        e(TAG, "TEST " + isDeleted)
                        if (isDeleted!!)
                            finish()
                    }
                }
            }
        }
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
                holder.bind(chatMessage)
            }

            override fun getItemViewType(position: Int): Int {
                val chatMessage: ChatMessage = getItem(position)
                when (chatMessage.userId) {
                    FirebaseAuth.getInstance().currentUser?.uid -> {
                        return CURRENT_USER
                    }
                }
                return OTHER_USER
            }
        }
        recyclerView_messages.adapter = mAdapter
        recyclerView_messages.adapter.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                recyclerView_messages.smoothScrollToPosition(mAdapter.itemCount)
            }
        })
    }

    override fun showMessage(message: Int) {
        longToast(message)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.button_send_message -> {
                val messageToSend = textInputEditText_message.text.toString()
                if (messageToSend.trim().isNotEmpty()) {
                    mChatPresenter.sendMessage(messageToSend)
                    textInputEditText_message.text.clear()
                }
            }
        }
    }

    companion object {
        private val TAG = "ChatActivity"
        val IS_DELETED = "is_deleted"
        val DELETE_CHAT = 99
    }
}
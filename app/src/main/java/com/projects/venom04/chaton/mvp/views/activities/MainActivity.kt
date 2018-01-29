package com.projects.venom04.chaton.mvp.views.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log.e
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.firebase.ui.database.FirebaseListAdapter
import com.firebase.ui.database.FirebaseListOptions
import com.google.firebase.database.Query
import com.projects.venom04.chaton.R
import com.projects.venom04.chaton.mvp.models.Chat
import com.projects.venom04.chaton.mvp.presenters.main.MainPresenter
import com.projects.venom04.chaton.mvp.presenters.main.MainView
import com.projects.venom04.chaton.mvp.views.fragments.AddChatDialogFragment
import com.projects.venom04.chaton.utils.DateHelper
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.find
import org.jetbrains.anko.longToast

class MainActivity : AppCompatActivity(), MainView, View.OnClickListener, AddChatDialogFragment.AddChatDialogListener {

    private lateinit var mMainPresenter: MainPresenter

    private lateinit var mAdapter: FirebaseListAdapter<Chat>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mMainPresenter = MainPresenter(this)
        mMainPresenter.loadChats()
        floatingButton_addChat.setOnClickListener(this)
    }

    override fun onStop() {
        super.onStop()
        mAdapter.stopListening()
    }

    override fun getAllChats(query: Query) {
        val options: FirebaseListOptions<Chat> = FirebaseListOptions.Builder<Chat>()
                .setLayout(R.layout.item_chat)
                .setQuery(query, Chat::class.java)
                .build()

        e(TAG, "getAlLChats " + query.toString())
        mAdapter = object : FirebaseListAdapter<Chat>(options) {
            override fun populateView(v: View?, chat: Chat?, position: Int) {
                e(TAG, chat.toString())
                val ivIcon = v?.find<ImageView>(R.id.imageView_icon)
                val tvTitle = v?.find<TextView>(R.id.textView_title)
                val tvLastMessage = v?.find<TextView>(R.id.textView_lastMessage)
                val tvSendAt = v?.find<TextView>(R.id.textView_sendAt)

                tvTitle?.text = chat?.name
                if (chat!!.chatMessageList.isNotEmpty()) {
                    val lastMessage = chat.chatMessageList.last()
                    tvLastMessage?.text = lastMessage.message
                    tvSendAt?.text = lastMessage.sendAt.let { DateHelper.longToDate(it, "dd/MM/yyyy") }
                }
            }
        }
        listView_chats.adapter = mAdapter
        mAdapter.startListening()
    }

    override fun onAddingChat(name: String) {
        mMainPresenter.addNewChat(name)
    }

    private fun openAddChatDialog() {
        val dialogFragment = AddChatDialogFragment()
        dialogFragment.show(fragmentManager, "fragment_addchat")
    }

    override fun showMessage(message: Int) {
        longToast(message)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.floatingButton_addChat -> {
                openAddChatDialog()
            }
        }
    }

    companion object {
        val TAG = "MainActivity"
    }
}

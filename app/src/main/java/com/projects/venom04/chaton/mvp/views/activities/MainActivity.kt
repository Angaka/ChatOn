package com.projects.venom04.chaton.mvp.views.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.widget.AdapterView
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
import com.projects.venom04.chaton.utils.Constants
import com.projects.venom04.chaton.utils.DateHelper
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.find
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.longToast
import org.jetbrains.anko.singleTop

class MainActivity : AppCompatActivity(), MainView, View.OnClickListener, AddChatDialogFragment.AddChatDialogListener, AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {

    private lateinit var mMainPresenter: MainPresenter

    private lateinit var mAdapter: FirebaseListAdapter<Chat>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mMainPresenter = MainPresenter(this)
        mMainPresenter.loadChats()
        floatingButton_addChat.setOnClickListener(this)
    }

    override fun onStart() {
        super.onStart()
        mAdapter.startListening()
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

        mAdapter = object : FirebaseListAdapter<Chat>(options) {
            override fun populateView(v: View?, chat: Chat?, position: Int) {
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
        listView_chats.onItemClickListener = this
        listView_chats.onItemLongClickListener = this
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

    override fun onItemClick(adapterView: AdapterView<*>?, v: View?, position: Int, p3: Long) {
        val childId = mAdapter.getRef(position)
        startActivity(intentFor<ChatActivity>(Constants.CHILD_ID to childId).singleTop())
    }

    override fun onItemLongClick(adapterView: AdapterView<*>?, v: View?, position: Int, p3: Long): Boolean {
        return true
    }

    companion object {
        val TAG = "MainActivity"
    }
}

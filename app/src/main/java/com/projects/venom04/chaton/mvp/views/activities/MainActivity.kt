package com.projects.venom04.chaton.mvp.views.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.LinearLayout
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
import org.jetbrains.anko.*
import java.util.*

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

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.settings -> {
                startActivity<ProfileActivity>()
            }
            R.id.logout -> {
                mMainPresenter.logout()
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
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
                    tvLastMessage?.visibility = TextView.VISIBLE
                    tvSendAt?.visibility = TextView.VISIBLE

                    val lastKey: String = chat.chatMessageList.keys.max()!!
                    val lastMessage = chat.chatMessageList[lastKey]
                    tvLastMessage?.text = lastMessage!!.message

                    var lastSendAt = DateHelper.longToDate(lastMessage.sendAt, "dd/MM/yyyy")
                    val todayDate = DateHelper.longToDate(Date().time, "dd/MM/yyyy")
                    if (lastSendAt.equals(todayDate))
                        lastSendAt = DateHelper.longToDate(lastMessage.sendAt, "hh:mm")
                    tvSendAt?.text = lastSendAt
                } else {
                    tvLastMessage?.visibility = TextView.GONE
                    tvSendAt?.visibility = TextView.GONE
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
        val chat: Chat = mAdapter.getItem(position)
        val childId = mAdapter.getRef(position).key
        startActivity(intentFor<ChatActivity>(Constants.CHAT to chat, Constants.CHILD_ID to childId).singleTop())
    }

    override fun onItemLongClick(adapterView: AdapterView<*>?, v: View?, position: Int, p3: Long): Boolean {
        return true
    }

    companion object {
        val TAG = "MainActivity"
    }
}

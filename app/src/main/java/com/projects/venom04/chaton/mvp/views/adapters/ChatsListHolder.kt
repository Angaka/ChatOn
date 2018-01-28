package com.projects.venom04.chaton.mvp.views.adapters

import android.support.v7.widget.RecyclerView
import android.view.View
import com.projects.venom04.chaton.mvp.models.Chat
import com.projects.venom04.chaton.utils.DateHelper
import kotlinx.android.synthetic.main.item_chat.view.*

/**
 * Created by Venom on 28/01/2018.
 */
class ChatsListHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {

    private lateinit var mListener : ChatListListener

    fun setListener(listener: ChatListListener) {
        mListener = listener
    }

    fun bind(chat: Chat) = with(itemView) {
        //            itemView.imageView_icon.setImage(chat.urlIcon)
        itemView.textView_title.text = chat.name

        val lastMessage = chat.chatMessageList.last()
        itemView.textView_lastMessage.text = lastMessage.message
        itemView.textView_sendAt.text = DateHelper.longToDate(lastMessage.sendAt, "dd/MM/yyyy")
        setOnClickListener { mListener.onSelectedChat(chat) }
    }

    interface ChatListListener {
        fun onSelectedChat(chat: Chat)
    }
}
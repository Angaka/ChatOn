package com.projects.venom04.chaton.mvp.views.adapters

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.projects.venom04.chaton.R
import com.projects.venom04.chaton.extensions.inflate
import com.projects.venom04.chaton.mvp.models.Chat
import com.projects.venom04.chaton.utils.DateHelper
import kotlinx.android.synthetic.main.item_chat.view.*

/**
 * Created by Venom on 28/01/2018.
 */
class ChatsListAdapter(val options: FirebaseRecyclerOptions<Chat>, val chatsList: ArrayList<Chat>, val listener: (Chat) -> Unit) : FirebaseRecyclerAdapter<Chat, ChatsListAdapter.ViewHolder>(options) {


    override fun onBindViewHolder(holder: ViewHolder, position: Int, model: Chat) {
        val chat = chatsList[position]
        holder.bind(chat, listener)
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val view = parent?.inflate(R.layout.item_chat, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = chatsList.size

    class ViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
        fun bind(chat: Chat, listener: (Chat) -> Unit) = with(itemView) {
//            itemView.imageView_icon.setImage(chat.urlIcon)
            itemView.textView_title.text = chat.name

            val lastMessage = chat.chatMessageList.last()
            itemView.textView_lastMessage.text = lastMessage.message
            itemView.textView_sendAt.text = DateHelper.longToDate(lastMessage.sendAt, "dd/MM/yyyy")
            setOnClickListener { listener(chat) }
        }
    }
}
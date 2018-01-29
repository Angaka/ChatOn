package com.projects.venom04.chaton.mvp.views.adapters

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.projects.venom04.chaton.R
import com.projects.venom04.chaton.mvp.models.ChatMessage
import com.projects.venom04.chaton.utils.DateHelper
import org.jetbrains.anko.find

/**
 * Created by beau-oudong on 29/01/2018.
 */
class ChatHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(chatMessage: ChatMessage) {
        with (chatMessage) {
            val tvMessage = itemView.find<TextView>(R.id.textView_message)
            val tvSendAt = itemView.find<TextView>(R.id.textView_sendAt)

            if (!FirebaseAuth.getInstance().currentUser?.displayName.equals(chatMessage.user)) {
                val tvUser = itemView.find<TextView>(R.id.textView_user)
                tvUser.text = chatMessage.user
            }
            tvMessage.text = chatMessage.message
            tvSendAt.text = DateHelper.longToDate(chatMessage.sendAt, "hh:mm")
        }
    }
}
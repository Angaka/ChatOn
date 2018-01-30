package com.projects.venom04.chaton.mvp.presenters.chat

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.projects.venom04.chaton.mvp.models.ChatMessage

/**
 * Created by beau-oudong on 29/01/2018.
 */
class ChatPresenter(chatView: ChatView, childId: String) {

    private var mChatView: ChatView
    private var mFirebaseAuth: FirebaseAuth
    private var mFirebaseDb: DatabaseReference

    init {
        mChatView = chatView
        mFirebaseAuth = FirebaseAuth.getInstance()
        mFirebaseDb = FirebaseDatabase.getInstance()
                .reference
                .child("chats")
                .child(childId)
                .child("chatMessageList")
    }

    fun loadChatMessages() {
        val query = mFirebaseDb.orderByKey()
        mChatView.loadChat(query)
    }

    fun sendMessage(message: String) {
        mFirebaseDb.push().setValue(ChatMessage(mFirebaseAuth.currentUser?.uid!!, mFirebaseAuth.currentUser?.displayName!!, message))
    }
}
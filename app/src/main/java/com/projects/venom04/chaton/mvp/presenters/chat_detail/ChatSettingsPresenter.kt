package com.projects.venom04.chaton.mvp.presenters.chat_detail

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.projects.venom04.chaton.R

/**
 * Created by beau-oudong on 31/01/2018.
 */
class ChatSettingsPresenter(chatSettingsView: ChatSettingsView, childId: String) {

    private var mChatSettingsView: ChatSettingsView
    private var mFirebaseAuth: FirebaseAuth
    private var mFirebaseDb: DatabaseReference

    init {
        mChatSettingsView = chatSettingsView
        mFirebaseAuth = FirebaseAuth.getInstance()
        mFirebaseDb = FirebaseDatabase.getInstance()
                .reference
                .child("chats")
                .child(childId)
    }

    fun loadChatInfos() {
        val query = mFirebaseDb.orderByKey()
        mChatSettingsView.loadChatInfos(query)
    }

    fun updateChatInfos(name: String) {
        mFirebaseDb.child("name").setValue(name)
    }

    fun deleteChat() {
        mFirebaseDb.removeValue()
        mChatSettingsView.showMessage(R.string.chat_settings_chat_deleted)
    }
}
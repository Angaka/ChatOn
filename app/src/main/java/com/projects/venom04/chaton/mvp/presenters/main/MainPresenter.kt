package com.projects.venom04.chaton.mvp.presenters.main

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.projects.venom04.chaton.R
import com.projects.venom04.chaton.mvp.models.Chat

/**
 * Created by Venom on 27/01/2018.
 */
class MainPresenter(mainView: MainView) {

    private var mMainView: MainView
    private var mFirebaseAuth: FirebaseAuth
    private var mFirebaseDb: DatabaseReference

    init {
        mMainView = mainView
        mFirebaseAuth = FirebaseAuth.getInstance()
        mFirebaseDb = FirebaseDatabase.getInstance()
                .reference
                .child("chats")
    }

    fun loadChats() {
        val query = mFirebaseDb.orderByKey()
        mMainView.getAllChats(query)
    }

    fun loadChat(chatId: String) {
        mFirebaseDb.child(chatId).orderByKey()
    }

    fun addNewChat(name: String) {
        mFirebaseDb.push().setValue(Chat(name, "icon"))
        mMainView.showMessage(R.string.chat_added)
    }

    fun logout() {
        mFirebaseAuth.signOut()
    }
}
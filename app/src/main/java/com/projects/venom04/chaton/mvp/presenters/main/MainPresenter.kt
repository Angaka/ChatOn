package com.projects.venom04.chaton.mvp.presenters.main

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

/**
 * Created by Venom on 27/01/2018.
 */
class MainPresenter(mainView: MainView) {

    private var mMainView: MainView = mainView
    private var mFirebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private var mFirebaseDb: DatabaseReference = FirebaseDatabase.getInstance().reference

    fun loadChats() {
        val query = mFirebaseDb.orderByKey()
        mMainView.getAllChats(query)
    }

    fun loadChat(chatId: String) {
        mFirebaseDb.child(chatId)
    }
}
package com.projects.venom04.chaton.mvp.presenters.chat_settings

import android.util.Log.e
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.projects.venom04.chaton.R

/**
 * Created by beau-oudong on 31/01/2018.
 */
class ChatSettingsPresenter(chatSettingsView: ChatSettingsView, childId: String) {

    private var mChatSettingsView: ChatSettingsView
    private var mFirebaseAuth: FirebaseAuth
    private var mFirebaseStorage: StorageReference
    private var mFirebaseDb: DatabaseReference

    init {
        mChatSettingsView = chatSettingsView
        mFirebaseAuth = FirebaseAuth.getInstance()
        mFirebaseStorage = FirebaseStorage.getInstance()
                .reference
                .child("chats")
                .child(childId)
        mFirebaseDb = FirebaseDatabase.getInstance()
                .reference
                .child("chats")
                .child(childId)
    }

    fun loadChatInfos() {
        val query = mFirebaseDb.orderByKey()
        mChatSettingsView.loadChatInfos(query)
    }

    fun updateCover(coverBytes: ByteArray?) {
        val coverPath = mFirebaseStorage.child("coverUrl").putBytes(coverBytes!!)
        coverPath.addOnSuccessListener {
            mFirebaseDb.child("coverUrl").setValue(it.downloadUrl.toString())
            e(TAG, it.downloadUrl.toString())
        }
    }

    fun updatePicture(pictureBytes: ByteArray?) {
        val picturePath = mFirebaseStorage.child("pictureUrl").putBytes(pictureBytes!!)
        picturePath.addOnSuccessListener {
            e(TAG, it.downloadUrl.toString())
            mFirebaseDb.child("pictureUrl").setValue(it.downloadUrl.toString())
        }
    }

    fun updateChatInfos(name: String, description: String) {
        mFirebaseDb.child("name").setValue(name)
        mFirebaseDb.child("description").setValue(description)
        mChatSettingsView.showMessage(R.string.informations_updated)
    }

    fun deleteChat() {
        mFirebaseStorage.child("pictureUrl").delete()
        mFirebaseStorage.child("coverUrl").delete()
        mFirebaseDb.removeValue()
        mChatSettingsView.showMessage(R.string.chat_settings_chat_deleted)
    }

    companion object {
        private val TAG = "ChatSettingsPres"
    }
}
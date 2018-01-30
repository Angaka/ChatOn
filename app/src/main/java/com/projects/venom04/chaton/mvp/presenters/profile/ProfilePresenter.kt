package com.projects.venom04.chaton.mvp.presenters.profile

import com.google.firebase.auth.FirebaseAuth

/**
 * Created by beau-oudong on 30/01/2018.
 */
class ProfilePresenter(profileView: ProfileView) {

    private var mProfileView: ProfileView = profileView
    private var mFirebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()

    fun getUserInfos() {
        mProfileView.loadUserInfos(mFirebaseAuth.currentUser)
    }

    fun updateInformations() {

    }
}
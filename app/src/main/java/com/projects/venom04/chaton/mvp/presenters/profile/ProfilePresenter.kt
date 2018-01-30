package com.projects.venom04.chaton.mvp.presenters.profile

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.projects.venom04.chaton.R
import com.projects.venom04.chaton.utils.InputHelper

/**
 * Created by beau-oudong on 30/01/2018.
 */
class ProfilePresenter(profileView: ProfileView) {

    private var mProfileView: ProfileView = profileView
    private var mFirebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()

    fun getUserInfos() {
        mProfileView.loadUserInfos(mFirebaseAuth.currentUser)
    }

    fun updateInformations(username: String, email: String, password: String, confirmPassword: String) {
        if (!username.trim().isEmpty()) {
            val userProfileChangeRequest = UserProfileChangeRequest.Builder()
                    .setDisplayName(username)
                    .build()
            mFirebaseAuth.currentUser?.updateProfile(userProfileChangeRequest)
        } else
            mProfileView.showMessage(R.string.login_invalid_username)

        if (InputHelper.validateEmail(email))
            mFirebaseAuth.currentUser?.updateEmail(email)
        else
            mProfileView.showMessage(R.string.login_invalid_email)

        if (InputHelper.validatePassword(password) && InputHelper.comparePassword(password, confirmPassword))
            mFirebaseAuth.currentUser?.updatePassword(password)
        else
            mProfileView.showMessage(R.string.login_invalid_password)
    }
}
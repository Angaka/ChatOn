package com.projects.venom04.chaton.mvp.presenters.profile

import com.google.firebase.auth.FirebaseUser
import com.projects.venom04.chaton.mvp.presenters.BaseView

/**
 * Created by beau-oudong on 30/01/2018.
 */
interface ProfileView: BaseView {
    fun loadUserInfos(currentUser: FirebaseUser?)
//    fun showRequiredFields()
}
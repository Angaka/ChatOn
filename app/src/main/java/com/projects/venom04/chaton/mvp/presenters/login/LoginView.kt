package com.projects.venom04.chaton.mvp.presenters.login

import com.projects.venom04.chaton.mvp.presenters.BaseView

/**
 * Created by Venom on 27/01/2018.
 */
interface LoginView : BaseView {
    fun onSuccessfulConnection()
}
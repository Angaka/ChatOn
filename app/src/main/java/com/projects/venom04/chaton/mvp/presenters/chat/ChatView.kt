package com.projects.venom04.chaton.mvp.presenters.chat

import com.google.firebase.database.Query
import com.projects.venom04.chaton.mvp.presenters.BaseView

/**
 * Created by beau-oudong on 29/01/2018.
 */
interface ChatView : BaseView {
    fun loadChat(query: Query)
}
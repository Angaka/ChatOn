package com.projects.venom04.chaton.mvp.presenters.chat_detail

import com.google.firebase.database.Query
import com.projects.venom04.chaton.mvp.presenters.BaseView

/**
 * Created by beau-oudong on 31/01/2018.
 */
interface ChatSettingsView : BaseView {
    fun loadChatInfos(query: Query)
}
package com.projects.venom04.chaton.mvp.presenters.main

import com.google.firebase.database.Query
import com.projects.venom04.chaton.mvp.presenters.BaseView

/**
 * Created by Venom on 27/01/2018.
 */
interface MainView: BaseView {
    fun getAllChats(query: Query)
}
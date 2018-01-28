package com.projects.venom04.chaton.mvp.presenters.main

import com.google.firebase.database.Query

/**
 * Created by Venom on 27/01/2018.
 */
interface MainView {
    fun getAllChats(query: Query)
}
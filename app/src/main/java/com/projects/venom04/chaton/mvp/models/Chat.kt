package com.projects.venom04.chaton.mvp.models

import java.util.*

/**
 * Created by Venom on 28/01/2018.
 */
class Chat(val name: String = "", val urlIcon: String = "", val chatMessageList : ArrayList<ChatMessage> = ArrayList(), val createdAt : Long = Date().time) {
}
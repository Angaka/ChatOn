package com.projects.venom04.chaton.mvp.models

import java.util.*
import kotlin.collections.HashMap

/**
 * Created by Venom on 28/01/2018.
 */
class Chat(val name: String = "", val urlIcon: String = "", val chatMessageList : HashMap<String, ChatMessage> = HashMap(), val createdAt : Long = Date().time) {
    //Expected a List while deserializing, but got a class java.util.HashMap
}
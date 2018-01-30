package com.projects.venom04.chaton.mvp.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*
import kotlin.collections.HashMap

//import kotlinx.serialization.*

/**
 * Created by Venom on 28/01/2018.
 */
@Parcelize
data class Chat(val name: String = "", val urlIcon: String = "", val chatMessageList: HashMap<String, ChatMessage> = HashMap(), val createdAt: Long = Date().time) : Parcelable {
    //Expected a List while deserializing, but got a class java.util.HashMap
}
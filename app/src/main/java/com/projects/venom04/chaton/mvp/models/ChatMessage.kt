package com.projects.venom04.chaton.mvp.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

/**
 * Created by Venom on 28/01/2018.
 */
@Parcelize
data class ChatMessage(var userId: String = "", var user: String = "", var message: String = "", var sendAt: Long = Date().time) : Parcelable
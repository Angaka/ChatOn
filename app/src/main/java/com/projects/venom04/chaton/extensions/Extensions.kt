package com.projects.venom04.chaton.extensions

import android.app.Dialog
import android.support.annotation.LayoutRes
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * Created by Venom on 27/01/2018.
 */

internal fun Dialog.showDialog() {
    if (!isShowing) {
        show()
    }
}

internal fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false) : View {
    return LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)
}
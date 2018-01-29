package com.projects.venom04.chaton.extensions

import android.app.Dialog
import android.support.annotation.LayoutRes
import android.support.design.widget.TextInputEditText
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import com.projects.venom04.chaton.R

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

internal fun EditText.setAsRequired() {
    if (text.trim().toString().isEmpty())
        error = context.getString(R.string.required_field)
}
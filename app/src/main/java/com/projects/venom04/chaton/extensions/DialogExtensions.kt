package com.projects.venom04.chaton.extensions

import android.app.Dialog

/**
 * Created by Venom on 27/01/2018.
 */

internal inline fun Dialog.showDialog() {
    if (!isShowing) {
        show()
    }
}
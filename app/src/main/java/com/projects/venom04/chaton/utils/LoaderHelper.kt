package com.projects.venom04.chaton.utils

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import com.projects.venom04.chaton.R

/**
 * Created by beau-oudong on 02/02/2018.
 */
class LoaderHelper {

    companion object {
        private lateinit var mDialogLoader: Dialog

        fun showLoader(context: Context) {
            mDialogLoader = Dialog(context)
            mDialogLoader.setContentView(R.layout.view_loader)
            mDialogLoader.setCanceledOnTouchOutside(false)
            mDialogLoader.window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

            mDialogLoader.show()
        }

        fun hideLoader() {
            if (mDialogLoader.isShowing)
                mDialogLoader.hide()
        }
    }
}
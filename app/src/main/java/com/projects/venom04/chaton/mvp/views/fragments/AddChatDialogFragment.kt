package com.projects.venom04.chaton.mvp.views.fragments

import android.app.AlertDialog
import android.app.Dialog
import android.app.DialogFragment
import android.content.Context
import android.os.Bundle
import android.support.design.widget.TextInputLayout
import android.view.View
import android.widget.Button
import com.projects.venom04.chaton.R
import com.projects.venom04.chaton.extensions.setAsRequired
import com.projects.venom04.chaton.utils.InputHelper
import org.jetbrains.anko.find


/**
 * Created by Venom on 27/01/2018.
 */
class AddChatDialogFragment : DialogFragment(), View.OnClickListener {

    private lateinit var mInputName: TextInputLayout
    private lateinit var mBtnSubmit: Button
    private lateinit var mBtnClose: Button

    private var listener: AddChatDialogListener? = null

    interface AddChatDialogListener {
        fun onAddingChat(name: String)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is AddChatDialogListener) {
            listener = context
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view = activity.layoutInflater.inflate(R.layout.fragment_dialog_addchat, null)

        mInputName = view.find(R.id.textInputLayout_name)

        mBtnSubmit = view.find(R.id.button_submit)
        mBtnSubmit.setOnClickListener(this)
        mBtnClose = view.find(R.id.button_close)
        mBtnClose.setOnClickListener(this)

        return AlertDialog.Builder(activity, R.style.AppTheme_Dialog)
                .setView(view)
                .create()
                .apply {
                    setCanceledOnTouchOutside(false)
                }
    }

    override fun onResume() {
        val width = resources.getDimensionPixelSize(R.dimen.dialog_width)
        val height = resources.getDimensionPixelSize(R.dimen.dialog_height)
        dialog.window.setLayout(width, height)
        // Call super onResume after sizing
        super.onResume()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.button_submit -> {
                val name = mInputName.editText?.text.toString()

                if (!name.trim().isEmpty()) {
                    listener?.onAddingChat(name)
                    dismiss()
                } else {
                    mInputName.editText?.setAsRequired()
                }
            }
            R.id.button_close -> {
                dismiss()
            }
        }
    }
}
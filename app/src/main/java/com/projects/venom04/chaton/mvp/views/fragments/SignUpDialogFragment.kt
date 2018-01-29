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
import org.jetbrains.anko.find



/**
 * Created by Venom on 27/01/2018.
 */
class SignUpDialogFragment : DialogFragment(), View.OnClickListener {

    private lateinit var mInputEmail : TextInputLayout
    private lateinit var mInputUsername : TextInputLayout
    private lateinit var mInputPassword : TextInputLayout
    private lateinit var mInputRecheckPassword : TextInputLayout
    private lateinit var mBtnSubmit : Button
    private lateinit var mBtnClose : Button

    private var listener : SignUpDialogListener? = null

    interface SignUpDialogListener {
        fun onSignUp(email: String, username: String, password: String, recheckPassword: String)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is SignUpDialogListener) {
            listener = context
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view = activity.layoutInflater.inflate(R.layout.fragment_dialog_signup, null)

        mInputEmail = view.find(R.id.textInputLayout_email)
        mInputUsername = view.find(R.id.textInputLayout_username)
        mInputPassword = view.find(R.id.textInputLayout_password)
        mInputRecheckPassword = view.find(R.id.textInputLayout_recheck_password)

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
        when(v?.id) {
            R.id.button_submit -> {
                val email = mInputEmail.editText?.text.toString()
                val username = mInputUsername.editText?.text.toString()
                val password = mInputPassword.editText?.text.toString()
                val recheckPassword = mInputRecheckPassword.editText?.text.toString()

                listener?.onSignUp(email, username, password, recheckPassword)
                dismiss()
            }
            R.id.button_close -> {
                dismiss()
            }
        }
    }
}
package com.projects.venom04.chaton.utils

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

/**
 * Created by Venom on 27/01/2018.
 */
class InputHelper {
    companion object {
        fun validateEmail(email: String) : Boolean {
            if (email.trim().isEmpty())
                return false
            return true
        }

        fun validatePassword(password: String) : Boolean {
            if (password.trim().isEmpty())
                return false
            if (password.length < 6)
                return false
            return true
        }

        fun comparePassword(password: String, recheckPassword: String) : Boolean {
            return password.equals(recheckPassword)
        }

        fun hideKeyboard(v: View) {
            val imm = v.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(v.windowToken, 0)
        }
    }
}
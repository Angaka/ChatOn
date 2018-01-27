package com.projects.venom04.chaton.utils

/**
 * Created by Venom on 27/01/2018.
 */
class InputUtils {
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
    }
}
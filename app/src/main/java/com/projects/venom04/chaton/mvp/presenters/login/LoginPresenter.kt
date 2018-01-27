package com.projects.venom04.chaton.mvp.presenters.login

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.projects.venom04.chaton.R
import com.projects.venom04.chaton.utils.InputUtils

/**
 * Created by Venom on 27/01/2018.
 */
class LoginPresenter(loginView: LoginView) {

    private var mLoginView: LoginView = loginView
    private var mFirebaseAuth : FirebaseAuth = FirebaseAuth.getInstance()

    fun signIn(email: String, password: String) {
        if (!InputUtils.validateEmail(email))
            mLoginView.showMessage(R.string.login_invalid_email)
        else if (!InputUtils.validatePassword(password))
            mLoginView.showMessage(R.string.login_invalid_password)
        else {
            mFirebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task: Task<AuthResult> ->
                if (task.isSuccessful) {
                    mLoginView.onSuccessfulConnection()
                    mLoginView.showMessage(R.string.login_successful)
                } else {
                    mLoginView.showMessage(R.string.login_failed)
                }
            }
        }
    }

    fun signUp(email: String, password: String) {
        if (!InputUtils.validateEmail(email))
            mLoginView.showMessage(R.string.login_invalid_email)
        else if (!InputUtils.validatePassword(password))
            mLoginView.showMessage(R.string.login_invalid_password)
        else {
            mFirebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task: Task<AuthResult> ->
                if (task.isSuccessful) {
                    mLoginView.onSuccessfulRegistration()
                    mLoginView.showMessage(R.string.login_successful)
                } else {
                    mLoginView.showMessage(R.string.login_failed)
                }
            }
        }
    }
}
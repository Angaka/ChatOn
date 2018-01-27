package com.projects.venom04.chaton.mvp.views.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.projects.venom04.chaton.R
import com.projects.venom04.chaton.mvp.presenters.login.LoginPresenter
import com.projects.venom04.chaton.mvp.presenters.login.LoginView
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.longToast
import org.jetbrains.anko.startActivity

/**
 * Created by Venom on 27/01/2018.
 */
class LoginActivity : AppCompatActivity(), View.OnClickListener, LoginView {

    private lateinit var mLoginPresenter : LoginPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        mLoginPresenter = LoginPresenter(this)

        button_connect.setOnClickListener(this)
    }

    private fun submit() {
        val email = textInputLayout_email.editText?.text.toString()
        val password = textInputLayout_password.editText?.text.toString()

        mLoginPresenter.signIn(email, password)
    }

    override fun onSuccessfulConnection() {
        startActivity<MainActivity>()
    }

    override fun showMessage(message: Int) {
        longToast(message)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            button_connect.id -> {
                submit()
            }
        }
    }
}
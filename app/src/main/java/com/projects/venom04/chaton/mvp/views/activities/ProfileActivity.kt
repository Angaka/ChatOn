package com.projects.venom04.chaton.mvp.views.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.view.View
import com.google.firebase.auth.FirebaseUser
import com.projects.venom04.chaton.R
import com.projects.venom04.chaton.mvp.presenters.profile.ProfilePresenter
import com.projects.venom04.chaton.mvp.presenters.profile.ProfileView
import kotlinx.android.synthetic.main.activity_profile.*
import org.jetbrains.anko.toast

/**
 * Created by beau-oudong on 30/01/2018.
 */
class ProfileActivity : AppCompatActivity(), ProfileView, View.OnClickListener {

    private lateinit var mProfilePresenter: ProfilePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        supportActionBar?.elevation = 0f
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        mProfilePresenter = ProfilePresenter(this)
        mProfilePresenter.getUserInfos()

        button_update_informations.setOnClickListener(this)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun loadUserInfos(currentUser: FirebaseUser?) {
        textInputLayout_profile_username.editText?.setText(currentUser?.displayName)
        textInputLayout_profile_email.editText?.setText(currentUser?.email)
    }

    private fun updateInformations() {
        val username = textInputLayout_profile_username.editText?.text.toString()
        val email = textInputLayout_profile_email.editText?.text.toString()
        val password = textInputLayout_profile_password.editText?.text.toString()
        val confirmPassword = textInputLayout_profile_confirm_password.editText?.text.toString()


    }

    override fun showMessage(message: Int) {
        toast(message)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.button_update_informations -> {
                updateInformations()
            }
        }
    }

    companion object {
        val TAG = "ProfileActivity"
    }
}
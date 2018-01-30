package com.projects.venom04.chaton.mvp.views.activities

import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log.e
import android.view.MenuItem
import android.view.View
import com.google.firebase.auth.FirebaseUser
import com.projects.venom04.chaton.R
import com.projects.venom04.chaton.mvp.presenters.profile.ProfilePresenter
import com.projects.venom04.chaton.mvp.presenters.profile.ProfileView
import kotlinx.android.synthetic.main.activity_profile.*
import org.jetbrains.anko.backgroundDrawable
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

        imageButton_profile_picture.setOnClickListener(this)
        textView_profile_cover.setOnClickListener(this)
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            PICK_PICTURE -> {
                when (resultCode) {
                    Activity.RESULT_OK -> {
                        try {
                            val inputStream = contentResolver.openInputStream(data?.data)
                            val bitmap = BitmapFactory.decodeStream(inputStream)
                            imageButton_profile_picture.setImageBitmap(bitmap)
                        } catch (exception: Exception) {
                            e(TAG, exception.message)
                        }
                    }
                }
            }
            PICK_COVER -> {
                when (resultCode) {
                    Activity.RESULT_OK -> {
                        try {
                            val inputStream = contentResolver.openInputStream(data?.data)
                            val bitmap = BitmapFactory.decodeStream(inputStream)
                            val drawable = BitmapDrawable(resources, bitmap)
                            linearLayout_profile_cover.backgroundDrawable = drawable
                        } catch (exception: Exception) {
                            e(TAG, exception.message)
                        }
                    }
                }
            }
        }
    }

    override fun loadUserInfos(currentUser: FirebaseUser?) {
        textInputLayout_profile_username.editText?.setText(currentUser?.displayName)
        textInputLayout_profile_email.editText?.setText(currentUser?.email)
    }

    private fun pickProfilePicture() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        startActivityForResult(Intent.createChooser(intent, "Select picture"), PICK_PICTURE)
    }

    private fun pickProfileCover() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        startActivityForResult(Intent.createChooser(intent, "Select picture"), PICK_COVER)
    }

    private fun updateInformations() {
        val username = textInputLayout_profile_username.editText?.text.toString()
        val email = textInputLayout_profile_email.editText?.text.toString()
        val password = textInputLayout_profile_password.editText?.text.toString()
        val confirmPassword = textInputLayout_profile_confirm_password.editText?.text.toString()

        mProfilePresenter.updateInformations(username, email, password, confirmPassword)
    }

    override fun showMessage(message: Int) {
        toast(message)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.button_update_informations -> {
                updateInformations()
            }
            R.id.imageButton_profile_picture -> {
                pickProfilePicture()
            }
            R.id.textView_profile_cover -> {
                pickProfileCover()
            }
        }
    }

    companion object {
        private val TAG = "ProfileActivity"
        private val PICK_PICTURE = 0
        private val PICK_COVER = 1
    }
}
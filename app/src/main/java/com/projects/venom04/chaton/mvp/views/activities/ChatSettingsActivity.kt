package com.projects.venom04.chaton.mvp.views.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.bumptech.glide.Glide
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.Query
import com.google.firebase.database.ValueEventListener
import com.projects.venom04.chaton.R
import com.projects.venom04.chaton.mvp.presenters.chat_settings.ChatSettingsPresenter
import com.projects.venom04.chaton.mvp.presenters.chat_settings.ChatSettingsView
import com.projects.venom04.chaton.utils.Constants
import com.projects.venom04.chaton.utils.Constants.Companion.PICK_COVER
import com.projects.venom04.chaton.utils.Constants.Companion.PICK_PICTURE
import com.projects.venom04.chaton.utils.DateHelper
import com.projects.venom04.chaton.utils.GlideApp
import com.projects.venom04.chaton.utils.ImageHelper
import kotlinx.android.synthetic.main.activity_chat_settings.*
import org.jetbrains.anko.longToast

/**
 * Created by beau-oudong on 31/01/2018.
 */
class ChatSettingsActivity : AppCompatActivity(), ChatSettingsView, View.OnClickListener {

    private lateinit var mChatSettingsPresenter: ChatSettingsPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_settings)

        setSupportActionBar(toolbar_chat_settings)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        val childId = intent.extras.getString(Constants.CHILD_ID)
        mChatSettingsPresenter = ChatSettingsPresenter(this, childId)

        mChatSettingsPresenter.loadChatInfos()

        textView_chat_settings_picture.setOnClickListener(this)
        button_update_informations.setOnClickListener(this)
        button_delete_and_quit.setOnClickListener(this)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_chat_settings, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> {
                finish()
            }
            R.id.edit_cover -> {
                startActivityForResult(Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI), PICK_COVER)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            PICK_COVER -> {
                when (resultCode) {
                    Activity.RESULT_OK -> {
                        imageView_chat_settings_cover.setImageURI(data?.data)
                        imageView_chat_settings_cover.buildDrawingCache()
                        val coverBytes = ImageHelper.encodeImageToByteArray(imageView_chat_settings_cover.drawingCache)
                        mChatSettingsPresenter.updateCover(coverBytes)
                    }
                }
            }
            PICK_PICTURE -> {
                when (resultCode) {
                    Activity.RESULT_OK -> {
                        imageView_chat_settings_picture.setImageURI(data?.data)
                        imageView_chat_settings_picture.buildDrawingCache()
                        val pictureBytes = ImageHelper.encodeImageToByteArray(imageView_chat_settings_picture.drawingCache)
                        mChatSettingsPresenter.updatePicture(pictureBytes)
                    }
                }
            }
        }
    }

    override fun showMessage(message: Int) {
        longToast(message)
    }

    override fun loadChatInfos(query: Query) {
        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(databaseError: DatabaseError?) {
                longToast(databaseError!!.message)
            }

            override fun onDataChange(dataSnapshot: DataSnapshot?) {
                if (dataSnapshot!!.exists()) {
                    val name = dataSnapshot.child("name").value.toString()
                    val description = dataSnapshot.child("description").value.toString()
                    val createdAt = dataSnapshot.child("createdAt").value as Long
                    val formattedCreatedAt = getString(R.string.created_at)
                    val coverUrl = dataSnapshot.child("coverUrl").value.toString()
                    val pictureUrl = dataSnapshot.child("pictureUrl").value.toString()

                    collapsingToolbarLayout_chat_settings.title = name
                    collapsingToolbarLayout_chat_settings.subtitle = String.format(formattedCreatedAt, DateHelper.longToDate(createdAt, "dd/MM/yyyy hh:mm"))

                    textInputLayout_chat_settings_name.editText?.setText(name)
                    textInputLayout_chat_settings_desc.editText?.setText(description)
                    if (coverUrl.trim().isNotEmpty()) {
                        GlideApp.with(applicationContext)
                                .asDrawable()
                                .centerCrop()
                                .load(coverUrl)
                                .into(imageView_chat_settings_cover)
                    }
                    if (pictureUrl.trim().isNotEmpty()) {
                        GlideApp.with(applicationContext)
                                .asDrawable()
                                .centerCrop()
                                .load(pictureUrl)
                                .into(imageView_chat_settings_picture)
                    }
                }
            }
        })
    }

    private fun updateInformations() {
        val name = textInputLayout_chat_settings_name.editText?.text.toString()
        val description = textInputLayout_chat_settings_desc.editText?.text.toString()

        mChatSettingsPresenter.updateChatInfos(name, description)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.textView_chat_settings_picture -> {
                startActivityForResult(Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI), PICK_PICTURE)
            }
            R.id.button_update_informations -> {
                updateInformations()
            }
            R.id.button_delete_and_quit -> {
                mChatSettingsPresenter.deleteChat()
                val resultIntent = Intent()
                resultIntent.putExtra(ChatActivity.IS_DELETED, true)
                setResult(Activity.RESULT_OK, resultIntent)
                finish()
            }
        }
    }

    companion object {
        private val TAG = "ChatSettings"
    }
}
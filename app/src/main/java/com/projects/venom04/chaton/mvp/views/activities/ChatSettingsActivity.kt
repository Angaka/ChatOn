package com.projects.venom04.chaton.mvp.views.activities

import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.Query
import com.google.firebase.database.ValueEventListener
import com.projects.venom04.chaton.R
import com.projects.venom04.chaton.mvp.presenters.chat_detail.ChatSettingsPresenter
import com.projects.venom04.chaton.mvp.presenters.chat_detail.ChatSettingsView
import com.projects.venom04.chaton.utils.Constants
import com.projects.venom04.chaton.utils.DateHelper
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
                val intent = Intent(Intent.ACTION_GET_CONTENT)
                intent.type = "image/*"
                startActivityForResult(Intent.createChooser(intent, "Select picture"), PICK_COVER)
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
                        try {
                            val inputStream = contentResolver.openInputStream(data?.data)
                            val bitmap = BitmapFactory.decodeStream(inputStream)
                            imageView_chat_settings_cover.setImageBitmap(bitmap)
                        } catch (exception: Exception) {
                            Log.e(TAG, exception.message)
                        }
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

                    collapsingToolbarLayout_chat_settings.title = name
                    collapsingToolbarLayout_chat_settings.subtitle = String.format(formattedCreatedAt, DateHelper.longToDate(createdAt, "dd/MM/yyyy hh:mm"))

                    textInputEditText_chat_settings_name.setText(name)
                    textInputEditText_chat_settings_desc.setText(description)
                }
            }
        })
    }

    override fun onClick(v: View?) {
        when (v?.id) {
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
        private val PICK_COVER = 0
    }
}
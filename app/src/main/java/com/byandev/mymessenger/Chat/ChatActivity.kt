package com.byandev.mymessenger.Chat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.byandev.mymessenger.Message.NewMessageActivity
import com.byandev.mymessenger.Model.ChatLeftItem
import com.byandev.mymessenger.Model.ChatRightItem
import com.byandev.mymessenger.Model.User
import com.byandev.mymessenger.R
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_chat.*

class ChatActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)


//        val userName = intent.getStringExtra(NewMessageActivity.USER_KEY)

        val user = intent.getParcelableExtra<User>(NewMessageActivity.USER_KEY)
        supportActionBar?.title = user.username

        val adapter = GroupAdapter<ViewHolder>()

        adapter.add(ChatLeftItem())
        adapter.add(ChatRightItem())
        adapter.add(ChatLeftItem())
        adapter.add(ChatRightItem())

        rvchat.adapter = adapter
    }
}


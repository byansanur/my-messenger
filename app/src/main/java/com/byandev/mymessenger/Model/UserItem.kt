package com.byandev.mymessenger.Model

import com.byandev.mymessenger.R
import com.squareup.picasso.Picasso
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.item_user.view.*

class UserItem(val user: User): Item<ViewHolder>() {
    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.textViewUserName.text = user.username

        Picasso.get().load(user.profileImageUrl).into(viewHolder.itemView.imageViewUser)
    }

    override fun getLayout(): Int {
        return R.layout.item_user
    }
}
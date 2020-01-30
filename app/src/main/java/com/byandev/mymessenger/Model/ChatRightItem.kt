package com.byandev.mymessenger.Model

import com.byandev.mymessenger.R
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder

class ChatRightItem: Item<ViewHolder>() {
    override fun bind(viewHolder: ViewHolder, position: Int) {

    }

    override fun getLayout(): Int {

        return R.layout.item_chat_row_right

    }
}
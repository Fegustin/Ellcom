package com.example.ellcom.adapter

import com.example.ellcom.R
import com.example.ellcom.pojo.session.SessionResult
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.item_session.*

class SessionItem(private val item: SessionResult.Data.Return) : Item() {
    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.textViewSessionStart.text = item.sessionStart
        viewHolder.textViewSessionStop.text = item.sessionStop
        viewHolder.textViewConnectionStart.text = item.connectionStart
        viewHolder.textViewIP.text = item.ipAddress
    }

    override fun getLayout(): Int = R.layout.item_session
}
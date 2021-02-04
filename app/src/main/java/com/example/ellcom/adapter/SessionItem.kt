package com.example.ellcom.adapter

import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.ellcom.R
import com.example.ellcom.pojo.session.SessionResult
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.item_session.*

class SessionItem(private val item: SessionResult.Data.Return) : Item() {
    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        if (position == 0) viewHolder.tableRowTitle.visibility = View.VISIBLE

        viewHolder.textViewSessionStart.text = item.sessionStart.substringBefore(" ")
        viewHolder.textViewSessionStop.text = item.sessionStop.substringBefore(" ")
        viewHolder.textViewConnectionStart.text = item.connectionStart.substringBefore(" ")
        viewHolder.textViewIP.text = item.ipAddress
        viewHolder.textViewMac.text = item.callingStationId
    }

    override fun getLayout(): Int = R.layout.item_session
}
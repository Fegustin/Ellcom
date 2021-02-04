package com.example.ellcom.adapter

import android.view.View
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
        viewHolder.textViewInputTraffic.text =
            String.format("%.1f", item.incomingTraffic * 0.00000762939453125)
        viewHolder.textViewOutPutTraffic.text =
            String.format("%.1f", item.outgoingTraffic * 0.00000762939453125)
    }

    override fun getLayout(): Int = R.layout.item_session
}
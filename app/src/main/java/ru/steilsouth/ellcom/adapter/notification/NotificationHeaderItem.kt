package ru.steilsouth.ellcom.adapter.notification

import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.item_header_notification.*
import ru.steilsouth.ellcom.R

class NotificationHeaderItem(private val title: String) : Item() {
    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.textViewContent.text = title
    }

    override fun getLayout(): Int = R.layout.item_header_notification
}
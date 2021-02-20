package ru.steilsouth.ellcom.adapter

import com.xwray.groupie.ExpandableGroup
import com.xwray.groupie.ExpandableItem
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.item_header_notification.*
import ru.steilsouth.ellcom.R

class NotificationHeaderItem(private val title: String) : Item(), ExpandableItem {
    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.textViewContent.text = title
    }

    override fun getLayout(): Int = R.layout.item_header_notification
    override fun setExpandableGroup(onToggleListener: ExpandableGroup) {}
}
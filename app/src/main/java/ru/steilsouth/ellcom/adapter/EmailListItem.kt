package ru.steilsouth.ellcom.adapter

import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.item_email_list.*
import ru.steilsouth.ellcom.R
import ru.steilsouth.ellcom.pojo.email.EmailAddress

class EmailListItem(private val item: EmailAddress) : Item() {
    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.textViewEmail.text = item.email
    }

    override fun getLayout(): Int = R.layout.item_email_list
}
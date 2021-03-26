package ru.steilsouth.ellcom.adapter

import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.item_bills.*
import ru.steilsouth.ellcom.R
import java.util.*

class BillsItem(
    private val price: String,
    private val service: String,
    private val date: String
) : Item() {
    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.textViewPrice.text = "$price ₽"
        viewHolder.textViewService.text = service.capitalize(Locale.getDefault())
        viewHolder.textViewDate.text = date
    }

    override fun getLayout(): Int = R.layout.item_bills
}
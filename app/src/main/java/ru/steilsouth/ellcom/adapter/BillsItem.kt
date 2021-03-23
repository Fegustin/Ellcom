package ru.steilsouth.ellcom.adapter

import android.content.Context
import androidx.core.content.ContextCompat
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.item_bills.*
import ru.steilsouth.ellcom.R

class BillsItem(
    private val price: String,
    private val image: Int,
    private val date: String,
    private val context: Context
) : Item() {
    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.textViewPrice.text = "$price ₽"
        viewHolder.imageViewPaymentMethod.setImageDrawable(ContextCompat.getDrawable(context, image))
        viewHolder.textViewDate.text = date
    }

    override fun getLayout(): Int = R.layout.item_bills
}
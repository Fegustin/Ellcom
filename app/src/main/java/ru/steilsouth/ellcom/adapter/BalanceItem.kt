package ru.steilsouth.ellcom.adapter

import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.item_balance_description.*
import ru.steilsouth.ellcom.R
import ru.steilsouth.ellcom.pojo.balance.BalanceList

class BalanceItem(
    private val item: BalanceList
) : Item() {
    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.textViewTitle.text = item.type
        viewHolder.textViewSum.text = item.summ.toString()
    }

    override fun getLayout(): Int = R.layout.item_balance_description
}
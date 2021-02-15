package ru.steilsouth.ellcom.adapter

import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.item_distribute_funds.*
import ru.steilsouth.ellcom.R
import ru.steilsouth.ellcom.pojo.subcontracts.SubContractsResult

class ReallocationFundsItem(private val item: SubContractsResult.Data.Result,) : Item() {
    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.textViewLogin.text = "№" + item.id.toString()
        viewHolder.textViewBalance.text = item.balance.toString() + " руб."
    }

    override fun getLayout(): Int = R.layout.item_distribute_funds
}
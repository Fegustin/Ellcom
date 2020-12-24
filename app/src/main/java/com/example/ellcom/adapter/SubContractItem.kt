package com.example.ellcom.adapter

import android.annotation.SuppressLint
import android.content.Context
import androidx.core.content.ContextCompat
import com.example.ellcom.R
import com.example.ellcom.pojo.subcontracts.SubContractsResult
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.item_subcontract.*

class SubContractItem(
    private val context: Context,
    private val item: SubContractsResult.Data.Result
) : Item() {
    @SuppressLint("SetTextI18n")
    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        val itemSub = item

        viewHolder.textViewContactNumSubOne.text = "Субдоговор: №${itemSub.title}"
        viewHolder.textViewRateSubOne.text =
            "Тариф: ${itemSub.rateList[0].tariffTitle.substringBefore("(")}"
        viewHolder.textViewBalanceSubOne.text = "${itemSub.balance} ₽"
        viewHolder.textViewTimeSubOne.text = "Хватит до ${itemSub.rateList[0].dateTo}"
        viewHolder.textViewIsActiveSubOne.text = itemSub.rateList[0].status

        if (itemSub.rateList[0].status != "Активен") {
            viewHolder.textViewIsActiveSubOne.setTextColor(
                ContextCompat.getColor(
                    context,
                    R.color.red
                )
            )
        }
    }

    override fun getLayout(): Int = R.layout.item_subcontract
}
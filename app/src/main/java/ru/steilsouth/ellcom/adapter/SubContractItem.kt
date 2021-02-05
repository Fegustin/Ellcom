package ru.steilsouth.ellcom.adapter

import android.annotation.SuppressLint
import android.content.Context
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.item_subcontract.*
import ru.steilsouth.ellcom.R
import ru.steilsouth.ellcom.pojo.subcontracts.SubContractsResult
import ru.steilsouth.ellcom.ui.MainScreenFragment
import ru.steilsouth.ellcom.ui.MainScreenFragmentDirections
import ru.steilsouth.ellcom.ui.SubContractListFragment
import ru.steilsouth.ellcom.ui.SubContractListFragmentDirections

class SubContractItem(
    private val context: Context,
    private val item: SubContractsResult.Data.Result,
    private val fragment: Fragment
) : Item() {
    @SuppressLint("SetTextI18n")
    override fun bind(viewHolder: GroupieViewHolder, position: Int) {

        init(viewHolder, item)

        viewHolder.layoutSubContract.setOnClickListener { goToProfilePage(item) }
    }

    override fun getLayout(): Int = R.layout.item_subcontract

    private fun init(viewHolder: GroupieViewHolder, item: SubContractsResult.Data.Result) {
        viewHolder.textViewContactNumSubOne.text = "Субдоговор: №${item.title}"
        viewHolder.textViewRateSubOne.text =
            "Тариф: ${item.rateList[0].tariffTitle.substringBefore("(")}"
        viewHolder.textViewBalanceSubOne.text = "${item.balance} ₽"
        viewHolder.textViewTimeSubOne.text = "Хватит до ${item.rateList[0].dateTo}"
        viewHolder.textViewIsActiveSubOne.text = item.rateList[0].status

        if (item.rateList[0].status != "Активен") {
            viewHolder.textViewIsActiveSubOne.setTextColor(
                ContextCompat.getColor(
                    context,
                    R.color.red
                )
            )
        }
    }

    private fun goToProfilePage(item: SubContractsResult.Data.Result) {
        val action: NavDirections
        if (fragment::class.java == MainScreenFragment::class.java) {
            fragment as MainScreenFragment
            action = MainScreenFragmentDirections.actionMainScreenFragmentToProfileFragment2(
                true,
                item.comment,
                item.rateList[0].tariffTitle.substringBefore("("),
                item.balance.toString(),
                item.id
            )
        } else {
            fragment as SubContractListFragment
            action =
                SubContractListFragmentDirections.actionSubContractListFragmentToProfileFragment2(
                    true,
                    item.comment,
                    item.rateList[0].tariffTitle.substringBefore("("),
                    item.balance.toString(),
                    item.id
                )
        }
        context.getSharedPreferences("SP_INFO", Context.MODE_PRIVATE)?.edit()?.apply {
            putBoolean("isSuperContract", false)
            apply()
        }
        fragment.findNavController().navigate(action)
    }
}
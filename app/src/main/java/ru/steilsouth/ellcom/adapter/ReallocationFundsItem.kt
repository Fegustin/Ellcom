package ru.steilsouth.ellcom.adapter

import android.content.Context
import android.text.Html
import android.view.KeyEvent
import android.widget.Toast
import androidx.lifecycle.LifecycleOwner
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.item_distribute_funds.*
import ru.steilsouth.ellcom.R
import ru.steilsouth.ellcom.pojo.subcontracts.SubContractsResult
import ru.steilsouth.ellcom.utils.*
import ru.steilsouth.ellcom.viewmodal.ReallocationFundsVM
import java.math.BigDecimal


/* Используется экстеншен функция hideKeyboard() расширяющая View */
class ReallocationFundsItem(
    private val context: Context,
    private val viewLifecycleOwner: LifecycleOwner,
    private val item: SubContractsResult.Data.Result,
    private val model: ReallocationFundsVM
) : Item() {
    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        val ruble = Html.fromHtml(" &#x20bd", 0)

        viewHolder.textViewLogin.text = "№" + item.id.toString()
        viewHolder.textViewBalance.text = item.balance.toString() + ruble

        viewHolder.editTextSum.setOnKeyListener { v, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                viewHolder.editTextSum.clearFocus()
                viewHolder.editTextSum.isCursorVisible = false

                if (!viewHolder.editTextSum.validationReallocation()) {
                    v.hideKeyboard()
                    return@setOnKeyListener false
                }

                if (!isOnline(context)) {
                    v.hideKeyboard()
                    return@setOnKeyListener false
                }

                v.hideKeyboard()
                val numberRedistribute = viewHolder.editTextSum.text.toString()

                redistribute(item.id, numberRedistribute.toBigDecimal())

                val newBalance =
                    viewHolder.textViewBalance.text.toString()
                        .substringBefore(ruble.toString())
                        .toDouble() + numberRedistribute.toDouble()
                viewHolder.textViewBalance.text = newBalance.toString() + ruble

                model.select(numberRedistribute.toDouble())

                viewHolder.editTextSum.setText("")
                return@setOnKeyListener true
            }
            return@setOnKeyListener false
        }
    }

    override fun getLayout(): Int = R.layout.item_distribute_funds

    private fun redistribute(id: Int, amount: BigDecimal) {
        val token =
            context.getSharedPreferences("SP_INFO", Context.MODE_PRIVATE)?.getString("token", "")
        if (token != null) {
            model.reallocationOfFunds(token, id, amount).observe(viewLifecycleOwner) {
                if (it.status != "ok") {
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }
}
package ru.steilsouth.ellcom.adapter

import android.app.AlertDialog
import android.app.Application
import android.content.Context
import android.view.View
import androidx.navigation.findNavController
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.item_conract.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.steilsouth.ellcom.R
import ru.steilsouth.ellcom.repository.DataBaseRepository


class ContractItem(
    private val token: String,
    private val number: String,
    private val name: String,
    private val context: Context,
    private val application: Application,
) : Item() {
    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.textViewLoginNum.text = number
        viewHolder.textViewLoginName.text = name

        val tokenCurrent =
            context.getSharedPreferences("SP_INFO", Context.MODE_PRIVATE).getString("token", "")

        if (tokenCurrent == token) {
            viewHolder.textViewTitleActiveContract.visibility = View.VISIBLE
            viewHolder.imageButtonDelete.visibility = View.GONE
        } else {
            viewHolder.layoutContract.setOnClickListener {
                changeContract(it)
            }
            viewHolder.imageButtonDelete.setOnClickListener {
                deleteContract(it)
            }
        }
    }

    override fun getLayout(): Int = R.layout.item_conract

    private fun changeContract(view: View) {
        AlertDialog.Builder(context)
            .setTitle("Смена договора")
            .setMessage("Вы действительно хотите сменить договор?")
            .setPositiveButton("Да") { _, _ ->

                context.getSharedPreferences("SP_INFO", Context.MODE_PRIVATE)
                    .edit()
                    .clear()
                    .apply()

                context.getSharedPreferences("SP_INFO", Context.MODE_PRIVATE).edit().apply {
                    putString("token", token)
                    putBoolean("hasEntered", true)
                    putBoolean("disposable", false)
                    apply()
                }

                view.findNavController()
                    .navigate(R.id.mainScreenFragment)
            }
            .setNegativeButton("Нет") { _, _ -> }
            .show()
    }

    private fun deleteContract(view: View) {
        AlertDialog.Builder(context)
            .setTitle("Удалить договор")
            .setMessage("Вы действительно хотите удалить договор?")
            .setPositiveButton("Да") { _, _ ->
                CoroutineScope(Dispatchers.IO).launch {
                    val db = DataBaseRepository(application)
                    val user = db.getUser(token)
                    if (user != null) db.deleteUser(user)
                }
                view.findNavController()
                    .navigate(R.id.contractChangeFragment)
            }
            .setNegativeButton("Нет") { _, _ -> }
            .show()
    }
}
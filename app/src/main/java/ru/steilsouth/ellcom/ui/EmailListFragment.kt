package ru.steilsouth.ellcom.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.groupiex.plusAssign
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import kotlinx.android.synthetic.main.fragment_email_list.*
import ru.steilsouth.ellcom.R
import ru.steilsouth.ellcom.adapter.EmailListItem
import ru.steilsouth.ellcom.pojo.email.EmailAddress
import ru.steilsouth.ellcom.utils.hideKeyboard
import ru.steilsouth.ellcom.utils.isOnline
import ru.steilsouth.ellcom.utils.validationEmailList
import ru.steilsouth.ellcom.viewmodal.EmailListVM

/* Используется экстеншен функция hideKeyboard() расширяющая View */
class EmailListFragment : Fragment(R.layout.fragment_email_list) {

    private val modelEmailList: EmailListVM by activityViewModels()

    private val adapter = GroupAdapter<GroupieViewHolder>()
    private val emailList = mutableListOf<EmailAddress>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val token =
            activity?.getSharedPreferences("SP_INFO", Context.MODE_PRIVATE)?.getString("token", "")

        if (token != null) {

            getEmailList(token)

            buttonEnter.setOnClickListener {
                editTextTextEmailAddress.hideKeyboard()

                if (editTextTextEmailAddress.text.isNullOrEmpty()) return@setOnClickListener

                if (!editTextTextEmailAddress.validationEmailList()) {
                    Toast.makeText(activity, "Не корректный Email", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                adapter += EmailListItem(EmailAddress(editTextTextEmailAddress.text.toString()))
                adapter.notifyDataSetChanged()
                emailList.add(EmailAddress(editTextTextEmailAddress.text.toString()))
                updateEmailList(token, emailList)

                isEmptyEmail()

                editTextTextEmailAddress.setText("")
            }
        }
    }

    private fun getEmailList(token: String) {
        if (isOnline(requireContext())) {
            modelEmailList.getEmailList(token).observe(viewLifecycleOwner) {
                if (it.status == "ok") {
                    adapter.clear()
                    for (i in it.data.res) {
                        adapter.add(EmailListItem(i))
                        emailList.add(i)
                    }
                    recyclerViewEmailList.adapter = adapter
                    ItemTouchHelper(itemTouchHelperCallback()).attachToRecyclerView(recyclerViewEmailList)

                    isEmptyEmail()
                } else Toast.makeText(activity, it.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun updateEmailList(token: String, email: MutableList<EmailAddress>) {
        if (isOnline(requireContext())) {
            modelEmailList.updateEmail(token, email).observe(viewLifecycleOwner) {
                if (it.status == "ok") Toast.makeText(
                    activity,
                    "Список email успешно обновлен",
                    Toast.LENGTH_SHORT
                ).show()
                else Toast.makeText(activity, it.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun itemTouchHelperCallback() =
        object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean = false

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val token =
                    activity?.getSharedPreferences("SP_INFO", Context.MODE_PRIVATE)?.getString("token", "")

                if (token != null) {
                    emailList.removeAt(viewHolder.adapterPosition)
                    updateEmailList(token, emailList)
                    adapter.removeGroupAtAdapterPosition(viewHolder.adapterPosition)
                    adapter.notifyDataSetChanged()
                }

                isEmptyEmail()
            }
        }

    private fun isEmptyEmail() {
        if (emailList.isEmpty()) textViewNoItem.visibility = View.VISIBLE
        else textViewNoItem.visibility = View.GONE
    }
}
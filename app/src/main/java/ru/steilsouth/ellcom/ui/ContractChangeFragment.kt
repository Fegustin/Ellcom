package ru.steilsouth.ellcom.ui

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import kotlinx.android.synthetic.main.fragment_contract_change.*
import kotlinx.android.synthetic.main.fragment_contract_change.swipeRefresh
import kotlinx.android.synthetic.main.fragment_notification.*
import ru.steilsouth.ellcom.R
import ru.steilsouth.ellcom.adapter.ContractItem
import ru.steilsouth.ellcom.database.model.UserData
import ru.steilsouth.ellcom.utils.isOnline
import ru.steilsouth.ellcom.utils.validation
import ru.steilsouth.ellcom.viewmodal.AuthVM
import ru.steilsouth.ellcom.viewmodal.DataBaseVM
import ru.steilsouth.ellcom.viewmodal.MainAndSubVM


class ContractChangeFragment : Fragment(R.layout.fragment_contract_change) {

    private val modelDB: DataBaseVM by activityViewModels()
    private val modelAuth: AuthVM by activityViewModels()
    private val modelMainAndSub: MainAndSubVM by activityViewModels()

    private val adapter = GroupAdapter<GroupieViewHolder>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerViewContract.adapter = adapter

        initRecyclerView()

        buttonAddContract.setOnClickListener {
            addContract()
        }

        swipeRefresh.setOnRefreshListener {
            initRecyclerView()
        }
    }

    private fun initRecyclerView() {
        if (!isOnline(requireContext())) {
            swipeRefresh.isRefreshing = false
            return
        }
        modelDB.getUserAll().observe(viewLifecycleOwner) {
            adapter.clear()
            for (i in it) {
                adapter.add(
                    ContractItem(
                        i.token,
                        i.number,
                        i.name,
                        requireContext(),
                        requireActivity().application,
                    )
                )
            }
            adapter.notifyDataSetChanged()
            swipeRefresh.isRefreshing = false
        }
    }

    private fun addContract() {
        val dialogView = View.inflate(requireContext(), R.layout.dialog_contract, null)
        val editTextLogin = dialogView.findViewById<EditText>(R.id.editTextLogin)
        val editTextPassword = dialogView.findViewById<EditText>(R.id.editTextPassword)

        val dialog = AlertDialog.Builder(activity)
            .setTitle("Добавить договор")
            .setView(dialogView)
            .setPositiveButton("Добавить", null)
            .setNegativeButton("Отмена") { _, _ -> }
            .create()

        dialog.show()
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
            if (!editTextLogin.validation() || !editTextPassword.validation()) {
                Toast.makeText(activity, "Заполните поля", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val type = editTextLogin.text.toString()
                .toIntOrNull() != null && editTextPassword.text.length == 9

            auth(editTextLogin.text.toString(), editTextPassword.text.toString(), type)
            dialog.dismiss()
        }
    }

    private fun auth(login: String, password: String, type: Boolean) {
        if (!isOnline(requireContext())) {
            swipeRefresh.isRefreshing = false
            return
        }
        modelAuth.auth(login, password, type).observe(viewLifecycleOwner) {
            if (it.status == "ok") {
                getContract(it.data.res.token)
            } else Toast.makeText(activity, it.message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun getContract(token: String) {
        if (!isOnline(requireContext())) {
            swipeRefresh.isRefreshing = false
            return
        }
        modelMainAndSub.infoProfile(token).observe(viewLifecycleOwner) {
            if (it.status == "ok") {
                modelDB.insertUser(
                    UserData(
                        token = token,
                        number = it.data.res.contract_num,
                        name = it.data.res.name
                    )
                )
                initRecyclerView()
            } else Toast.makeText(activity, it.message, Toast.LENGTH_SHORT).show()
        }
    }
}
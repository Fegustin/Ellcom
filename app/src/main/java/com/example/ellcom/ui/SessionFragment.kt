package com.example.ellcom.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.ellcom.R
import com.example.ellcom.adapter.SessionItem
import com.example.ellcom.utils.Internet
import com.example.ellcom.viewmodal.ChangePasswordViewModal
import com.example.ellcom.viewmodal.SessionViewModal
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import kotlinx.android.synthetic.main.fragment_session.*
import java.text.SimpleDateFormat
import java.util.*


class SessionFragment : Fragment() {

    private val sessionViewModal: SessionViewModal by activityViewModels()
    private val model: ChangePasswordViewModal by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_session, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var isHistorySession = false

        val token =
            activity?.getSharedPreferences("SP_INFO", Context.MODE_PRIVATE)?.getString("token", "")
        val isSuperContract =
            activity?.getSharedPreferences("SP_INFO", Context.MODE_PRIVATE)
                ?.getBoolean("isSuperContract", true)

        if (!Internet().checkInternetConnecting(activity)) {
            Toast.makeText(activity, "Отсутствует подключение к интернету", Toast.LENGTH_SHORT)
                .show()
        } else {
            if (token != null && isSuperContract != null) {
                setContactInSpinner(token)

                buttonGroupSession.addOnButtonCheckedListener { _, checkedId, isChecked ->
                    if (isChecked) {
                        when (checkedId) {
                            R.id.buttonActive -> {
                                isHistorySession = false

                                getActiveSession(
                                    token,
                                    spinnerLogin.selectedItem.toString().substringAfter("№")
                                )
                            }
                            R.id.buttonHistory -> {
                                isHistorySession = true

                                getHistorySession(
                                    token,
                                    spinnerLogin.selectedItem.toString().substringAfter("№"),
                                )
                            }
                        }
                    }
                }

                spinnerLogin.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        parent: AdapterView<*>?,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {
                        if (isHistorySession) {
                            getHistorySession(
                                token,
                                spinnerLogin.selectedItem.toString().substringAfter("№")
                            )
                        } else {
                            getActiveSession(
                                token,
                                spinnerLogin.selectedItem.toString().substringAfter("№")
                            )
                        }
                    }

                    override fun onNothingSelected(parent: AdapterView<*>?) {}
                }
            }
        }
    }

    private fun setContactInSpinner(token: String) {
        val array = mutableListOf<String>()
        model.getListServiceInternet(token).observe(viewLifecycleOwner) {
            if (it.status == "ok") {
                for (i in it.data.res) array.add("Логин: №" + i.id.toString())
                fillSpinner(array)
            } else Toast.makeText(activity, it.message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun fillSpinner(array: List<String>) {
        val adapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, array)
        spinnerLogin.adapter = adapter
    }

    private fun getActiveSession(token: String, servId: String) {
        sessionViewModal.getActiveSession(token, servId.toInt()).observe(viewLifecycleOwner) {
            if (it.status == "ok") {
                val adapter = GroupAdapter<GroupieViewHolder>()
                for (i in it.data.res) {
                    adapter.add(SessionItem(i))
                }
                recyclerViewSession.adapter = adapter
            } else Toast.makeText(activity, it.message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun getHistorySession(token: String, servId: String) {
        sessionViewModal.getHistorySession(
            token,
            servId.toInt(),
            "${getDateNow("yyyy")}-${getDateNow("MM")}-01",
            getDateNow("yyyy-MM-dd")
        )
            .observe(viewLifecycleOwner) {
                if (it.status == "ok") {
                    val adapter = GroupAdapter<GroupieViewHolder>()
                    for (i in it.data.res) {
                        adapter.add(SessionItem(i))
                    }
                    recyclerViewSession.adapter = adapter
                } else Toast.makeText(activity, it.message, Toast.LENGTH_SHORT).show()
            }
    }

    private fun getDateNow(pattern: String): String {
        val date = Calendar.getInstance().time
        val format = SimpleDateFormat(pattern)
        return format.format(date)
    }
}
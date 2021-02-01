package com.example.ellcom.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.ellcom.R
import com.example.ellcom.utils.Internet
import com.example.ellcom.viewmodal.MainAndSubViewModal
import kotlinx.android.synthetic.main.fragment_session.*


class TrafficFragment : Fragment() {

    private val mainAndSubViewModal: MainAndSubViewModal by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_traffic, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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
                setContactInSpinner(token, isSuperContract)
            }
        }
    }

    private fun setContactInSpinner(token: String, isSuperContract: Boolean) {
        val array = mutableListOf<String>()
        mainAndSubViewModal.infoProfile(token).observe(viewLifecycleOwner) {
            if (it.status == "ok") {
                array.add("Логин: №" + it.data.res.id.toString())

                if (isSuperContract) {
                    mainAndSubViewModal.getSubContractsList(token)
                        .observe(viewLifecycleOwner) { it2 ->
                            if (it2.status == "ok") {
                                for (i in it2.data.res) array.add("Логин: №" + i.id.toString())
                                fillSpinner(array)
                            } else Toast.makeText(activity, it.message, Toast.LENGTH_SHORT).show()
                        }
                } else {
                    fillSpinner(array)
                }

            } else Toast.makeText(activity, it.message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun fillSpinner(array: List<String>) {
        val adapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, array)
        spinnerLogin.adapter = adapter
    }
}
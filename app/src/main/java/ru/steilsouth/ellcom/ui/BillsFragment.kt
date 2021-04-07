package ru.steilsouth.ellcom.ui

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import kotlinx.android.synthetic.main.fragment_bills.*
import kotlinx.android.synthetic.main.fragment_bills.view.*
import ru.steilsouth.ellcom.R
import ru.steilsouth.ellcom.adapter.BillsItem
import ru.steilsouth.ellcom.utils.isOnline
import ru.steilsouth.ellcom.utils.setFormat
import ru.steilsouth.ellcom.utils.validationDate
import ru.steilsouth.ellcom.viewmodal.BillsVM
import ru.steilsouth.ellcom.viewmodal.MainAndSubVM
import java.util.*


class BillsFragment : Fragment(R.layout.fragment_bills) {

    private val modelMainAndSub: MainAndSubVM by activityViewModels()
    private val modelBills: BillsVM by activityViewModels()

    private val adapter = GroupAdapter<GroupieViewHolder>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerViewBills.adapter = adapter

        setFormat("[00]{.}[00]{.}[9900]", editTextDateFrom)
        setFormat("[00]{.}[00]{.}[9900]", editTextDateTo)

        val token =
            activity?.getSharedPreferences("SP_INFO", Context.MODE_PRIVATE)?.getString("token", "")

        if (token != null) {
            imageButtonSearch.setOnClickListener {
                if (!editTextDateFrom.validationDate()) {
                    Toast.makeText(
                        activity,
                        "Некорректная дата",
                        Toast.LENGTH_SHORT
                    ).show()
                    return@setOnClickListener
                }

                if (!editTextDateTo.validationDate()) {
                    Toast.makeText(
                        activity,
                        "Некорректная дата",
                        Toast.LENGTH_SHORT
                    ).show()
                    return@setOnClickListener
                }

                initRecyclerView(
                    token,
                    editTextDateFrom.text.toString(),
                    editTextDateTo.text.toString()
                )
            }
        }
    }

    private fun initRecyclerView(token: String, dateFrom: String, dateTo: String) {
        if (!isOnline(requireContext())) return
        modelMainAndSub.infoProfile(token).observe(viewLifecycleOwner) { infoResult ->
            if (infoResult.status == "ok") {
                modelBills.getBillsList(
                    infoResult.data.res.contract_num,
                    dateFrom,
                    dateTo
                ).observe(viewLifecycleOwner) {
                    if (it.isSuccessful) {
                        val res = it.body()
                        if (res != null) {
                            if (res.status == "ok") {
                                if (res.data.isEmpty()) {
                                    Toast.makeText(activity, "Ничего не найдено", Toast.LENGTH_SHORT).show()
                                }
                                adapter.clear()
                                for (i in res.data) {
                                    adapter.add(
                                        BillsItem(
                                            i.acc_records[0].sum.toString(),
                                            i.acc_records[0].service,
                                            i.acc_date
                                        )
                                    )
                                }
                            } else {
                                Toast.makeText(activity, res?.message, Toast.LENGTH_SHORT).show()
                            }
                        }
                    } else {
                        Toast.makeText(
                            activity,
                            "Что-то пошло не так. Обратитесь в поддержку",
                            Toast.LENGTH_SHORT
                        ).show()
                        return@observe
                    }
                }
            }
        }
    }
}
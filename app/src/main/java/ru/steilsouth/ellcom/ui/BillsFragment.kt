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
import ru.steilsouth.ellcom.R
import ru.steilsouth.ellcom.utils.isOnline
import ru.steilsouth.ellcom.viewmodal.BillsVM
import ru.steilsouth.ellcom.viewmodal.MainAndSubVM
import java.text.SimpleDateFormat
import java.util.*


class BillsFragment : Fragment(R.layout.fragment_bills) {

    private val modelMainAndSub: MainAndSubVM by activityViewModels()
    private val modelBills: BillsVM by activityViewModels()

    private val adapter = GroupAdapter<GroupieViewHolder>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerViewBills.adapter = adapter

        val token =
            activity?.getSharedPreferences("SP_INFO", Context.MODE_PRIVATE)?.getString("token", "")

        if (token != null) {
            initRecyclerView(token)
        }
    }

    private fun initRecyclerView(token: String) {
        if (!isOnline(requireContext())) {
            swipeRefresh.isRefreshing = false
            return
        }
        modelMainAndSub.infoProfile(token).observe(viewLifecycleOwner) { infoResult ->
            if (infoResult.status == "ok") {
                modelBills.getBillsList(
                    infoResult.data.res.contract_num,
                    "01.01.2020",
                    dateFormat()
                ).observe(viewLifecycleOwner) {
                    if (it == null) return@observe
                    if (it.status == "ok") {
                    } else {
                        Toast.makeText(activity, it.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun dateFormat(): String {
        val calendar = Calendar.getInstance()
        return SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(calendar.time)
    }
}
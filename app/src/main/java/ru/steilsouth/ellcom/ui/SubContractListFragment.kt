package ru.steilsouth.ellcom.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import kotlinx.android.synthetic.main.fragment_sub_contract_list.*
import ru.steilsouth.ellcom.R
import ru.steilsouth.ellcom.adapter.SubContractItem
import ru.steilsouth.ellcom.utils.isOnline
import ru.steilsouth.ellcom.viewmodal.MainAndSubVM


class SubContractListFragment : Fragment(R.layout.fragment_sub_contract_list) {
    private val model: MainAndSubVM by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = GroupAdapter<GroupieViewHolder>()
        recyclerViewSubList.adapter = adapter

        val token =
            activity?.getSharedPreferences("SP_INFO", Context.MODE_PRIVATE)?.getString("token", "")

        if (isOnline(requireContext())) {
            if (token != null) {
                fillAdapter(token)

                swipeRefreshSub.setOnRefreshListener {
                    if (isOnline(requireContext())) {
                        fillAdapter(token)
                    }
                }
            }
        }
    }

    private fun fillAdapter(token: String) {
        if (isOnline(requireContext())) {
            model.getSubContractsList(token).observe(viewLifecycleOwner) {
                if (it.status == "ok") {
                    swipeRefreshSub.isRefreshing = false
                    val adapter = GroupAdapter<GroupieViewHolder>()

                    for (i in it.data.res) {
                        adapter.add(SubContractItem(requireContext(), i, this))
                    }

                    recyclerViewSubList.adapter = adapter
                } else {
                    Toast.makeText(activity, it.message, Toast.LENGTH_SHORT).show()
                    swipeRefreshSub.isRefreshing = false
                }
            }
        } else {
            swipeRefreshSub.isRefreshing = false
        }
    }
}
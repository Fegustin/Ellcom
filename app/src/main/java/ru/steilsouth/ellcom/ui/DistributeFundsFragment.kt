package ru.steilsouth.ellcom.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import kotlinx.android.synthetic.main.fragment_distribute_funds.*
import ru.steilsouth.ellcom.R
import ru.steilsouth.ellcom.adapter.ReallocationFundsItem
import ru.steilsouth.ellcom.utils.isOnline
import ru.steilsouth.ellcom.viewmodal.MainAndSubVM
import ru.steilsouth.ellcom.viewmodal.ReallocationFundsVM


class DistributeFundsFragment : Fragment(R.layout.fragment_distribute_funds) {

    private val modelMainAndSub: MainAndSubVM by activityViewModels()
    private val modelReallocationFunds: ReallocationFundsVM by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val token =
            activity?.getSharedPreferences("SP_INFO", Context.MODE_PRIVATE)?.getString("token", "")

        if (isOnline(requireContext())) {
            if (token != null) {

                infoProfile(token)

                fillRecyclerView(token)

                modelReallocationFunds.balance.observe(viewLifecycleOwner) {
                    if (it != null && textViewBalance.text.isNotBlank()) {
                        textViewBalance.text =
                            (textViewBalance.text.toString().toDouble() - it).toString()
                    }
                }

                swipeRefresh.setOnRefreshListener {
                    if (isOnline(requireContext())) {
                        fillRecyclerView(token)
                    }
                }
            }
        }
    }

    private fun fillRecyclerView(token: String) {
        if (isOnline(requireContext())) {
            modelMainAndSub.getSubContractsList(token).observe(viewLifecycleOwner) {
                if (it.status == "ok") {
                    swipeRefresh?.isRefreshing = false
                    val adapter = GroupAdapter<GroupieViewHolder>()
                    for (i in it.data.res) {
                        adapter.add(
                            ReallocationFundsItem(
                                requireContext(),
                                viewLifecycleOwner,
                                i,
                                modelReallocationFunds
                            )
                        )
                    }
                    recyclerViewSubList.adapter = adapter
                } else {
                    Toast.makeText(activity, it.message, Toast.LENGTH_SHORT).show()
                    swipeRefresh?.isRefreshing = false
                }
            }
        }
    }

    private fun infoProfile(token: String) {
        if (isOnline(requireContext())) {
            modelMainAndSub.infoProfile(token).observe(viewLifecycleOwner) {
                if (it.status == "ok") {
                    setTitle(it.data.res.name)
                    textViewBalance.text = it.data.res.balance.toString()
                }
            }
        }
    }

    private fun setTitle(text: String) {
        activity?.findViewById<TextView>(R.id.textViewTitle)?.text = text
    }
}
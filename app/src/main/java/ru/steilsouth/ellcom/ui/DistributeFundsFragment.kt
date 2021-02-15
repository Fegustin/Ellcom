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
import ru.steilsouth.ellcom.utils.Internet
import ru.steilsouth.ellcom.viewmodal.MainAndSubViewModal
import ru.steilsouth.ellcom.viewmodal.ReallocationFundsViewModal


class DistributeFundsFragment : Fragment() {

    private val modelMainAndSub: MainAndSubViewModal by activityViewModels()
    private val modelReallocationFunds: ReallocationFundsViewModal by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_distribute_funds, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val token =
            activity?.getSharedPreferences("SP_INFO", Context.MODE_PRIVATE)?.getString("token", "")

        if (!Internet().checkInternetConnecting(activity)) {
            Toast.makeText(activity, "Отсутствует подключение к интернету", Toast.LENGTH_SHORT)
                .show()
        } else {
            if (token != null) {

                infoProfile(token)

                fillRecyclerView(token)

                swipeRefresh.setOnRefreshListener {
                    if (Internet().checkInternetConnecting(activity)) {
                        fillRecyclerView(token)
                    }
                }
            }
        }
    }

    private fun fillRecyclerView(token: String) {
        modelMainAndSub.getSubContractsList(token).observe(viewLifecycleOwner) {
            if (it.status == "ok") {
                swipeRefresh?.isRefreshing = false
                val adapter = GroupAdapter<GroupieViewHolder>()
                for (i in it.data.res) {
                    adapter.add(ReallocationFundsItem(i))
                }
                recyclerViewSubList.adapter = adapter
            } else {
                Toast.makeText(activity, it.message, Toast.LENGTH_SHORT).show()
                swipeRefresh?.isRefreshing = false
            }
        }
    }

    private fun infoProfile(token: String) {
        modelMainAndSub.infoProfile(token).observe(viewLifecycleOwner) {
            if (it.status == "ok") {
                setTitle(it.data.res.name)
                textViewBalance.text = it.data.res.balance.toString()
            }
        }
    }

    private fun setTitle(text: String) {
        activity?.findViewById<TextView>(R.id.textViewTitle)?.text = text
    }
}
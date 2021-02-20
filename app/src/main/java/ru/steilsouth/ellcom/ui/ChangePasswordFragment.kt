package ru.steilsouth.ellcom.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import kotlinx.android.synthetic.main.fragment_change_password.*
import ru.steilsouth.ellcom.R
import ru.steilsouth.ellcom.adapter.ServiceInternetItem
import ru.steilsouth.ellcom.utils.isOnline
import ru.steilsouth.ellcom.viewmodal.ChangePasswordVM


class ChangePasswordFragment : Fragment(R.layout.fragment_change_password) {

    private val model: ChangePasswordVM by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val login =
            activity?.getSharedPreferences("SP_INFO", Context.MODE_PRIVATE)?.getString("login", "")
        val token =
            activity?.getSharedPreferences("SP_INFO", Context.MODE_PRIVATE)?.getString("token", "")

        if (isOnline(requireContext())) {
            if (token != null) {
                setInRecyclerViewItems(token)
            }

            if (login != null) textViewLogin.text = "Логин: $login"

            changePasswordContract.setOnClickListener {
                findNavController()
                    .navigate(ChangePasswordFragmentDirections.actionChangePasswordFragmentToChangePasswordContractAndInternetFragment())
            }
        }
    }

    private fun setInRecyclerViewItems(token: String) {
        if (isOnline(requireContext())) {
            model.getListServiceInternet(token).observe(viewLifecycleOwner) {
                if (it.status == "ok") {
                    val adapter = GroupAdapter<GroupieViewHolder>()
                    for (i in it.data.res) {
                        adapter.add(ServiceInternetItem(i, this))
                    }

                    progressBarInternet.visibility = View.GONE
                    recyclerViewInternet.adapter = adapter
                    recyclerViewInternet.visibility = View.VISIBLE
                } else Toast.makeText(activity, it.message, Toast.LENGTH_SHORT).show()
            }
        }
    }
}
package ru.steilsouth.ellcom.adapter

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.item_service_internet.*
import ru.steilsouth.ellcom.R
import ru.steilsouth.ellcom.pojo.changepassword.inet.ServiceInternetResult
import ru.steilsouth.ellcom.ui.ChangePasswordFragmentDirections

class ServiceInternetItem(
    private val item: ServiceInternetResult.Data.Result,
    private val fragment: Fragment
) : Item() {
    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.textViewLogin.text = "Логин: " + item.login

        viewHolder.changePasswordContract.setOnClickListener {
            val action =
                ChangePasswordFragmentDirections.actionChangePasswordFragmentToChangePasswordContractAndInternetFragment(
                    false,
                    item.id
                )
            fragment.findNavController().navigate(action)
        }
    }

    override fun getLayout(): Int = R.layout.item_service_internet
}
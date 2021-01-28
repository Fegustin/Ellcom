package com.example.ellcom.adapter

import com.example.ellcom.R
import com.example.ellcom.pojo.changepassword.inet.ServiceInternetResult
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.item_service_internet.*

class ServiceInternetItem(
    private val item: ServiceInternetResult.Data.Result
) : Item() {
    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.textViewLogin.text = item.login
    }

    override fun getLayout(): Int = R.layout.item_service_internet
}
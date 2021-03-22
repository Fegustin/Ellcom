package ru.steilsouth.ellcom.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.steilsouth.ellcom.R
import ru.steilsouth.ellcom.pojo.bills.createbills.SubMobileContract
import ru.steilsouth.ellcom.repository.BillsRepository
import ru.steilsouth.ellcom.viewmodal.BillsVM
import ru.steilsouth.ellcom.viewmodal.DataBaseVM
import ru.steilsouth.ellcom.viewmodal.MainAndSubVM


class BillsFragment : Fragment(R.layout.fragment_bills) {

    private val modelDB: DataBaseVM by activityViewModels()
    private val modelMainAndSub: MainAndSubVM by activityViewModels()
    private val modelBills: BillsVM by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val token =
            activity?.getSharedPreferences("SP_INFO", Context.MODE_PRIVATE)?.getString("token", "")

        if (token != null) {
            modelMainAndSub.infoProfile(token).observe(viewLifecycleOwner) {
                modelBills.getBillsList(
                    "c43f18a2-c6c7-4850-be95-24f3cbaef6b6",
                    "Основной13579",
                    "01.03.2021",
                    "19.03.2021"
                ).observe(viewLifecycleOwner) {

                }
            }
        }
    }
}
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
//            modelMainAndSub.infoProfile(token).observe(viewLifecycleOwner) {
//                val sub = it.data.res.subMobileContract[0]
//                modelBills.createBills(
//                    token,
//                    "Основной13579",
//                    "бухалтер ттутутуту",
//                    listOf(
//                        SubMobileContract(
//                            "салам ворам",
//                            sub.title,
//                            1,
//                            50,
//                            sub.rateList[0].tariffTitle,
//                            "мес"
//                        )
//                    )
//                ).observe(viewLifecycleOwner) {
//
//                }
//            }


            modelMainAndSub.infoProfile(token).observe(viewLifecycleOwner) {
                modelBills.getBillsList("Основной13579", "01.12.2020", "21.01.2021").observe(viewLifecycleOwner) {

                }
            }
        }
    }
}
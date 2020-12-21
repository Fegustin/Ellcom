package com.example.ellcom.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.ellcom.R
import com.example.ellcom.repository.MainRepository
import com.example.ellcom.viewmodal.AuthViewModal
import kotlinx.android.synthetic.main.fragment_main_screen.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MainScreenFragment : Fragment() {

    private val model: AuthViewModal by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val token =
            activity?.getSharedPreferences("SP_INFO", Context.MODE_PRIVATE)?.getString("token", "")

        if (token != null) {
            infoProfile(token)
        }

        buttonRadio.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                if (token != null) {
                    MainRepository().getSubContractsList(token)
                }
            }
        }
    }

    private fun infoProfile(token: String) {
        model.infoProfile(token).observe(viewLifecycleOwner) {
            if (it.status == "ok") {
                val rate = it.data.res.rateList[0].tariffTitle
                    .substringAfter("\"")
                    .substringBefore("\"")

                initTextSuper(
                    it.data.res.contract_num,
                    rate,
                    it.data.res.balance.toString(),
                    it.data.res.rateList[0].dateTo
                )

                activity?.getSharedPreferences("SP_INFO", Context.MODE_PRIVATE)?.edit()
                    ?.apply {
                        putString("contractNum", it.data.res.contract_num)
                        putString("balance", it.data.res.balance.toString())
                        putString("rate", rate)
                        apply()
                    }
            } else Toast.makeText(activity, it.message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun initTextSuper(number: String, rate: String, balance: String, time: String) {
        // Супердоговор
        textViewContactNum.text = "Супердоговор: №$number"
        textViewRate.text = "Тариф: $rate"
        textViewBalance.text = "$balance ₽"
        textViewTime.text = "Хватит до $time"
    }
}
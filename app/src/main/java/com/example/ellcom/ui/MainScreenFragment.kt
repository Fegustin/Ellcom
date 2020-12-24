package com.example.ellcom.ui

import android.content.Context
import android.content.Intent
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.ellcom.MainActivity
import com.example.ellcom.R
import com.example.ellcom.service.RadioService
import com.example.ellcom.utils.Internet
import com.example.ellcom.viewmodal.MainAndSubViewModal
import kotlinx.android.synthetic.main.fragment_main_screen.*
import java.io.IOException


class MainScreenFragment : Fragment() {

    private val model: MainAndSubViewModal by activityViewModels()

    private var isStartRadio = false

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

        if (!Internet().checkInternetConnecting(activity)) {
            Toast.makeText(activity, "Отсутствует подключение к интернету", Toast.LENGTH_SHORT)
                .show()
        } else {
            if (token != null) {
                infoProfile(token)
            }
        }

        buttonRadio.setOnClickListener {
            isStartRadio = if (!isStartRadio) {
                activity?.let { it1 -> RadioService.startService(it1, "EuropePlus") }
                Toast.makeText(activity, "Запуск радио", Toast.LENGTH_SHORT).show()
                true
            } else {
                activity?.let { it1 -> RadioService.stopService(it1) }
                Toast.makeText(activity, "Остановка радио", Toast.LENGTH_SHORT).show()
                false
            }
        }

        buttonShowSub.setOnClickListener {
            findNavController()
                .navigate(MainScreenFragmentDirections.actionMainScreenFragmentToSubContractListFragment())
        }
    }

    private fun infoProfile(token: String) {
        model.infoProfile(token).observe(viewLifecycleOwner) {
            if (it.status == "ok") {
                val rate = it.data.res.rateList[0].tariffTitle
                    .substringAfter("\"")
                    .substringBefore("\"")

                textViewCompany.text = it.data.res.name

                if (it.data.res.subMobileContract.isNotEmpty()) {
                    initTextSuper(
                        "Супердоговор: №${it.data.res.contract_num}",
                        rate,
                        it.data.res.balance.toString(),
                        it.data.res.rateList[0].dateTo,
                        it.data.res.status
                    )

                    initTextSubOne(
                        it.data.res.subMobileContract[0].title,
                        it.data.res.subMobileContract[0].rateList[0].tariffTitle.substringBefore("("),
                        it.data.res.subMobileContract[0].balance.toString(),
                        it.data.res.subMobileContract[0].rateList[0].dateTo,
                        it.data.res.subMobileContract[0].status
                    )
                    initTextSubTwo(
                        it.data.res.subMobileContract[1].title,
                        it.data.res.subMobileContract[1].rateList[0].tariffTitle.substringBefore("("),
                        it.data.res.subMobileContract[1].balance.toString(),
                        it.data.res.subMobileContract[1].rateList[0].dateTo,
                        it.data.res.subMobileContract[1].status
                    )

                    buttonShowSub.visibility = View.VISIBLE
                } else {
                    initTextSuper(
                        "Субдоговор: №${it.data.res.contract_num}",
                        rate,
                        it.data.res.balance.toString(),
                        it.data.res.rateList[0].dateTo,
                        it.data.res.status
                    )

                    buttonShowSub.visibility = View.GONE
                }

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

    private fun initTextSuper(
        number: String,
        rate: String,
        balance: String,
        time: String,
        status: String
    ) {
        textViewContactNum.text = number
        textViewRate.text = "Тариф: $rate"
        textViewBalance.text = "$balance ₽"
        textViewTime.text = "Хватит до $time"
        textViewIsActive.text = status
        if (status != "Активен") {
            textViewIsActive.setTextColor(ContextCompat.getColor(requireContext(), R.color.red))
        }
    }

    private fun initTextSubOne(
        number: String,
        rate: String,
        balance: String,
        time: String,
        status: String
    ) {
        textViewContactNumSubOne.text = "Субдоговор: №$number"
        textViewRateSubOne.text = "Тариф: $rate"
        textViewBalanceSubOne.text = "$balance ₽"
        textViewTimeSubOne.text = "Хватит до $time"
        textViewIsActiveSubOne.text = status
        if (status != "Активен") {
            textViewIsActiveSubOne.setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.red
                )
            )
        }
    }

    private fun initTextSubTwo(
        number: String,
        rate: String,
        balance: String,
        time: String,
        status: String
    ) {
        textViewContactNumSubTwo.text = "Субдоговор: №$number"
        textViewRateSubTwo.text = "Тариф: $rate"
        textViewBalanceSubTwo.text = "$balance ₽"
        textViewTimeSubTwo.text = "Хватит до $time"
        textViewIsActiveSubTwo.text = status
        if (status != "Активен") {
            textViewIsActiveSubTwo.setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.red
                )
            )
        }
    }
}
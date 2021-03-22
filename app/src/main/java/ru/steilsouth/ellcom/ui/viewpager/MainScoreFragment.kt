package ru.steilsouth.ellcom.ui.viewpager

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.SeekBar
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import kotlinx.android.synthetic.main.fragment_main_score.*
import ru.steilsouth.ellcom.R
import ru.steilsouth.ellcom.pojo.bills.createbills.SubMobileContract
import ru.steilsouth.ellcom.utils.isOnline
import ru.steilsouth.ellcom.viewmodal.BillsVM
import ru.steilsouth.ellcom.viewmodal.ContactVM
import ru.steilsouth.ellcom.viewmodal.MainAndSubVM


class MainScoreFragment : Fragment(R.layout.fragment_main_score) {

    private val modelBills: BillsVM by activityViewModels()
    private val modelMainAndSub: MainAndSubVM by activityViewModels()
    private val modelContact: ContactVM by activityViewModels()

    private val infoProfile = mutableMapOf<String, String>()
    private var sum = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val token =
            activity?.getSharedPreferences("SP_INFO", Context.MODE_PRIVATE)?.getString("token", "")

        if (token != null) {
            getAccountant(token)
            getInfoProfile(token)

            seekBarScore?.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(
                    seekBar: SeekBar?,
                    progress: Int,
                    fromUser: Boolean
                ) {
                    textViewMouth?.text = progress.toString() + " мес."

                    buttonCreateScore?.isEnabled = if (progress > 0) {
                        buttonCreateScore?.backgroundTintList =
                            ContextCompat.getColorStateList(requireContext(), R.color.dark_blue)
                        true
                    } else {
                        buttonCreateScore?.backgroundTintList =
                            ContextCompat.getColorStateList(requireContext(), R.color.dark_gray)
                        false
                    }

                    sum = costCalculation(progress, 85, textViewSum)
                }

                override fun onStartTrackingTouch(seekBar: SeekBar?) {}

                override fun onStopTrackingTouch(seekBar: SeekBar?) {}
            })

            buttonCreateScore?.setOnClickListener {
                createAnInvoice(token, seekBarScore.progress, sum)
            }
        }
    }

    private fun createAnInvoice(token: String, quantity: Int, sum: Int) {
        if (!isOnline(requireContext())) return
        val contract = infoProfile["contract"]
        val accountant = infoProfile["accountant"]
        val type = infoProfile["type"]

        if (!contract.isNullOrEmpty() && !accountant.isNullOrEmpty() && !type.isNullOrEmpty()) {
            modelBills.createBills(
                token,
                contract,
                accountant,
                listOf(
                    SubMobileContract(
                        contract,
                        type,
                        quantity,
                        sum,
                        type
                    )
                )
            ).observe(viewLifecycleOwner) {
                if (it.status == "ok") {
                    Toast.makeText(activity, "Счет сформирован", Toast.LENGTH_SHORT).show()
                } else Toast.makeText(activity, it.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getAccountant(token: String) {
        if (!isOnline(requireContext())) return
        modelContact.getMobileContact(token).observe(viewLifecycleOwner) {
            if (it.status == "ok") {
                infoProfile["accountant"] = it.data.res.accountant
            } else {
                Toast.makeText(
                    activity,
                    "Что-то пошло не так. Обратитесь в поддержку",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun getInfoProfile(token: String) {
        if (!isOnline(requireContext())) return
        modelMainAndSub.infoProfile(token).observe(viewLifecycleOwner) {
            if (it.status == "ok") {
                infoProfile["contract"] = it.data.res.contract_num
                infoProfile["balance"] = it.data.res.balance.toString()
                infoProfile["price"] = it.data.res.rateList[0].tariffPrice
                infoProfile["type"] =
                    "Абонентская плата по тарифу " + it.data.res.rateList[0].tariffTitle

                textViewContactNum?.text = "№" + it.data.res.contract_num
            } else {
                Toast.makeText(
                    activity,
                    "Что-то пошло не так. Обратитесь в поддержку",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}
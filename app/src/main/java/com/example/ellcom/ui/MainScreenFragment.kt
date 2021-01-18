package com.example.ellcom.ui

import android.app.ActivityManager
import android.content.Context
import android.content.Context.ACTIVITY_SERVICE
import android.graphics.drawable.AnimatedVectorDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.ellcom.R
import com.example.ellcom.adapter.SubContractItem
import com.example.ellcom.service.RadioService
import com.example.ellcom.utils.Internet
import com.example.ellcom.utils.timerForWatchingMainContent
import com.example.ellcom.viewmodal.MainAndSubViewModal
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import kotlinx.android.synthetic.main.fragment_main_screen.*


class MainScreenFragment : Fragment() {

    private val model: MainAndSubViewModal by activityViewModels()

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

                notification(token)
            }
        }

        timerForWatchingMainContent(progressBar, layoutContent)

        buttonRadio.setOnClickListener {
            if (!checkServiceRunning(RadioService::class.java)) {
                activity?.let { it1 -> RadioService.startService(it1, "EuropePlus") }
                animationRadioIcon(R.drawable.anim_play_pause)
            } else {
                activity?.let { it1 -> RadioService.stopService(it1) }
                animationRadioIcon(R.drawable.anim_pause_play)
            }
        }

        buttonShowSub.setOnClickListener {
            findNavController()
                .navigate(MainScreenFragmentDirections.actionMainScreenFragmentToSubContractListFragment())
        }
    }

    override fun onResume() {
        super.onResume()
        if (!checkServiceRunning(RadioService::class.java)) {
            imageViewAnim.setImageResource(R.drawable.anim_play_pause)
        } else {
            imageViewAnim.setImageResource(R.drawable.anim_pause_play)
        }
    }

    private fun checkServiceRunning(serviceClass: Class<*>): Boolean {
        val manager = context?.getSystemService(ACTIVITY_SERVICE) as ActivityManager?
        if (manager != null) {
            for (service in manager.getRunningServices(Int.MAX_VALUE)) {
                if (serviceClass.name == service.service.className) {
                    return true
                }
            }
        }
        return false
    }

    private fun animationRadioIcon(anim: Int) {
        imageViewAnim.setImageResource(anim)
        val d: Drawable = imageViewAnim.drawable
        if (d is AnimatedVectorDrawable) {
            d.start()
        }
    }

    private fun notification(token: String) {
        if (Internet().checkInternetConnecting(activity)) {
            model.getNotificationList(token, true, 0).observe(viewLifecycleOwner) {
                if (it.status == "ok") {
                    if (it.data.res.size() > 0) {
                        notificationText.visibility = View.VISIBLE
                        notificationText.text = it.data.res.size().toString()
                    } else {
                        notificationText.visibility = View.INVISIBLE
                    }
                }
            }
        } else {
            Toast.makeText(activity, "Отсутствует подключение к интернету", Toast.LENGTH_SHORT)
                .show()
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

                    // Fill subContract
                    val adapter = GroupAdapter<GroupieViewHolder>()
                    for (i in it.data.res.subMobileContract) {
                        adapter.add(SubContractItem(requireContext(), i))
                    }
                    recyclerViewSubList.adapter = adapter

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
}
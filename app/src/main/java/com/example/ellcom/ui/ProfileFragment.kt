package com.example.ellcom.ui

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.ellcom.R
import com.example.ellcom.utils.Internet
import com.example.ellcom.utils.timerForWatchingMainContent
import com.example.ellcom.viewmodal.MainAndSubViewModal
import kotlinx.android.synthetic.main.fragment_profile.*


class ProfileFragment : Fragment() {

    private val model: MainAndSubViewModal by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        timerForWatchingMainContent(progressBar, layoutContent)

        val token =
            activity?.getSharedPreferences("SP_INFO", Context.MODE_PRIVATE)?.getString("token", "")

        if (!Internet().checkInternetConnecting(activity)) {
            Toast.makeText(activity, "Отсутствует подключение к интернету", Toast.LENGTH_SHORT)
                .show()
        } else {
            if (token != null) {
                infoProfile(token)

                buttonExit.setOnClickListener {
                    exit()
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

                textViewCompany.text = it.data.res.name
                textViewRateDown.text = rate
                textViewBalanceDown.text = "${it.data.res.balance}₽"
            }
        }
    }

    private fun exit() {
        AlertDialog.Builder(activity)
            .setTitle("Выход")
            .setMessage("Вы действительно хотите выйти из аккаунта?")
            .setPositiveButton("Да") { _, _ ->
                activity?.getSharedPreferences("SP_INFO", Context.MODE_PRIVATE)
                    ?.edit()
                    ?.clear()
                    ?.apply()
                findNavController()
                    .navigate(R.id.action_global_authFragment)
            }
            .setNegativeButton("Нет") { _, _ -> }
            .show()
    }
}
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
import androidx.navigation.fragment.navArgs
import com.example.ellcom.R
import com.example.ellcom.utils.Internet
import com.example.ellcom.utils.timerForWatchingMainContent
import com.example.ellcom.viewmodal.MainAndSubViewModal
import kotlinx.android.synthetic.main.fragment_profile.*


class ProfileFragment : Fragment() {

    private val model: MainAndSubViewModal by activityViewModels()
    private val args: ProfileFragmentArgs by navArgs()

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
                if (args.isSubContract) setMainText(args.companyName, args.rate, args.balance)
                else getInfoMainProfile(token)

                buttonExit.setOnClickListener {
                    exit()
                }

                changePassword.setOnClickListener {
                    if (args.isSubContract) {
                        findNavController().navigate(
                            ProfileFragmentDirections.actionProfileFragmentToChangePasswordContractAndInternetFragment(
                                false,
                                args.servId
                            )
                        )
                    } else {
                        findNavController()
                            .navigate(ProfileFragmentDirections.actionProfileFragmentToChangePasswordFragment())
                    }
                }

                buttonSession.setOnClickListener {
                    findNavController()
                        .navigate(ProfileFragmentDirections.actionProfileFragmentToSessionFragment2())
                }

                bottonTraffic.setOnClickListener {
                    findNavController()
                        .navigate(ProfileFragmentDirections.actionProfileFragmentToTrafficFragment())
                }
            }
        }
    }

    private fun getInfoMainProfile(token: String) {
        model.infoProfile(token).observe(viewLifecycleOwner) {
            if (it.status == "ok") {
                val rate = it.data.res.rateList[0].tariffTitle
                    .substringAfter("\"")
                    .substringBefore("\"")

                setMainText(it.data.res.name, rate, "${it.data.res.balance}₽")
            }
        }
    }

    private fun setMainText(company: String, rate: String, balance: String) {
        textViewCompany.text = company
        textViewRateDown.text = rate
        textViewBalanceDown.text = balance
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
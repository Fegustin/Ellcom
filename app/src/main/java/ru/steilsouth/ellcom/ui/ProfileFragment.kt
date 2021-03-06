package ru.steilsouth.ellcom.ui

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import kotlinx.android.synthetic.main.fragment_profile.*
import ru.steilsouth.ellcom.R
import ru.steilsouth.ellcom.utils.isOnline
import ru.steilsouth.ellcom.utils.subscribeNotification
import ru.steilsouth.ellcom.utils.timerForWatchingMainContent
import ru.steilsouth.ellcom.viewmodal.DataBaseVM
import ru.steilsouth.ellcom.viewmodal.MainAndSubVM


class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private val model: MainAndSubVM by activityViewModels()
    private val args: ProfileFragmentArgs by navArgs()
    private val modelDB: DataBaseVM by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        timerForWatchingMainContent(progressBar, layoutContent)

        val token =
            activity?.getSharedPreferences("SP_INFO", Context.MODE_PRIVATE)?.getString("token", "")

        if (isOnline(requireContext())) {
            if (token != null) {
                if (args.isSubContract) setMainText(args.companyName, args.rate, args.balance)
                else getInfoMainProfile(token)

                buttonExit.setOnClickListener {
                    exit()
                }

                buttonChangePassword.setOnClickListener {
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

                buttonEmail.setOnClickListener {
                    findNavController()
                        .navigate(ProfileFragmentDirections.actionProfileFragmentToEmailListFragment())
                }

                buttonContractChange.setOnClickListener {
                    findNavController()
                        .navigate(ProfileFragmentDirections.actionProfileFragmentToContractChangeFragment())
                }
            }
        }
    }

    private fun getInfoMainProfile(token: String) {
        if (isOnline(requireContext())) {
            model.infoProfile(token).observe(viewLifecycleOwner) {
                if (it.status == "ok") {
                    val rate = it.data.res.rateList[0].tariffTitle
                        .substringAfter("\"")
                        .substringBefore("\"")

                    setMainText(it.data.res.name, rate, "${it.data.res.balance}???")
                }
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
            .setTitle("??????????")
            .setMessage("???? ?????????????????????????? ???????????? ?????????? ???? ?????????????????")
            .setPositiveButton("????") { _, _ ->

                subscribeNotification(requireContext(), false)

                activity?.getSharedPreferences("SP_INFO", Context.MODE_PRIVATE)
                    ?.edit()
                    ?.clear()
                    ?.apply()

                modelDB.deleteAll()

                findNavController()
                    .navigate(R.id.action_global_authFragment)
            }
            .setNegativeButton("??????") { _, _ -> }
            .show()
    }
}
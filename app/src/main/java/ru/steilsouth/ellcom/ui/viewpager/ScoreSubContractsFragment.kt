package ru.steilsouth.ellcom.ui.viewpager

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import kotlinx.android.synthetic.main.fragment_main_score.*
import kotlinx.android.synthetic.main.fragment_score_sub_contracts.*
import kotlinx.android.synthetic.main.fragment_score_sub_contracts.buttonCreateScore
import ru.steilsouth.ellcom.R
import ru.steilsouth.ellcom.adapter.ScoreSubItem
import ru.steilsouth.ellcom.pojo.bills.createbills.SubMobileContract
import ru.steilsouth.ellcom.utils.isOnline
import ru.steilsouth.ellcom.utils.openFile
import ru.steilsouth.ellcom.utils.saveFilePDF
import ru.steilsouth.ellcom.viewmodal.BillsVM
import ru.steilsouth.ellcom.viewmodal.ContactVM
import ru.steilsouth.ellcom.viewmodal.MainAndSubVM


class ScoreSubContractsFragment : Fragment(R.layout.fragment_score_sub_contracts) {

    private val modelBills: BillsVM by activityViewModels()
    private val modelMainAndSub: MainAndSubVM by activityViewModels()
    private val modelContact: ContactVM by activityViewModels()

    private val adapter = GroupAdapter<GroupieViewHolder>()

    private val infoProfile = mutableMapOf<String, String>()
    private val subContractList = mutableListOf<SubMobileContract>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerViewCopeSub.adapter = adapter


        val token =
            activity?.getSharedPreferences("SP_INFO", Context.MODE_PRIVATE)?.getString("token", "")

        if (token != null) {
            getAccountant(token)
            initRecyclerView(token)

            buttonCreateScore.setOnClickListener {
                fillSubContractList()

                progressBarScoreSub.visibility = View.VISIBLE
                buttonCreateScore.visibility = View.GONE
                createAnInvoice(token)
            }
        }
    }

    private fun createAnInvoice(token: String) {
        if (!isOnline(requireContext())) {
            progressBarScoreSub.visibility = View.GONE
            buttonCreateScore.visibility = View.VISIBLE
            return
        }
        val contract = infoProfile["contract"]
        val accountant = infoProfile["accountant"]
        if (subContractList.isNullOrEmpty()) {
            Toast.makeText(
                activity,
                "?????????????? ???????????????????? ?????????????? ???????? ???? ???? ?????????? ????????????????",
                Toast.LENGTH_SHORT
            ).show()
            progressBarScoreSub.visibility = View.GONE
            buttonCreateScore?.visibility = View.VISIBLE
            return
        }
        if (!contract.isNullOrEmpty() && !accountant.isNullOrEmpty()) {
            modelBills.createBills(token, contract, accountant, subContractList)
                .observe(viewLifecycleOwner) {
                    if (it.isSuccessful) {
                        val res = it.body()
                        if (res != null) {
                            if (res.status == "ok") {
                                val pdfData = res.data as String
                                val path = saveFilePDF(pdfData, requireContext())
                                AlertDialog.Builder(requireContext())
                                    .setTitle("??????????")
                                    .setMessage(
                                        "???????? ?????????????????????? ?? ???????????????? ???? ???????? $path"
                                    )
                                    .setNegativeButton("?????????????? ????????") { _, _ -> }
                                    .setPositiveButton("?????????????? ??????????") { _, _ ->
                                        activity?.let { it1 -> openFile(it1) }
                                    }
                                    .show()
                            } else Toast.makeText(activity, res.message, Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Toast.makeText(
                            activity,
                            "??????-???? ?????????? ???? ??????. ???????????????????? ?? ??????????????????",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    progressBarScoreSub.visibility = View.GONE
                    buttonCreateScore?.visibility = View.VISIBLE
                }
        } else {
            progressBarScoreSub.visibility = View.GONE
            buttonCreateScore?.visibility = View.VISIBLE
            Toast.makeText(
                activity,
                "??????-???? ?????????? ???? ??????. ???????????????????? ?? ??????????????????",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun initRecyclerView(token: String) {
        if (!isOnline(requireContext())) return
        modelMainAndSub.infoProfile(token).observe(viewLifecycleOwner) {
            if (it == null) return@observe
            if (it.status == "ok") {
                infoProfile["contract"] = it.data.res.contract_num

                adapter.clear()
                for (i in it.data.res.subMobileContract) {
                    adapter.add(ScoreSubItem(i))
                }
            } else {
                Toast.makeText(
                    activity,
                    "??????-???? ?????????? ???? ??????. ???????????????????? ?? ??????????????????",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun getAccountant(token: String) {
        if (!isOnline(requireContext())) return
        modelContact.getMobileContact(token).observe(viewLifecycleOwner) {
            if (it == null) return@observe
            if (it.status == "ok") {
                infoProfile["accountant"] = it.data.res.accountant
            } else {
                Toast.makeText(
                    activity,
                    "??????-???? ?????????? ???? ??????. ???????????????????? ?? ??????????????????",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun fillSubContractList() {
        subContractList.clear()
        for (i in 0 until adapter.itemCount) {
            val subContract = adapter.getItem(i) as ScoreSubItem

            if (subContract.quantity <= 0) continue

            val type =
                "?????????????????????? ?????????? ???? ???????????? " + subContract.item.rateList[0].tariffTitle

            subContractList.add(
                SubMobileContract(
                    subContract.item.title,
                    type,
                    subContract.quantity,
                    subContract.price,
                    type,
                )
            )
        }
    }
}
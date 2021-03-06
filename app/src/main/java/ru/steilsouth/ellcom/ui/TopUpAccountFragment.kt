package ru.steilsouth.ellcom.ui

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_top_up_account.*
import ru.steilsouth.ellcom.R
import ru.steilsouth.ellcom.utils.enam.Path
import ru.steilsouth.ellcom.utils.getPermission
import ru.steilsouth.ellcom.utils.isOnline


class TopUpAccountFragment : Fragment(R.layout.fragment_top_up_account) {

    private val PERMISSION_REQUEST_CODE = 1234

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        imageViewIoMoney.setOnClickListener {
            otherPayment("https://yoomoney.ru/oplata/ellco")
        }
        imageViewUbank.setOnClickListener {
            otherPayment("https://www.ubank.ru/pay/internet/ellco/")
        }
        imageViewQiwi.setOnClickListener {
            otherPayment("https://qiwi.com/payment/form/1559")
        }

        buttonScore.setOnClickListener {
            getPermission(
                requireContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                "Для формирования счета нам нужны разрешения на хранение файла",
                PERMISSION_REQUEST_CODE
            ) { findNavController().navigate(TopUpAccountFragmentDirections.actionTopUpAccountFragmentToCreateBillsFragment()) }
        }

        buttonHistoryPayment.setOnClickListener {
            findNavController()
                .navigate(TopUpAccountFragmentDirections.actionTopUpAccountFragmentToBillsFragment())
        }

        buttonRegulation.setOnClickListener {
            findNavController()
                .navigate(TopUpAccountFragmentDirections.actionGlobalReaderPDFFragment(Path.PaymentRules.path))
        }
    }

    private fun otherPayment(url: String) {
        if (!isOnline(requireContext())) return
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
    }
}
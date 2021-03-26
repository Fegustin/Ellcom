package ru.steilsouth.ellcom.ui

import android.Manifest
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_top_up_account.*
import ru.steilsouth.ellcom.R
import ru.steilsouth.ellcom.utils.enam.Path
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
            getPermission()
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

    private fun getPermission() {
        when {
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED -> {
                findNavController()
                    .navigate(TopUpAccountFragmentDirections.actionTopUpAccountFragmentToCreateBillsFragment())
            }
            shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE) -> {
                dialogPermission()
            }
            else -> {
                requestPermissions(
                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    PERMISSION_REQUEST_CODE
                )
            }
        }
    }

    private fun dialogPermission() {
        AlertDialog.Builder(requireContext())
            .setTitle("Разрешения")
            .setMessage("Для формирования счета нам нужны разрешения на хранение файла")
            .setPositiveButton("Дать разрешение") { _, _ ->
                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                val uri: Uri = Uri.fromParts("package", activity?.packageName, null)
                intent.data = uri
                startActivity(intent)
            }
            .setNegativeButton("Отмена") { _, _ -> }
            .show()
    }
}
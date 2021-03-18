package ru.steilsouth.ellcom.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import kotlinx.android.synthetic.main.fragment_reader_p_d_f.*
import ru.steilsouth.ellcom.R


class ReaderPDFFragment : Fragment(R.layout.fragment_reader_p_d_f) {

    private val args: ReaderPDFFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showPdfFromAssets(args.filePath)
    }

    private fun showPdfFromAssets(filePath: String) {
        pdfView.fromAsset(filePath)
            .password(null) // if password protected, then write password
            .defaultPage(0) // set the default page to open
            .onPageError { page, _ ->
                Toast.makeText(
                    activity,
                    "Ошибка на странице: $page", Toast.LENGTH_LONG
                ).show()
            }
            .load()
    }
}
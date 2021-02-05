package ru.steilsouth.ellcom.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_reader_p_d_f.*
import ru.steilsouth.ellcom.R


class ReaderPDFFragment : Fragment() {
    private val pdfName = "dogovor.pdf"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_reader_p_d_f, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showPdfFromAssets()
    }

    private fun showPdfFromAssets() {
        pdfView.fromAsset(pdfName)
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
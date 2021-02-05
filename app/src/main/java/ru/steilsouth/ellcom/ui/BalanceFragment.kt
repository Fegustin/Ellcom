package ru.steilsouth.ellcom.ui

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_main_screen.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.steilsouth.ellcom.R
import ru.steilsouth.ellcom.repository.BalanceRepository
import ru.steilsouth.ellcom.utils.Internet
import ru.steilsouth.ellcom.utils.timerForWatchingMainContent


class BalanceFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_balance, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        timerForWatchingMainContent(progressBar, layoutContent)

        val token =
            activity?.getSharedPreferences("SP_INFO", Context.MODE_PRIVATE)?.getString("token", "")

        if (!Internet().checkInternetConnecting(activity)) {
            Toast.makeText(activity, "Отсутствует подключение к интернету", Toast.LENGTH_SHORT)
                .show()
        } else {
            CoroutineScope(Dispatchers.IO).launch {
                if (token != null) {
                    BalanceRepository().getBalance(token, "2020-12-27")
                }
            }
        }
    }
}
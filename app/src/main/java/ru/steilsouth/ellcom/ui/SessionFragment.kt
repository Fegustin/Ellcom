package ru.steilsouth.ellcom.ui

import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import kotlinx.android.synthetic.main.fragment_session.*
import ru.steilsouth.ellcom.R
import ru.steilsouth.ellcom.adapter.SessionItem
import ru.steilsouth.ellcom.utils.isOnline
import ru.steilsouth.ellcom.viewmodal.ChangePasswordVM
import ru.steilsouth.ellcom.viewmodal.SessionVM
import java.text.SimpleDateFormat
import java.util.*


class SessionFragment : Fragment(R.layout.fragment_session) {

    private val modelSession: SessionVM by activityViewModels()
    private val model: ChangePasswordVM by activityViewModels()

    private var textDate = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val token =
            activity?.getSharedPreferences("SP_INFO", Context.MODE_PRIVATE)?.getString("token", "")

        if (isOnline(requireContext())) {
            if (token != null) {
                setContactInSpinner(token)

                spinnerLogin.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        parent: AdapterView<*>?,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {
                        getActiveSession(
                            token,
                            spinnerLogin.selectedItem.toString().substringAfter("№")
                        )
                    }

                    override fun onNothingSelected(parent: AdapterView<*>?) {}
                }

                layoutDate.setOnClickListener { dateDialogPikerFrom(token) }
            }
        }
    }

    private fun setContactInSpinner(token: String) {
        if (isOnline(requireContext())) {
            val array = mutableListOf<String>()
            model.getListServiceInternet(token).observe(viewLifecycleOwner) {
                if (it.status == "ok") {
                    for (i in it.data.res) array.add("Логин: №" + i.id.toString())
                    fillSpinner(array)
                } else Toast.makeText(activity, it.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun fillSpinner(array: List<String>) {
        val adapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, array)
        spinnerLogin.adapter = adapter
    }

    private fun getActiveSession(token: String, servId: String) {
        if (isOnline(requireContext())) {
            textViewInputTraffic.text = ""
            textViewOutputTraffic.text = ""
            modelSession.getActiveSession(token, servId.toInt()).observe(viewLifecycleOwner) {
                if (it.status == "ok") {
                    val adapter = GroupAdapter<GroupieViewHolder>()
                    for (i in it.data.res) {
                        adapter.add(SessionItem(i))

                        textViewInputTraffic.text =
                            String.format("%.1f", i.incomingTraffic * 0.00000762939453125)
                        textViewOutputTraffic.text =
                            String.format("%.1f", i.outgoingTraffic * 0.00000762939453125)
                    }
                    recyclerViewSessionActive.adapter = adapter
                    recyclerViewSessionActive.isNestedScrollingEnabled = false
                } else Toast.makeText(activity, it.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getHistorySession(token: String, servId: String, dateFrom: String, dateTo: String) {
        if (isOnline(requireContext())) {
            modelSession.getHistorySession(
                token,
                servId.toInt(),
                dateFrom,
                dateTo
            )
                .observe(viewLifecycleOwner) {
                    if (it.status == "ok") {
                        val adapter = GroupAdapter<GroupieViewHolder>()
                        for (i in it.data.res) {
                            adapter.add(SessionItem(i))
                        }
                        recyclerViewSessionHistory.adapter = adapter
                        recyclerViewSessionHistory.isNestedScrollingEnabled = false
                    } else Toast.makeText(activity, it.message, Toast.LENGTH_SHORT).show()
                }
        }
    }

    private fun dateDialogPikerFrom(token: String) {
        textDate = ""
        textViewDate.text = "_________"

        val c = Calendar.getInstance()
        val years = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        val pickerDialog = DatePickerDialog(
            requireContext(), { _, year, monthOfYear, dayOfMonth ->
                val calendar = Calendar.getInstance()
                var calendarTo = Calendar.getInstance()
                calendar.set(year, monthOfYear, dayOfMonth)

                if (monthOfYear == Calendar.getInstance().get(Calendar.MONTH)) {
                    calendarTo = c
                } else {
                    calendarTo.set(
                        year,
                        monthOfYear,
                        calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
                    )
                }
                textDate += "От " + dateFormat(year, monthOfYear, dayOfMonth, "MMM dd")
                dateDialogPikerTo(
                    token,
                    calendar.time.time,
                    calendarTo.time.time,
                    dateFormat(year, monthOfYear, dayOfMonth, "yyyy-MM-dd")
                )
            },
            years,
            month,
            day
        )
        pickerDialog.setTitle("С какого числа")
        pickerDialog.datePicker.maxDate = c.time.time
        pickerDialog.show()
    }

    private fun dateDialogPikerTo(token: String, minDate: Long, maxDate: Long, dateFrom: String) {
        val c = Calendar.getInstance()
        val years = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        val pickerDialog = DatePickerDialog(
            requireContext(), { _, year, monthOfYear, dayOfMonth ->
                val calendar = Calendar.getInstance()
                calendar.set(year, monthOfYear, dayOfMonth)

                textDate += "  До " + dateFormat(year, monthOfYear, dayOfMonth, "MMM dd")
                textViewDate.text = textDate
                getHistorySession(
                    token,
                    spinnerLogin.selectedItem.toString().substringAfter("№"),
                    dateFrom,
                    dateFormat(year, monthOfYear, dayOfMonth, "yyyy-MM-dd")
                )
            },
            years,
            month,
            day
        )

        pickerDialog.setTitle("До какого числа")
        pickerDialog.datePicker.minDate = minDate
        pickerDialog.datePicker.maxDate = maxDate
        pickerDialog.show()
    }

    private fun dateFormat(year: Int, monthOfYear: Int, dayOfMonth: Int, pattern: String): String {
        val calendar = Calendar.getInstance()
        calendar.set(year, monthOfYear, dayOfMonth)
        return SimpleDateFormat(pattern, Locale.getDefault()).format(calendar.time)
    }
}
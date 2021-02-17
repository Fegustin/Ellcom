package ru.steilsouth.ellcom.ui

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.LinearInterpolator
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import kotlinx.android.synthetic.main.fragment_balance.*
import kotlinx.android.synthetic.main.fragment_main_screen.layoutContent
import kotlinx.android.synthetic.main.fragment_main_screen.progressBar
import kotlinx.android.synthetic.main.include_balance.*
import ru.steilsouth.ellcom.R
import ru.steilsouth.ellcom.adapter.BalanceItem
import ru.steilsouth.ellcom.pojo.balance.BalanceList
import ru.steilsouth.ellcom.utils.Month
import ru.steilsouth.ellcom.utils.isOnline
import ru.steilsouth.ellcom.utils.timerForWatchingMainContent
import ru.steilsouth.ellcom.viewmodal.BalanceVM
import ru.steilsouth.ellcom.viewmodal.MainAndSubVM
import java.text.SimpleDateFormat
import java.util.*


class BalanceFragment : Fragment() {

    private val modelBalance: BalanceVM by activityViewModels()
    private val modelMainAndSub: MainAndSubVM by activityViewModels()

    private val subTokenMap = mutableMapOf<String, String>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_balance, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        timerForWatchingMainContent(progressBar, layoutContent)

        val token =
            activity?.getSharedPreferences("SP_INFO", Context.MODE_PRIVATE)?.getString("token", "")
        val isSuperContract = activity?.getSharedPreferences("SP_INFO", Context.MODE_PRIVATE)
            ?.getBoolean("isSuperContract", false)

        if (!isOnline(requireContext())) {
            Toast.makeText(activity, "Отсутствует подключение к интернету", Toast.LENGTH_SHORT)
                .show()
        } else {
            if (token != null && isSuperContract != null) {
                clickChangeMonth()

                setContactInSpinner(token, isSuperContract)

                changeMonth(token, isSuperContract)

                buttonTopUpAccount.setOnClickListener {
                    findNavController()
                        .navigate(BalanceFragmentDirections.actionBalanceFragmentToTopUpAccountFragment())
                }

                if (!isSuperContract) {
                    buttonDistributeFunds.visibility = View.GONE
                }

                buttonDistributeFunds?.setOnClickListener {
                    findNavController()
                        .navigate(BalanceFragmentDirections.actionBalanceFragmentToDistributeFundsFragment())
                }

                spinnerLogin.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        parent: AdapterView<*>?,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {
                        val textViewMonth: TextView = textSwitcherMonth.currentView as TextView
                        initBalance(
                            token,
                            getMonthNumber(textViewMonth.text.toString()),
                            isSuperContract
                        )
                    }

                    override fun onNothingSelected(parent: AdapterView<*>?) {}
                }
            }
        }
    }

    private fun initBalance(token: String, month: String, isSuperContract: Boolean) {
        if (isSuperContract) {
            val localToken = subTokenMap[spinnerLogin?.selectedItem.toString().substringAfter("№")]
            localToken?.let { setBalance(it, month) }
        } else {
            setBalance(token, month)
        }
    }

    private fun getMonthNumber(month: String): String {
        return when (month) {
            Month.January.rusName -> Month.January.number
            Month.February.rusName -> Month.February.number
            Month.March.rusName -> Month.March.number
            Month.April.rusName -> Month.April.number
            Month.May.rusName -> Month.May.number
            Month.June.rusName -> Month.June.number
            Month.July.rusName -> Month.July.number
            Month.August.rusName -> Month.August.number
            Month.September.rusName -> Month.September.number
            Month.October.rusName -> Month.October.number
            Month.November.rusName -> Month.November.number
            else -> Month.December.number
        }
    }

    private fun clickChangeMonth() {
        var isOpenClickComing = false
        var isOpenClickConsumption = false
        var isOpenOperatingTime = false

        layoutComingBalance?.setOnClickListener {
            if (isOpenClickComing) {
                isOpenClickComing = false
                animationDropDownInfoMonth(recyclerViewComingBalance, 0f, 100, 20)
            } else {
                isOpenClickComing = true
                animationDropDownInfoMonth(recyclerViewComingBalance, 1f, 60, 100)
            }
        }

        layoutConsumptionBalance?.setOnClickListener {
            if (isOpenClickConsumption) {
                isOpenClickConsumption = false
                animationDropDownInfoMonth(recyclerViewConsumptionBalance, 0f, 100, 20)
            } else {
                isOpenClickConsumption = true
                animationDropDownInfoMonth(recyclerViewConsumptionBalance, 1f, 60, 100)
            }
        }

        layoutOperatingTime?.setOnClickListener {
            if (isOpenOperatingTime) {
                isOpenOperatingTime = false
                animationDropDownInfoMonth(recyclerViewOperatingTime, 0f, 100, 50)
            } else {
                isOpenOperatingTime = true
                animationDropDownInfoMonth(recyclerViewOperatingTime, 1f, 60, 100)
            }
        }
    }

    private fun animationDropDownInfoMonth(
        view: View,
        valueAlpha: Float,
        vararg valuesMargin: Int
    ) {
        // Margin Anim
        ValueAnimator.ofInt(*valuesMargin).apply {
            duration = 150
            addUpdateListener {
                if (valueAlpha == 1f) view.visibility = View.VISIBLE
                val lp = view.layoutParams as FrameLayout.LayoutParams
                lp.setMargins(0, (it.animatedValue as Int), 0, 0)
                view.layoutParams = lp
            }
            start()
        }
        // Alpha anim
        view.animate().apply {
            interpolator = LinearInterpolator()
            duration = 100
            alpha(valueAlpha)
            start()
            setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator?) {
                    super.onAnimationEnd(animation)
                    if (valueAlpha == 0f) view.visibility = View.GONE
                }
            })
        }
    }

    private fun setContactInSpinner(token: String, isSuperContract: Boolean) {
        val array = mutableListOf<String>()
        modelMainAndSub.infoProfile(token).observe(viewLifecycleOwner) { infoResult ->
            if (infoResult.status == "ok") {
                subTokenMap[infoResult.data.res.contract_num] = token
                array.add("Логин: №" + infoResult.data.res.contract_num)

                if (isSuperContract) {
                    modelMainAndSub.getSubContractsList(token).observe(viewLifecycleOwner) {
                        if (it.status == "ok") {
                            for (i in it.data.res) {
                                subTokenMap[i.id.toString()] = i.token
                                array.add("Логин: №" + i.id.toString())
                            }
                            fillSpinner(array)
                        } else Toast.makeText(activity, it.message, Toast.LENGTH_SHORT).show()
                    }
                } else fillSpinner(array)
            } else Toast.makeText(activity, infoResult.message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun fillSpinner(array: List<String>) {
        val adapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, array)
        spinnerLogin.adapter = adapter
    }

    private fun setBalance(token: String, month: String) {
        val year = getDate("yyyy")
        val day = getDate("dd")
        modelBalance.getBalance(token, "${year}-${month}-${day}").observe(viewLifecycleOwner) {
            if (it.status == "ok") {
                val response = it.data.res
                textViewInputRemainder.text = response.incomingSaldo.toString()
                layoutComingBalanceSum.text = response.accounts.toString()
                layoutConsumptionBalanceSum.text = response.payments.toString()
                layoutOperatingTimeSum.text = response.charges.toString()
                textViewOutputRemainder.text = response.outgoingSaldo.toString()
                textViewLimit.text = response.limit.toString()

                fillRecyclerViews(recyclerViewComingBalance, response.accountList)
                fillRecyclerViews(recyclerViewConsumptionBalance, response.paymentList)
                fillRecyclerViews(recyclerViewOperatingTime, response.chargeList)
            } else Toast.makeText(activity, it.message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun fillRecyclerViews(recycler: RecyclerView, array: List<BalanceList>) {
        val adapter = GroupAdapter<GroupieViewHolder>()
        for (i in array) {
            adapter.add(BalanceItem(i))
        }
        recycler.adapter = adapter
        recycler.isNestedScrollingEnabled = false
    }

    private fun buttonAnimationHorizontal(
        button: View,
        vararg values: Float
    ) {
        val animator = ObjectAnimator.ofFloat(button, View.TRANSLATION_X, *values)
        animator.duration = 200
        animator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationStart(animation: Animator?) {
                button.isEnabled = false
            }

            override fun onAnimationEnd(animation: Animator?) {
                button.isEnabled = true
            }
        })
        animator.start()
    }

    private fun initAnimTextSwitcher(animIn: Int, animOut: Int) {
        textSwitcherMonth.setInAnimation(context, animIn)
        textSwitcherMonth.setOutAnimation(context, animOut)
    }

    private fun changeMonth(token: String, isSuperContract: Boolean) {
        val arrayMonth = resources.getStringArray(R.array.month)

        var currentIndex = 0
        var isFirstTimeClicked = true

        imageButtonArrowRight.setOnClickListener {
            buttonAnimationHorizontal(it, 0f, 30f, 0f)

            initAnimTextSwitcher(R.anim.slide_in_right, R.anim.slide_out_left)

            if (isFirstTimeClicked) {
                isFirstTimeClicked = false
                currentIndex = 0
            }

            currentIndex++
            if (currentIndex == arrayMonth.size) currentIndex = 0

            textSwitcherMonth.setText(arrayMonth[currentIndex])
            val textViewMonth: TextView = textSwitcherMonth.currentView as TextView
            initBalance(token, getMonthNumber(textViewMonth.text.toString()), isSuperContract)
        }

        imageButtonArrowLeft.setOnClickListener {
            buttonAnimationHorizontal(it, 0f, -30f, 0f)

            initAnimTextSwitcher(R.anim.slide_in_left, R.anim.slide_out_right)

            if (isFirstTimeClicked) {
                isFirstTimeClicked = false
                currentIndex = arrayMonth.size - 1
            }

            if (currentIndex == 0) currentIndex = arrayMonth.size
            currentIndex--

            textSwitcherMonth.setText(arrayMonth[currentIndex])
            val textViewMonth: TextView = textSwitcherMonth.currentView as TextView
            initBalance(token, getMonthNumber(textViewMonth.text.toString()), isSuperContract)
        }
    }

    private fun getDate(pattern: String): String {
        val calendar = Calendar.getInstance().time
        return SimpleDateFormat(pattern, Locale.getDefault()).format(calendar)
    }
}
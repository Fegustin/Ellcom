package ru.steilsouth.ellcom.ui

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import kotlinx.android.synthetic.main.fragment_balance.*
import kotlinx.android.synthetic.main.fragment_balance.spinnerLogin
import kotlinx.android.synthetic.main.fragment_main_screen.layoutContent
import kotlinx.android.synthetic.main.fragment_main_screen.progressBar
import kotlinx.android.synthetic.main.fragment_session.*
import ru.steilsouth.ellcom.R
import ru.steilsouth.ellcom.utils.Internet
import ru.steilsouth.ellcom.utils.timerForWatchingMainContent
import ru.steilsouth.ellcom.viewmodal.BalanceViewModal
import ru.steilsouth.ellcom.viewmodal.ChangePasswordViewModal


class BalanceFragment : Fragment() {

    private val model: BalanceViewModal by activityViewModels()
    private val modelLogin: ChangePasswordViewModal by activityViewModels()

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

        if (!Internet().checkInternetConnecting(activity)) {
            Toast.makeText(activity, "Отсутствует подключение к интернету", Toast.LENGTH_SHORT)
                .show()
        } else {
            if (token != null) {
                setContactInSpinner(token)

                changeMonth()
            }
        }
    }

    private fun setContactInSpinner(token: String) {
        val array = mutableListOf<String>()
        modelLogin.getListServiceInternet(token).observe(viewLifecycleOwner) {
            if (it.status == "ok") {
                for (i in it.data.res) array.add("Логин: №" + i.id.toString())
                fillSpinner(array)
            } else Toast.makeText(activity, it.message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun fillSpinner(array: List<String>) {
        val adapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, array)
        spinnerLogin.adapter = adapter
    }

    private fun getBalance(token: String, date: String) {
        model.getBalance(token, date).observe(viewLifecycleOwner) {

        }
    }

    private fun arrowButtonAnimation(button: View, vararg values: Float) {
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

    private fun changeMonth() {
        val arrayMonth = resources.getStringArray(R.array.month)

        var currentIndex = 0
        var isFirstSlide = true

        imageButtonArrowRight.setOnClickListener {
            arrowButtonAnimation(it, 0f, 30f, 0f)

            initAnimTextSwitcher(R.anim.slide_in_right, R.anim.slide_out_left)

            if (isFirstSlide) {
                isFirstSlide = false
                currentIndex = 0
            }

            currentIndex++
            if (currentIndex == arrayMonth.size) currentIndex = 0

            textSwitcherMonth.setText(arrayMonth[currentIndex])
        }

        imageButtonArrowLeft.setOnClickListener {
            arrowButtonAnimation(it, 0f, -30f, 0f)

            initAnimTextSwitcher(R.anim.slide_in_left, R.anim.slide_out_right)

            if (isFirstSlide) {
                isFirstSlide = false
                currentIndex = arrayMonth.size - 1
            }

            if (currentIndex == 0) currentIndex = arrayMonth.size
            currentIndex--

            textSwitcherMonth.setText(arrayMonth[currentIndex]);
        }
    }
}
package com.example.ellcom.ui

import android.content.Context
import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.ellcom.R
import com.example.ellcom.utils.Internet
import com.example.ellcom.viewmodal.AuthViewModal
import kotlinx.android.synthetic.main.fragment_forgot_password.*


class ForgotPasswordFragment : Fragment() {

    private val model: AuthViewModal by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_forgot_password, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (!Internet().checkInternetConnecting(activity)) {
            Toast.makeText(activity, "Отсутствует подключение к интернету", Toast.LENGTH_SHORT)
                .show()
        }

        forgotPasswordLayout.setOnClickListener {
            val inputManager =
                activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputManager.hideSoftInputFromWindow(
                activity?.currentFocus?.windowToken,
                InputMethodManager.HIDE_NOT_ALWAYS
            )
        }

        buttonSend.setOnClickListener {
            if (!Internet().checkInternetConnecting(activity)) {
                Toast.makeText(activity, "Отсутствует подключение к интернету", Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            }

            val number = editTextNumber.text.trim().toString()
            val email = editTextEmail.text.trim().toString()

            if (!validate(number, email)) return@setOnClickListener

            requestOneTimePassword(number, email)
        }
    }

    private fun requestOneTimePassword(number: String, email: String) {
        model.forgotPassword(number, email).observe(viewLifecycleOwner) {
            if (it.status == "ok") {
                loginComplete()

                Toast.makeText(
                    activity,
                    "На почту $email было высланно письмо для входа",
                    Toast.LENGTH_SHORT
                ).show()
            } else loginError(
                it.message,
                R.color.edit_text_warning,
                R.color.edit_text_warning
            )

        }
    }

    private fun validate(number: String, email: String): Boolean {
        return when {
            number.isEmpty() && email.isEmpty() -> {
                loginError(
                    "Введите номер договора и Email",
                    R.color.edit_text_warning,
                    R.color.edit_text_warning
                )
                false
            }
            number.isEmpty() -> {
                loginError(
                    "Введите номер договора",
                    R.color.edit_text_warning,
                    R.color.edit_text_neutral
                )
                false
            }
            email.isEmpty() -> {
                loginError(
                    "Введите Email",
                    R.color.edit_text_neutral,
                    R.color.edit_text_warning
                )
                false
            }
            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                loginError(
                    "Введен некорректный email",
                    R.color.edit_text_neutral,
                    R.color.edit_text_warning
                )
                false
            }
            else -> {
                true
            }
        }
    }

    private fun loginError(error: String, colorLogin: Int, colorPassword: Int) {
        activity?.let {
            editTextNumber.backgroundTintList =
                ContextCompat.getColorStateList(it, colorLogin)
            editTextEmail.backgroundTintList =
                ContextCompat.getColorStateList(it, colorPassword)
        }
        Toast.makeText(activity, error, Toast.LENGTH_SHORT).show()
    }

    private fun loginComplete() {
        activity?.let {
            editTextEmail.backgroundTintList =
                ContextCompat.getColorStateList(it, R.color.edit_text_neutral)
            editTextNumber.backgroundTintList =
                ContextCompat.getColorStateList(it, R.color.edit_text_neutral)
        }
    }
}
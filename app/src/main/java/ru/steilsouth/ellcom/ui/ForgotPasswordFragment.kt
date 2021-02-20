package ru.steilsouth.ellcom.ui

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
import kotlinx.android.synthetic.main.fragment_forgot_password.*
import ru.steilsouth.ellcom.R
import ru.steilsouth.ellcom.utils.isOnline
import ru.steilsouth.ellcom.viewmodal.AuthVM


class ForgotPasswordFragment : Fragment(R.layout.fragment_forgot_password) {

    private val model: AuthVM by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isOnline(requireContext())

        forgotPasswordLayout.setOnClickListener {
            val inputManager =
                activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputManager.hideSoftInputFromWindow(
                activity?.currentFocus?.windowToken,
                InputMethodManager.HIDE_NOT_ALWAYS
            )
        }

        buttonSend.setOnClickListener {
            if (!isOnline(requireContext())) {
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
        if (isOnline(requireContext())) {
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
package com.example.ellcom.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.ellcom.R
import com.example.ellcom.utils.Internet
import com.example.ellcom.viewmodal.AuthViewModal
import kotlinx.android.synthetic.main.fragment_auth.*


class AuthFragment : Fragment() {

    private val model: AuthViewModal by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_auth, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (!Internet().checkInternetConnecting(activity)) {
            Toast.makeText(activity, "Отсутствует подключение к интернету", Toast.LENGTH_SHORT)
                .show()
        }

        buttonEnter.setOnClickListener {
            if (!Internet().checkInternetConnecting(activity)) {
                Toast.makeText(activity, "Отсутствует подключение к интернету", Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            }

            val login = editTextLogin.text.trim().toString()
            val password = editTextPassword.text.trim().toString()

            if (!validate(login, password)) return@setOnClickListener

            val type = login.toIntOrNull() != null && login.length == 9

            model.auth(editTextLogin.text.toString(), editTextPassword.text.toString(), type)
                .observe(
                    viewLifecycleOwner
                ) {
                    if (it.status == "ok") {
                        loginComplete()

                        activity?.getSharedPreferences("SP_INFO", Context.MODE_PRIVATE)?.edit()
                            ?.apply {
                                putString("token", it.data.res.token)
                                apply()
                            }

                        findNavController()
                            .navigate(AuthFragmentDirections.actionAuthFragmentToMainScreenFragment())

                    } else loginError(
                        it.message,
                        R.color.edit_text_warning,
                        R.color.edit_text_warning
                    )
                }
        }

        buttonRegistration.setOnClickListener {
            findNavController()
                .navigate(AuthFragmentDirections.actionAuthFragmentToRegistrationFragment())
        }
    }

    private fun validate(login: String, password: String): Boolean {
        return when {
            login.isEmpty() && password.isEmpty() -> {
                loginError(
                    "Введите логин и пароль",
                    R.color.edit_text_warning,
                    R.color.edit_text_warning
                )
                false
            }
            login.isEmpty() -> {
                loginError(
                    "Введите логин",
                    R.color.edit_text_warning,
                    R.color.edit_text_neutral
                )
                false
            }
            password.isEmpty() -> {
                loginError(
                    "Введите пароль",
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
            editTextLogin.backgroundTintList =
                ContextCompat.getColorStateList(it, colorLogin)
            editTextPassword.backgroundTintList =
                ContextCompat.getColorStateList(it, colorPassword)
        }
        textViewError.visibility = View.VISIBLE
        textViewError.text = error
    }

    private fun loginComplete() {
        activity?.let {
            editTextLogin.backgroundTintList =
                ContextCompat.getColorStateList(it, R.color.edit_text_neutral)
            editTextPassword.backgroundTintList =
                ContextCompat.getColorStateList(it, R.color.edit_text_neutral)
        }
        textViewError.visibility = View.INVISIBLE
    }
}
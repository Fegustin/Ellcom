package ru.steilsouth.ellcom.ui

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_auth.*
import ru.steilsouth.ellcom.R
import ru.steilsouth.ellcom.utils.isOnline
import ru.steilsouth.ellcom.viewmodal.AuthVM


class AuthFragment : Fragment() {

    private val model: AuthVM by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_auth, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (!isOnline(requireContext())) {
            Toast.makeText(activity, "Отсутствует подключение к интернету", Toast.LENGTH_SHORT)
                .show()
        }

        // Вход по одноразовому паролю
        val data: Uri? = activity?.intent?.data
        data?.let { uri ->
            val token = uri.toString().removePrefix("https://bill.ellco.ru/my/passwordReset/")
            loginWithOneTimePassword(token)
        }
        // -------

        authLayout.setOnClickListener {
            val inputManager =
                activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputManager.hideSoftInputFromWindow(
                activity?.currentFocus?.windowToken,
                InputMethodManager.HIDE_NOT_ALWAYS
            )
        }

        buttonEnter.setOnClickListener {
            if (!isOnline(requireContext())) {
                Toast.makeText(activity, "Отсутствует подключение к интернету", Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            }

            val login = editTextLogin.text.trim().toString()
            val password = editTextPassword.text.trim().toString()

            if (!validate(login, password)) return@setOnClickListener

            val type = login.toIntOrNull() != null && login.length == 9

            auth(login, password, type)
        }

        buttonRegistration.setOnClickListener {
            findNavController()
                .navigate(AuthFragmentDirections.actionAuthFragmentToRegistrationFragment())
        }

        buttonForgotPassword.setOnClickListener {
            findNavController()
                .navigate(AuthFragmentDirections.actionAuthFragmentToForgotPasswordFragment())
        }
    }

    private fun auth(login: String, password: String, type: Boolean) {
        model.auth(login, password, type)
            .observe(
                requireActivity()
            ) {
                if (it.status == "ok") {
                    loginComplete()

                    initSharedPreferencesForAuth(it.data.res.token, false)

                    findNavController()
                        .navigate(AuthFragmentDirections.actionAuthFragmentToNavigationToMainContent())

                } else loginError(
                    it.message,
                    R.color.edit_text_warning,
                    R.color.edit_text_warning
                )
            }
    }

    private fun loginWithOneTimePassword(token: String) {
        model.authOneTimePassword(token).observe(requireActivity()) {
            if (it.status == "ok") {
                initSharedPreferencesForAuth(it.data.res.token, true)

                findNavController()
                    .navigate(AuthFragmentDirections.actionAuthFragmentToNavigationToMainContent())
            } else Toast.makeText(activity, it.message, Toast.LENGTH_SHORT).show()
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

    private fun initSharedPreferencesForAuth(token: String, disposable: Boolean) {
        activity?.getSharedPreferences("SP_INFO", Context.MODE_PRIVATE)?.edit()?.apply {
            putString("token", token)
            putBoolean("hasEntered", true)
            putBoolean("disposable", disposable)
            apply()
        }
    }
}
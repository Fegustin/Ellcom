package com.example.ellcom.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.ellcom.R
import com.example.ellcom.utils.Internet
import com.example.ellcom.utils.validation
import com.example.ellcom.utils.validationConfirmPassword
import com.example.ellcom.viewmodal.ChangePasswordViewModal
import kotlinx.android.synthetic.main.fragment_change_password_contract_and_internet.*


/**
 * В классе используются экстеншен функции для EditText:
 * validation(), validationConfirmPassword()
 * они из файла Validation в папке utils
 */
class ChangePasswordContractAndInternetFragment : Fragment() {

    private val model: ChangePasswordViewModal by activityViewModels()
    private val args: ChangePasswordContractAndInternetFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(
            R.layout.fragment_change_password_contract_and_internet, container, false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val token =
            activity?.getSharedPreferences("SP_INFO", Context.MODE_PRIVATE)?.getString("token", "")

        if (!Internet().checkInternetConnecting(activity)) {
            Toast.makeText(activity, "Отсутствует подключение к интернету", Toast.LENGTH_SHORT)
                .show()
        } else {
            setTitleToolbar(args.isContract)

            buttonEnter.setOnClickListener {
                var error = 0

                if (!oldPassword.validation()) error++
                if (!newPassword.validation()) error++
                if (!confirmPassword.validation()) error++

                if (error > 0) {
                    Toast.makeText(activity, "Заполните поля", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                if (!confirmPassword.validationConfirmPassword(newPassword.text.toString())) {
                    Toast.makeText(activity, "Пароли не совпадают", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                if (token != "") token?.let { it1 ->
                    passwordChange(
                        it1,
                        oldPassword.text.toString(),
                        newPassword.text.toString(),
                        args.isContract,
                        args.servId
                    )
                }
            }
        }
    }

    private fun passwordChange(
        token: String,
        oldPassword: String,
        newPassword: String,
        isContract: Boolean,
        servId: Int
    ) {
        model.passwordChange(token, oldPassword, newPassword, isContract, servId)
            .observe(viewLifecycleOwner) {
                if (it.status != "ok") {
                    Toast.makeText(activity, "Вы успешно сменили пароль", Toast.LENGTH_SHORT)
                        .show()
                    findNavController().navigate(ChangePasswordFragmentDirections.actionGlobalAuthFragment())
                } else Toast.makeText(activity, it.message, Toast.LENGTH_SHORT)
                    .show()
            }
    }

    private fun setTitleToolbar(isContract: Boolean) {
        val title = activity?.findViewById<TextView>(R.id.textViewTitle)
        if (isContract) title?.text = "Смена пароля (договор)"
        else title?.text = "Смена пароля (Internet)"
    }
}
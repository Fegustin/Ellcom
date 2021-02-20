package ru.steilsouth.ellcom.ui

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
import kotlinx.android.synthetic.main.fragment_change_password_contract_and_internet.*
import ru.steilsouth.ellcom.R
import ru.steilsouth.ellcom.utils.isOnline
import ru.steilsouth.ellcom.utils.validation
import ru.steilsouth.ellcom.utils.validationConfirmPassword
import ru.steilsouth.ellcom.viewmodal.ChangePasswordVM


/**
 * В классе используются экстеншен функции для EditText:
 * validation(), validationConfirmPassword()
 * они из файла Validation в папке utils
 */
class ChangePasswordContractAndInternetFragment : Fragment(R.layout.fragment_change_password_contract_and_internet) {

    private val model: ChangePasswordVM by activityViewModels()
    private val args: ChangePasswordContractAndInternetFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val token =
            activity?.getSharedPreferences("SP_INFO", Context.MODE_PRIVATE)?.getString("token", "")
        val isSuperContract =
            activity?.getSharedPreferences("SP_INFO", Context.MODE_PRIVATE)
                ?.getBoolean("isSuperContract", true)

        if (isOnline(requireContext())) {
            if (isSuperContract != null) {
                setTitleToolbar(args.isContract, isSuperContract)
            }

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
        if (isOnline(requireContext())) {
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
    }

    private fun setTitleToolbar(isContract: Boolean, isSuperContract: Boolean) {
        val title = activity?.findViewById<TextView>(R.id.textViewTitle)
        if (isContract || !isSuperContract) title?.text = "Смена пароля (договор)"
        else title?.text = "Смена пароля (Internet)"
    }
}
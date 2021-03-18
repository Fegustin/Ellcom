package ru.steilsouth.ellcom.ui

import android.content.Context
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
import com.redmadrobot.inputmask.MaskedTextChangedListener
import kotlinx.android.synthetic.main.fragment_registration.*
import ru.steilsouth.ellcom.R
import ru.steilsouth.ellcom.utils.*
import ru.steilsouth.ellcom.utils.enam.FilePath
import ru.steilsouth.ellcom.viewmodal.RegistrationVM


/**
 * В классе используются экстеншен функции для EditText:
 * validation(), validationPhone() и validationEmail()
 * они из файла Validation в папке utils
 */
class RegistrationFragment : Fragment(R.layout.fragment_registration) {

    private val model: RegistrationVM by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isOnline(requireContext())

        setFormatPhone()

        registrationLayout.setOnClickListener {
            val inputManager =
                activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputManager.hideSoftInputFromWindow(
                activity?.currentFocus?.windowToken,
                InputMethodManager.HIDE_NOT_ALWAYS
            )
        }

        buttonSend.setOnClickListener {
            if (!isOnline(requireContext())) {
                return@setOnClickListener
            }

            var error = 0

            if (!checkBoxAgreement.isChecked) {
                checkBoxAgreement.setTextColor(resources.getColor(R.color.red))
                Toast.makeText(activity, "Примите условия соглашения", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            } else {
                checkBoxAgreement.setTextColor(resources.getColor(R.color.black))
            }
            if (!editTextEmail.text.isNullOrEmpty()) {
                if (!editTextEmail.validationEmail()) {
                    Toast.makeText(activity, "Введите корректный email", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
            } else {
                editTextEmail.backgroundTintList =
                    activity?.let { it1 ->
                        ContextCompat.getColorStateList(
                            it1,
                            R.color.edit_text_neutral
                        )
                    }
            }


            if (!editTextName.validation()) error++
            if (!editTextAddress.validation()) error++
            if (!editTextTel.validationPhone()) error++

            if (error > 0) {
                Toast.makeText(activity, "Заполните поля", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            registration(
                editTextName.text.trim().toString(),
                editTextCompany.text.trim().toString(),
                editTextTel.text.trim().toString(),
                editTextAddress.text.trim().toString(),
                editTextEmail.text.trim().toString()
            )
        }

        buttonRead.setOnClickListener {
            findNavController()
                .navigate(RegistrationFragmentDirections.actionGlobalReaderPDFFragment(FilePath.RegistrationAgreement.path))
        }
    }

    private fun registration(
        name: String,
        nameCompany: String = "",
        phone: String,
        address: String,
        email: String = ""
    ) {
        if (isOnline(requireContext())) {
            model.registration(name, nameCompany, phone, address, email)
                .observe(viewLifecycleOwner) {
                    if (it.isSuccessful) {
                        if (it.body()?.get("error")?.asBoolean == false) {
                            Toast.makeText(
                                activity,
                                "Ваша заявка успешно отправлена",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            Toast.makeText(
                                activity,
                                it.body()?.get("text").toString(),
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                }
        }
    }

    private fun setFormatPhone() {
        val listener = MaskedTextChangedListener("+7 ([000]) [000]-[00]-[00]", editTextTel)
        editTextTel.addTextChangedListener(listener)
        editTextTel.onFocusChangeListener = listener
    }
}
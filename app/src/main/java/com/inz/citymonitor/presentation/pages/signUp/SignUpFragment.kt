package com.inz.citymonitor.presentation.pages.signUp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.afollestad.materialdialogs.MaterialDialog

import com.inz.citymonitor.R
import com.inz.citymonitor.data.model.ErrorResponseModel
import com.inz.citymonitor.data.model.SuccesResponseModel
import com.inz.citymonitor.presentation.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_sign_up.*
import kotlin.math.E

class SignUpFragment : BaseFragment() {
    override fun setTopBarTitle() = "Zarejestruj"

    val viewModel by lazy { SignUpViewModel() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_sign_up, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        signUp.setOnClickListener {

            var lastName: String? = null
            var firstName: String? = null
            if (!lastname.text.isNullOrBlank()) {
                lastName = lastname.text?.toString();
            }
            if (!firstname.text.isNullOrBlank()) {
                firstName = firstname.text?.toString()
            }
            if (checkFields()) {
                viewModel.signUpUser(
                    userName.text.toString(),
                    password.text.toString(),
                    email.text.toString(),
                    firstName,
                    lastName
                )
            }else{

            }
        }
        viewModel.callResult.observe(viewLifecycleOwner, Observer {

            when (it) {
                is SuccesResponseModel -> {
                    findNavController().navigate(R.id.mapFragment);
                };
                is ErrorResponseModel -> {

                    activity?.let { activity ->
                        MaterialDialog(activity).show {
                            title(text = it.details)
                            message(
                                text = it.fields?.joinToString(
                                    transform = { field -> "${field.fieldName} ${field.details}" },
                                    separator = "\n"
                                )
                            )
                            positiveButton(text = "Try again") {
                                clearFields()
                                dismiss()
                            }
                        }
                    }
                }
            }
        })
    }

    private fun checkFields(): Boolean {
        val EMAIL: String = "^[a-zA-Z\\d\\.]+?@[a-zA-Z\\d]+?\\..{2,}[^\\.\$&+,:;=?@#|'<>.^*()%!-]\$"
        if (userName.text.toString().length < 4 || userName.text.toString().length > 30) {
            Toast.makeText(
                context,
                "Nazwa użytkownika powinna mieć od 4 do 10 znaków ",
                Toast.LENGTH_SHORT
            ).show()
            return false;
        }
        if (password.text.toString().length < 4 || password.text.toString().length > 20) {
            Toast.makeText(
                context,
                "Nazwa użytkokonwika powinna mieć od 4 do 20 znaków ",
                Toast.LENGTH_SHORT
            ).show()
            return false;
        }
        if (EMAIL.toRegex().find(email.text.toString()) == null) {
            Toast.makeText(
                context,
                "Adres mail nie poprawny ",
                Toast.LENGTH_SHORT
            ).show()
            return false;

        }
        if (email.text.toString().length < 4 || email.text.toString().length > 30) {
            Toast.makeText(
                context,
                "Adres mail powinein mieścić w przedziale  od 4 do 30 znaków  ",
                Toast.LENGTH_SHORT
            ).show()
            return false;


        }

        return true
    }

    private fun clearFields() {
        lastname.setText("")
        firstname.setText("")
        email.setText("")
        password.setText("")
        userName.setText("")
    }

}

package com.inz.citymonitor.presentation.pages.account.editProfile

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
import kotlinx.android.synthetic.main.fragment_edit_profile.*
import kotlinx.android.synthetic.main.fragment_edit_profile.email
import kotlinx.android.synthetic.main.fragment_reset_password.*

class EditProfileFragment : BaseFragment() {
    override fun setTopBarTitle() = "Edytuj profil"


    val viewModel by lazy { EditProfileViewModel() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_edit_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        changePassword.setOnClickListener {
            findNavController().navigate(R.id.changePasswordFragment)
        }

        update.setOnClickListener {

            var lastName: String? = null
            var firstName: String? = null
            var emailAdress: String? = null
            var username: String? = null

            if (!userName.text.isNullOrBlank()) {
                username = userName.text?.toString()
            }
            if (!email.text.isNullOrBlank()) {
                emailAdress = email.text?.toString()
            }
            if (!firstname.text.isNullOrBlank()) {
                firstName = firstname.text?.toString()
            }
            if (!lastname.text.isNullOrBlank()) {
                lastName = lastname.text?.toString();
            }
            if (lastName != null && firstName != null && emailAdress != null && username != null) {
                if (checkFields(lastName, username, firstName, emailAdress))
                    viewModel.editProfile(username, emailAdress, firstName, lastName)
            } else {
                Toast.makeText(context, "nie zmieniles zadnego pola", Toast.LENGTH_SHORT).show()
            }



            viewModel.callResult.observe(viewLifecycleOwner, Observer {

                when (it) {
                    is SuccesResponseModel -> {
                        viewModel.localStorage.logOut()
                        Toast.makeText(context, "OK zalgouj ponowanie ", Toast.LENGTH_SHORT).show()
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
    }

    private fun checkFields(
        lastName: String?,
        username: String?,
        firstName: String?,
        emailAdress: String?
    ): Boolean {
        if (lastName != null) {
            if (lastName.length < 4 || lastName.length > 10) {
                Toast.makeText(
                    context,
                    "naziwsko musi byc w przedziale od 4 do 10 znaków ",
                    Toast.LENGTH_SHORT
                ).show()
                return false
            }

        }
        if (username != null) {
            if (username.length < 4 || username.length > 10) {
                Toast.makeText(context, "login musi byc od 4 do 10 znaków ", Toast.LENGTH_SHORT)
                    .show()
                return false
            }
        }
        if (firstName != null) {
            if (firstName.length < 4 || firstName.length > 10) {
                Toast.makeText(context, "imie musi byc od 4 do 10 znaków ", Toast.LENGTH_SHORT)
                    .show()
                return false
            }
        }

        if (emailAdress != null) {
            val EMAIL: String =
                "^[a-zA-Z\\d\\.]+?@[a-zA-Z\\d]+?\\..{2,}[^\\.\$&+,:;=?@#|'<>.^*()%!-]\$"
            if (EMAIL.toRegex().find(email.text.toString()) == null) {
                Toast.makeText(
                    context,
                    "Adres mail nie poprawny ",
                    Toast.LENGTH_SHORT
                ).show()
                return false

            }
            if (email.text.toString().length < 4 || email.text.toString().length > 30) {
                Toast.makeText(
                    context,
                    "Adres mail powinein miesicic w przedziale  od 4 do 30 znaków  ",
                    Toast.LENGTH_SHORT
                ).show()
                return false


            }
        }
        return true
    }


    private fun clearFields() {
        userName.text.clear()
        email.text.clear()
        firstname.text.clear()
        lastname.text.clear()
    }
}

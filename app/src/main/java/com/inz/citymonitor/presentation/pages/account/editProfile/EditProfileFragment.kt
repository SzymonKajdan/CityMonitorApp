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
            viewModel.editProfile(username, emailAdress, firstName, lastName)
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
                                        transform = { field -> "${field.field} ${field.details}" },
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


    private fun clearFields() {
        userName.text.clear()
        email.text.clear()
        firstname.text.clear()
        lastname.text.clear()
    }
}

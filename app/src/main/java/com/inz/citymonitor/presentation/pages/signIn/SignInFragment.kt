package com.inz.citymonitor.presentation.pages.signIn

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.afollestad.materialdialogs.MaterialDialog

import com.inz.citymonitor.R
import com.inz.citymonitor.data.model.ErrorResponseModel
import com.inz.citymonitor.data.model.SuccesResponseModel
import com.inz.citymonitor.data.model.User.SignInUserResponse
import com.inz.citymonitor.presentation.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_sign_in.*

class SignInFragment : BaseFragment() {

    val viewModel by lazy { SignInViewModel() }

    override fun setTopBarTitle() = "Zaloguj"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_sign_in, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        resetPassword.setOnClickListener {
            findNavController().navigate(R.id.resetPasswordFragment)
        }

        signIn.setOnClickListener {
            viewModel.signIn(userName.text.toString(), password.text.toString())
        }


        viewModel.callResult.observe(viewLifecycleOwner, Observer {

            when (it) {
                is SignInUserResponse -> {
                    findNavController().navigate(R.id.mapFragment);
                }
                is ErrorResponseModel -> {

                    activity?.let { activity ->
                        MaterialDialog(activity).show {
                            title(text = it.details)
                            if (!it.fields.isNullOrEmpty()) {
                                message(
                                    text = it.fields?.joinToString(
                                        transform = { field -> "${field.field} ${field.details}" },
                                        separator = "\n"
                                    )
                                )
                            }
                            positiveButton(text = "Try again") {
                                clearFields()
                                dismiss()
                            }
                        }
                    }
                }
                else -> activity?.let { activity ->
                    MaterialDialog(activity).show {
                        title(text = "Spróbuj pózniej")
                        positiveButton(text = "Ok") {
                            findNavController().navigate(R.id.mapFragment)
                        }
                    }
                }

            }
        })
    }

    private fun clearFields() {
        userName.text.clear()
        password.text.clear()
    }

}

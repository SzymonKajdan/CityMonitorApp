package com.inz.citymonitor.presentation.pages.account.resetPassword

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.afollestad.materialdialogs.MaterialDialog
import com.google.android.material.snackbar.Snackbar
import com.inz.citymonitor.R
import com.inz.citymonitor.data.model.ErrorResponseModel
import com.inz.citymonitor.data.model.SuccesResponseModel
import com.inz.citymonitor.presentation.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_reset_password.*

class ResetPasswordFragment : BaseFragment() {
    override fun setTopBarTitle() = "resetuj haslo "


    val viewModel by lazy { ResetPasswordViewModel() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_reset_password, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        resetPassword.setOnClickListener {
            if (email.text.isNullOrBlank()) {
                Snackbar.make(view, "Nie moze być puste ", Snackbar.LENGTH_SHORT).show();
            } else
                viewModel.resetPassword(email.text.toString())

            viewModel.callResult.observe(viewLifecycleOwner, Observer {

                when (it) {
                    is SuccesResponseModel -> {
                        Toast.makeText(
                            context,
                            "Jesli taki adres isntieje w bazie zostanie na niego wysłane nowe hasło ",
                            Toast.LENGTH_SHORT
                        ).show()
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
    }
}

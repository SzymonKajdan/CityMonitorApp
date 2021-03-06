package com.inz.citymonitor.presentation.pages.account.changePassword

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
import kotlinx.android.synthetic.main.fragment_change_password.*

class ChangePasswordFragment : BaseFragment() {
    override fun setTopBarTitle() = "Edytuj profil"

    val viewModel by lazy { ChangePasswordViewModel() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_change_password, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        changePassword.setOnClickListener {

            if (checkFields())
                viewModel.changePassword(
                    oldPassword.text.toString(),
                    newPassword.text.toString()
                )
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

    private fun checkFields(): Boolean {
        if (oldPassword.text.toString().length < 4 || oldPassword.text.toString().length > 30) {
            Toast.makeText(context, "stare hasło nie poprawne dlugosc od 4 do 30 ", Toast.LENGTH_SHORT).show()
            return false
        }
        if (newPassword.text.toString().length < 4 || newPassword.text.toString().length > 30) {
            Toast.makeText(context, "nowe hasło nie poprawne długosc od 4 do 30", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    fun clearFields() {
        newPassword.text.clear()
        oldPassword.text.clear()
    }
}

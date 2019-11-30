package com.inz.citymonitor.presentation.pages.account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.inz.citymonitor.R
import com.inz.citymonitor.data.model.User.LocalUser
import com.inz.citymonitor.presentation.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_account_details.*

class AccountDetailsFragment : BaseFragment() {
    override fun setTopBarTitle() = "Infomacje o Koncie"

    val viewModel by lazy { AccountDetailsViewModel() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_account_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val user = viewModel.getUser()
        setText(user)
        editProfil.setOnClickListener {
            findNavController().navigate(R.id.editProfileFragment)
        }
    }

    private fun setText(user: LocalUser?) {

        surname.text = getFormmatedString(R.string.surname, user?.surname)

        firstname.text = getFormmatedString(R.string.firstname, user?.firstname)

        login.text = getFormmatedString(R.string.username, user?.username)

        email.text = getFormmatedString(R.string.email, user?.email)

        rating.text = getString(R.string.rating, user?.userRating.toString())

        if (user?.isBanned == true) {
            isBanned.text = getString(R.string.isBanned, "Masz blokade :(.")
        } else {
            isBanned.text = getString(R.string.isBanned, "Nie jesteś zbanowany.")
        }
    }

    private fun getFormmatedString(id: Int, text: String?): String {
        return getString(id, text)
    }
}

package com.inz.citymonitor.presentation.pages.report.reportInfo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.afollestad.materialdialogs.MaterialDialog
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.inz.citymonitor.R
import com.inz.citymonitor.data.model.ErrorResponseModel
import com.inz.citymonitor.data.model.History.ReportDetailsModel
import com.inz.citymonitor.data.model.SuccesResponseModel
import com.inz.citymonitor.presentation.pages.history.HistoryFragmentDirections
import kotlinx.android.synthetic.main.fragment_history_details.*
import kotlinx.android.synthetic.main.fragment_report_info.*
import kotlinx.android.synthetic.main.history_item.*
import kotlinx.android.synthetic.main.history_item.photo

class ReportInfoFragment : BottomSheetDialogFragment() {


    val viewModel by lazy { ReportInfoViewModel() }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_report_info, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getReport(arguments?.getLong("id"))

        viewModel.callResult.observe(viewLifecycleOwner, Observer {
            when (it) {
                is String -> {
                    Toast.makeText(
                        context,
                        "nieoczkeiwnay bład spróboj pozniej ",
                        Toast.LENGTH_SHORT
                    ).show()
                    findNavController().navigate(com.inz.citymonitor.R.id.mapFragment)
                }
                is ErrorResponseModel -> {
                    activity?.let { activity ->
                        MaterialDialog(activity).show {
                            title(text = it.details)
                            if (!it.fields.isNullOrEmpty()) {
                                message(
                                    text = it.fields?.joinToString(
                                        transform = { field -> "${field.fieldName} ${field.details}" },
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
                is ReportDetailsModel -> {
                    setFileds(it)

                }
                is SuccesResponseModel -> {
                    Toast.makeText(context, it.d, Toast.LENGTH_SHORT).show()
                }
            }
        })

        setMarksListeners()
    }

    private fun setMarksListeners() {
        positiveClick()
        falseClick()
        notActiveclick()
        photo.setOnClickListener {
            val reportId = arguments?.getLong("id")
            if (reportId != null) {

                findNavController().navigate(
                    HistoryFragmentDirections.toHistoryDetailsFragment(
                        reportId
                    )
                )
            }
        }
    }

    private fun falseClick() {
        fasleButton.setOnClickListener {
            if (viewModel.isLogged()) {
                viewModel.markAsFalse(arguments?.getLong("id"))
            } else {
                Toast.makeText(context, "Nie jestes zalogowany", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun notActiveclick() {
        notActiveButton.setOnClickListener {
            if (viewModel.isLogged()) {
                viewModel.markAsNotActive(arguments?.getLong("id"))
            } else {
                Toast.makeText(context, "Nie jestes zalogowany", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun positiveClick() {
        fiveMarkButton.setOnClickListener {
            if (viewModel.isLogged()) {
                viewModel.markAdd(arguments?.getLong("id"), markPostive)
            } else {
                Toast.makeText(context, "Nie jestes zalogowany", Toast.LENGTH_SHORT).show()
            }
        }
        zeroMarkbButton.setOnClickListener {
            if (viewModel.isLogged()) {
                viewModel.markAdd(arguments?.getLong("id"), markNegative)
            } else {
                Toast.makeText(context, "Nie jestes zalogowany", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setFileds(report: ReportDetailsModel) {
        view?.let {
            Glide.with(it)
                .load(report.photo)
                .into(photo)
        }
        description.text = report.description
    }

    companion object {
        fun newInstance(id: Long): ReportInfoFragment {
            return ReportInfoFragment().apply {
                arguments = bundleOf("id" to id)
            }
        }

        const val markPostive = 5
        const val markNegative = 0
    }
}


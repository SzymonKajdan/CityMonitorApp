package com.inz.citymonitor.presentation.pages.history.historyDetails

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.afollestad.materialdialogs.MaterialDialog
import com.bumptech.glide.Glide
import com.inz.citymonitor.R
import com.inz.citymonitor.data.model.ErrorResponseModel
import com.inz.citymonitor.data.model.History.ReportDetailsModel
import com.inz.citymonitor.presentation.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_history_details.*

class HistoryDetailsFragment : BaseFragment() {
    override fun setTopBarTitle() = "Historia Zgloszenia "

    private val args: HistoryDetailsFragmentArgs by navArgs()
    val viewModel by lazy { HistoryDetailsViewModel() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_history_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        detailsScroll.isFocusableInTouchMode = true
        detailsScroll.smoothScrollTo(0, 0)
        viewModel.getReport(args.reportId)
        viewModel.callResult.observe(viewLifecycleOwner, Observer {

            when (it) {
                is ReportDetailsModel -> {
                    setFields(it, view)
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

    private fun setFields(
        it: ReportDetailsModel,
        view: View
    ) {
        type.text = it.reportType

        Glide.with(view)
            .load(it.photo)
            .into(reportPhoto)
        isActive.text = if (it.isActive) "Aktywne " else " Nie aktwyne "
        dateReport.text=it.reportDate
        if(it.video!=null)
            video.setVideoURI(Uri.parse(it.video))
        else
            video.visibility = View.GONE
        descrptionReport.text=it.description
    }


}

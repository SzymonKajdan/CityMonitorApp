package com.inz.citymonitor.presentation.pages.history.historyDetails

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.MediaController
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.afollestad.materialdialogs.MaterialDialog
import com.bumptech.glide.Glide
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapsInitializer
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.inz.citymonitor.R
import com.inz.citymonitor.data.model.ErrorResponseModel
import com.inz.citymonitor.data.model.History.ReportDetailsModel
import com.inz.citymonitor.presentation.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_history_details.*


class HistoryDetailsFragment : BaseFragment(), OnMapReadyCallback {
    override fun onMapReady(mMap: GoogleMap?) {
        mMap?.uiSettings?.isMyLocationButtonEnabled = false
        mMap?.uiSettings?.isCompassEnabled = false
        mMap?.uiSettings?.isMapToolbarEnabled = false

    }


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


        MapsInitializer.initialize(context)
        map.onCreate(savedInstanceState)
        map.onResume()
        map.getMapAsync(this)

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
        dateReport.text = it.reportDate
        if (it.video != null) {
            video.setVideoURI(Uri.parse(it.video))
            var mediaController: MediaController = MediaController(context);
            mediaController.setAnchorView(this.view)
            mediaController.setMediaPlayer(video)
            video.setMediaController(mediaController)
        }
        else {
            map.visibility = View.VISIBLE
            video.visibility = View.GONE
            setMarker(it)
        }
        descrptionReport.text = it.description
    }

    private fun setMarker(it: ReportDetailsModel) {

        map.getMapAsync { m ->
            var latLng = LatLng(it.latitude.toDouble(), it.longitude.toDouble())
            m.addMarker(
                MarkerOptions().position(
                    latLng
                )
            )
            m.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10f))
        }
    }


}

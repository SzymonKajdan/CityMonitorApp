package com.inz.citymonitor.presentation.pages.report.reportCreator

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.afollestad.materialdialogs.MaterialDialog
import com.bumptech.glide.Glide
import com.google.android.gms.location.FusedLocationProviderClient
import com.inz.citymonitor.R
import com.inz.citymonitor.data.model.ErrorResponseModel
import com.inz.citymonitor.data.model.ReportsType
import com.inz.citymonitor.presentation.base.BaseFragment


import kotlinx.android.synthetic.main.fragment_report_creator.*
import kotlinx.android.synthetic.main.fragment_report_creator.descrptionReport
import kotlinx.android.synthetic.main.fragment_report_creator.reportPhoto
import kotlin.collections.ArrayList


class ReportCreatorFragment : BaseFragment() {
    override fun setTopBarTitle() = "Nowe zgloszenie "

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private var photoUrl: String? =null

    val viewModel by lazy { ReportCreatorViewModel() }

    companion object {
        const val CAMERA_REQUEST = 168
        const val PHOTO = 190
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(

            com.inz.citymonitor.R.layout.fragment_report_creator,
            container,
            false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setSpinner();


        reportPhoto.setOnClickListener {
            if (checkPermission(Manifest.permission.CAMERA)) {
                var intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                startActivityForResult(intent, 190)
            } else {
                sendRequest(Manifest.permission.CAMERA)
                if (checkPermission(Manifest.permission.CAMERA)) {

                } else {
                    Toast.makeText(context, "Brak uprawanien", Toast.LENGTH_SHORT).show()
                }
            }

        }

        addReportButton.setOnClickListener {
            if (descrptionReport.text.isNullOrEmpty()) {
                Toast.makeText(context, "Nie uzupełniłes opisu ", Toast.LENGTH_SHORT).show()
            } else {

                val permission = "android.permission.ACCESS_FINE_LOCATION";
                if (context?.checkCallingOrSelfPermission(permission)!!.equals(PackageManager.PERMISSION_GRANTED)) {

                    if(photoUrl!=null)
                        viewModel.addPhoto(photoUrl)

                    var type = getReportType(reportTypeSpinner.selectedItem.toString())
                    viewModel.reportAdd(
                        arguments?.getString("long"),
                        arguments?.getString("lat"),
                        descrptionReport.text.toString(),
                        type,
                        photoUrl
                    )
                } else {
                    Toast.makeText(
                        context,
                        "nie mozna dodac zgłosznia jesli nei zezwolono na loklizacje",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            viewModel.callResult.observe(viewLifecycleOwner, Observer {
                when (it) {
                    is String -> {
                        Toast.makeText(
                            context,
                            "nieoczkeiwnay bład spróboj pozniej ",
                            Toast.LENGTH_SHORT
                        ).show()
                        findNavController().navigate(R.id.mapFragment)
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
                                    descrptionReport.text.clear()
                                    dismiss()
                                }
                            }
                        }
                    }
                    else -> {
                        findNavController().navigate(R.id.mapFragment)
                        Toast.makeText(context, "dodano pomśylnie ", Toast.LENGTH_SHORT).show()
                    }

                }
            })
        }
    }


    private fun checkPermission(permission: String): Boolean {
        return (ContextCompat.checkSelfPermission(
            activity?.applicationContext ?: return false,
            permission
        )
                == PackageManager.PERMISSION_GRANTED)
    }


    private fun getReportType(typeReport: String): String {

        var type: String = ""
        var types: ArrayList<String>
        ReportsType.values().forEach {

            if (it.type == typeReport) {
                type = it.name
            }
        }
        return type
    }

    private fun setSpinner() {
//        ArrayAdapter.createFromResource(
//            context,
//            ReportsType.values().toString(),
//            android.R.layout.simple_spinner_item
//        ).also { adapter ->
//            // Specify the layout to use when the list of choices appears
//            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
//            // Apply the adapter to the spinner
//            reportTypeSpinner.adapter = adapter
//        }
        var types = mutableListOf<String>()
        ReportsType.values().forEach {
            types.add(it.type)
            reportTypeSpinner.adapter = ArrayAdapter<String>(
                context,
                android.R.layout.simple_spinner_item,
                types
            )
        }
    }

    fun sendRequest(permission: String) {
        activity?.let {
            if (ContextCompat.checkSelfPermission(
                    it.applicationContext,
                    permission
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(it, permission)) {
                    ActivityCompat.requestPermissions(
                        it,
                        arrayOf(permission),
                        CAMERA_REQUEST
                    )
                } else {
                    ActivityCompat.requestPermissions(
                        it,
                        arrayOf(permission),
                        CAMERA_REQUEST
                    )
                }
            }
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == PHOTO) {
            photoUrl=viewModel.getPhoto(data,context)
        }
    }

}

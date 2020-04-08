package com.inz.citymonitor.presentation.pages.report.reportCreator


import android.Manifest
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.loader.content.CursorLoader
import androidx.navigation.fragment.findNavController
import com.afollestad.materialdialogs.MaterialDialog
import com.google.android.gms.location.FusedLocationProviderClient
import com.inz.citymonitor.data.model.ErrorResponseModel
import com.inz.citymonitor.data.model.ReportsType
import com.inz.citymonitor.presentation.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_report_creator.*


class ReportCreatorFragment : BaseFragment() {
    override fun setTopBarTitle() = "Nowe zgloszenie "

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private var photoUrl: String? = null

    val viewModel by lazy { ReportCreatorViewModel() }

    companion object {
        const val CAMERA_REQUEST = 168
        const val PHOTO = 190
        const val VIDEO = 191
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
        setSpinner()



        addPhoto()
        addReport()
        addVideo()
        addCall()

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
                is Boolean -> {
                    addReportButton.isEnabled = true
                }

                else -> {
                    findNavController().navigate(com.inz.citymonitor.R.id.mapFragment)
                    Toast.makeText(context, "dodano pomśylnie ", Toast.LENGTH_SHORT).show()
                }

            }
        })
    }

    private fun addCall() {

        call.setOnClickListener {
            if (checkPermission(Manifest.permission.CALL_PHONE)) {
                openCall()
            } else {
                sendRequest(Manifest.permission.CALL_PHONE)
                if (checkPermission(Manifest.permission.CALL_PHONE)) {
                    openCall()
                } else {
                    Toast.makeText(context, "Brak uprawanien", Toast.LENGTH_SHORT).show()
                }
            }

        }
    }

    private fun openCall() {
        var callingAcitivity= Intent(Intent.ACTION_CALL)
        callingAcitivity.data=Uri.parse("tel:112")
        startActivity(callingAcitivity)
    }

    private fun addPhoto() {
        reportPhoto.setOnClickListener {
            if (checkPermission(Manifest.permission.CAMERA)) {
                photoCaputre()
            } else {
                sendRequest(Manifest.permission.CAMERA)
                if (checkPermission(Manifest.permission.CAMERA)) {
                    photoCaputre()
                } else {
                    Toast.makeText(context, "Brak uprawanien", Toast.LENGTH_SHORT).show()
                }
            }

        }
    }

    private fun addVideo() {

        reportVideo.setOnClickListener {
            if (checkPermission(Manifest.permission.READ_EXTERNAL_STORAGE)) {
                if (checkPermission(Manifest.permission.CAMERA)) {
                    videoRecord()
                } else {
                    sendRequest(Manifest.permission.CAMERA)
                    if (checkPermission(Manifest.permission.CAMERA)) {
                        videoRecord()
                    } else {
                        Toast.makeText(context, "Brak uprawanien", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                sendRequest(Manifest.permission.READ_EXTERNAL_STORAGE)
            }
        }
    }

    private fun photoCaputre() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, PHOTO)
    }

    private fun videoRecord() {
        val intent = Intent(MediaStore.ACTION_VIDEO_CAPTURE)
        startActivityForResult(intent, VIDEO)
    }

    private fun addReport() {
        addReportButton.setOnClickListener {
            if (descrptionReport.text.isNullOrEmpty()) {
                Toast.makeText(context, "Nie uzupełniłes opisu ", Toast.LENGTH_SHORT).show()
            } else {

                val permission = "android.permission.ACCESS_FINE_LOCATION";
                if (context?.checkCallingOrSelfPermission(permission)!! == PackageManager.PERMISSION_GRANTED) {


                    var type = getReportType(reportTypeSpinner.selectedItem.toString())
                    viewModel.reportAdd(
                        arguments?.getString("long"),
                        arguments?.getString("lat"),
                        descrptionReport.text.toString(),
                        type
                    )
                } else {
                    Toast.makeText(
                        context,
                        "nie mozna dodac zgłosznia jesli nei zezwolono na loklizacje",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
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
        if (requestCode == PHOTO && resultCode == RESULT_OK) {
            photoUrl = viewModel.getPhoto(data, context)
            viewModel.addPhoto(photoUrl)
            Toast.makeText(context, "Zapisywanei zdjecia", Toast.LENGTH_SHORT).show()
            addReportButton.isEnabled = false
        }
        if (requestCode == VIDEO && resultCode == RESULT_OK) {
            val videoUri: Uri? = data?.data

            viewModel.addVideo(viewModel.path(videoUri, context))
            Toast.makeText(context, "Zapisywanei zdjecia", Toast.LENGTH_SHORT).show()
            addReportButton.isEnabled = false
        }
    }


}

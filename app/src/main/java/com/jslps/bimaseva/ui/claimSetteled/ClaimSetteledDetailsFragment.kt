package com.jslps.bimaseva.ui.claimSetteled

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.TextUtils
import android.util.Base64
import android.util.Log
import android.view.*
import android.widget.*
import androidx.annotation.NonNull
import androidx.core.app.ActivityCompat
import androidx.fragment.app.FragmentActivity
import com.jslps.bimaseva.Constant
import com.jslps.bimaseva.DialogUtil
import com.jslps.bimaseva.R
import com.jslps.bimaseva.activity.MainActivity
import com.jslps.bimaseva.cache.AppCache
import com.jslps.bimaseva.listener.OnFragmentListItemSelectListener
import com.jslps.bimaseva.model.HeaderData
import com.jslps.bimaseva.model.Master
import com.jslps.bimaseva.ui.BaseFragment
import java.io.*

class ClaimSetteledDetailsFragment : BaseFragment(), ClaimSetteledDetailsView,
    OnFragmentListItemSelectListener {
    override fun showMessage(message: Any?) {
        val toast = Toast.makeText(context, message.toString(), Toast.LENGTH_SHORT)
        toast.show()
        if (message != null) {
            if (message.equals("Insurance Update Successfully")) {
                val intent = Intent(activity, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                startActivity(intent)
            }
        }
    }

    override fun gotoScreen(fragmentID: Int, message: Any?) {

    }

    override fun loadData(cardInitResponse: ArrayList<Master>?) {

    }

    override fun showProgress() {
        DialogUtil.displayProgress(activity!!)
    }

    override fun hideProgress() {
        DialogUtil.stopProgressDisplay()
    }

    override fun noInternet() {
        val toast = Toast.makeText(context, Constant.NO_INTERNET, Toast.LENGTH_SHORT)
        toast.show()
    }

    override fun onListItemSelected(itemId: Int, data: Any) {
    }

    override fun onListItemLongClicked(itemId: Int, data: Any) {
    }

    var document: ImageView? = null
    var encodedBase64: String? = null
    var REQUEST_CAMERA = 0
    var SELECT_FILE = 1
    var presenter: ClaimSetteledDetailsPresenter? = null
    private var rootView: View? = null
    override fun onResume() {
        super.onResume()
        mListener!!.onFragmentUpdate(Constant.setTitle, HeaderData(false, "Claim Settled "))
    }

    private fun onSelectFromGalleryResult(data: Intent) {
        val selectedImageUri = data.getData()
        val projection = arrayOf<String>(MediaStore.MediaColumns.DATA)
        val cursor = activity?.managedQuery(selectedImageUri, projection, null, null, null)
        val column_index = cursor?.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA)
        cursor?.moveToFirst()
        val selectedImagePath = column_index?.let { cursor.getString(it) }
        val bm: Bitmap
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeFile(selectedImagePath, options)
        val REQUIRED_SIZE = 200
        var scale = 1
        while ((options.outWidth / scale / 2 >= REQUIRED_SIZE && options.outHeight / scale / 2 >= REQUIRED_SIZE))
            scale *= 2
        options.inSampleSize = scale
        options.inJustDecodeBounds = false
        bm = BitmapFactory.decodeFile(selectedImagePath, options)
        document?.setImageBitmap(bm)
        val imgFile = File(selectedImagePath)
        if (imgFile.exists()) {
            val fileInputStreamReader: FileInputStream
            try {
                fileInputStreamReader = FileInputStream(imgFile)
                val fileSizeInBytes = fileInputStreamReader.available()
                val bytes = ByteArray(imgFile.length().toInt())
                fileInputStreamReader.read(bytes)
                if (fileSizeInBytes < 5000000) {
                    encodedBase64 = Base64.encodeToString(bytes, Base64.DEFAULT)
                } else {
                    encodedBase64 = null
                    Toast.makeText(
                        context as Activity,
                        "Your pic size more than 5 mb",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            } catch (e: Error) {

            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, @NonNull permissions: Array<String>, @NonNull grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            0 -> {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    attchmemntPopup(activity as Activity)
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return
            }
        }// other 'case' lines to check for other
        // permissions this app might request
    }

    var documentPreviousAttached: ImageView? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        rootView = inflater.inflate(R.layout.claim_details, container, false)
        val nameOfInsurance: TextView? = rootView?.findViewById(R.id.insuranceName)
        val textHeading: TextView? = rootView?.findViewById(R.id.textHeading)
        val nomineeName: TextView? = rootView?.findViewById(R.id.nomineeInsurance)
        val contactNo: TextView? = rootView?.findViewById(R.id.contactNo)
        val block: TextView? = rootView?.findViewById(R.id.block)
        val village: TextView? = rootView?.findViewById(R.id.village)
        val claimReject: Button? = rootView?.findViewById(R.id.claimReject)
        val claimSetteled: Button? = rootView?.findViewById(R.id.claimSetteled)
        val buttonlayout: LinearLayout? = rootView?.findViewById(R.id.buttonlayout)
        val imageLayoutPrevious: LinearLayout? = rootView?.findViewById(R.id.imageLayoutPrevious)
        val imageLayout: LinearLayout? = rootView?.findViewById(R.id.imageLayout)
        documentPreviousAttached = rootView?.findViewById(R.id.doucment_previous_registerd)
       val doucment_previous_underprocess = rootView?.findViewById<ImageView>(R.id.doucment_previous_underprocess)
        val rejectReason: EditText? = rootView?.findViewById(R.id.rejectReason)
        val amount: EditText? = rootView?.findViewById(R.id.amount)
        document = rootView?.findViewById(R.id.doucment)
        var actionButton: Button? = rootView?.findViewById(R.id.actionButton)
        val uploadDocument: Button? = rootView?.findViewById(R.id.uploadDocument)
        val previousHeading: TextView? = rootView?.findViewById(R.id.previousHeading)
        val bankbranch: TextView? = rootView?.findViewById(R.id.bankbranch)
        nomineeName?.text = insuranceNameeee?.name
        block?.text = insuranceNameeee?.blockname
        uploadDocument?.setText("Upload Receipt")
        buttonlayout?.visibility = View.VISIBLE
        amount?.visibility = View.VISIBLE
        imageLayout?.visibility = View.VISIBLE
        rejectReason?.visibility = View.GONE
        actionButton?.visibility = View.GONE
        textHeading?.text = "Service Charged recived"
        // block?.startAnimation(AnimationUtils.loadAnimation(activity, R.anim.move));
        village?.text = insuranceNameeee?.villagename
        contactNo?.text = insuranceNameeee?.phno_ofNominee.toString()
        bankbranch?.text = insuranceNameeee?.branchName
        nameOfInsurance?.text = AppCache.getCache().insurancetype
        if (!TextUtils.isEmpty(insuranceNameeee?.imagebyte)||!TextUtils.isEmpty(insuranceNameeee?.image_UP)) {
            previousHeading?.visibility = View.VISIBLE
            imageLayoutPrevious?.visibility = View.VISIBLE
            try {
                val byteArray: ByteArray =
                    Base64.decode(insuranceNameeee?.imagebyte, 0)
                val bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
                documentPreviousAttached?.setImageBitmap(bitmap)
                val byteArray1: ByteArray =
                    Base64.decode(insuranceNameeee?.image_UP, 0)
                val bitmap1 = BitmapFactory.decodeByteArray(byteArray1, 0, byteArray.size)
                doucment_previous_underprocess?.setImageBitmap(bitmap1)
            } catch (e: Exception) {
                e.printStackTrace()
            } catch (e: java.lang.Error) {
            }
        } else {
            previousHeading?.visibility = View.GONE
            imageLayoutPrevious?.visibility = View.GONE
        }
        presenter = ClaimSetteledDetailsPresenter(this, activity as Activity)
        actionButton?.setOnClickListener {
            if (TextUtils.isEmpty(rejectReason?.text.toString())) {
                val toast =
                    Toast.makeText(activity, "Please Write Reject Reason", Toast.LENGTH_SHORT)
                toast.show()
            } else {
                showProgress()
                presenter?.uploadClaimejected(insuranceNameeee, rejectReason?.text.toString())
            }
        }
        claimSetteled?.setOnClickListener {

            if (TextUtils.isEmpty(encodedBase64)) {
                val toast = Toast.makeText(activity, "Please Upload Document", Toast.LENGTH_SHORT)
                toast.show()
            } else if (TextUtils.isEmpty(amount?.text.toString())) {
                val toast = Toast.makeText(activity, "Please Enter Amount", Toast.LENGTH_SHORT)
                toast.show()
            } else {
                showProgress()
                presenter?.uploadClaimSetteled(
                    insuranceNameeee,
                    encodedBase64,
                    amount?.text.toString()
                )
            }
        }
        claimReject?.setOnClickListener {
            rejectReason?.visibility = View.VISIBLE
            imageLayout?.visibility = View.GONE
            buttonlayout?.visibility = View.GONE
            amount?.visibility = View.GONE
            actionButton?.visibility = View.VISIBLE
            textHeading?.text = "Write Reject Reason for Rejection"

        }
        uploadDocument?.setOnClickListener {
            if (ActivityCompat.checkSelfPermission(
                    context as Activity,
                    Manifest.permission.CAMERA
                ) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(
                    context as Activity,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(
                    context as Activity,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    context as Activity,
                    arrayOf(
                        Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE
                    ),
                    0
                )
                if (ActivityCompat.checkSelfPermission(
                        context as Activity,
                        Manifest.permission.CAMERA
                    ) != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(
                        context as Activity,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                    ) != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(
                        context as Activity,
                        Manifest.permission.READ_EXTERNAL_STORAGE
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    val toast = Toast.makeText(
                        context as Activity,
                        "Permission not given",
                        Toast.LENGTH_SHORT
                    )
                    toast.show()
                } else {
                    attchmemntPopup(context as Activity)
                }
            } else {
                attchmemntPopup(context as Activity)
            }
        }
        return rootView!!
    }

    private fun attchmemntPopup(context: Activity) {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val layout = inflater.inflate(R.layout.attachment_popup, null)
        val alertD = android.app.AlertDialog.Builder(context).create()
        alertD.setCancelable(true)
        //Making alert as bottom
        val window = alertD.window
        val wlp = window!!.attributes

        wlp.gravity = Gravity.CENTER
        wlp.flags = wlp.flags and WindowManager.LayoutParams.FLAG_DIM_BEHIND.inv()
        window.attributes = wlp
        val camera = layout.findViewById(R.id.camera) as LinearLayout
        val gallery = layout.findViewById(R.id.gallery) as LinearLayout

        camera.setOnClickListener {
            alertD.dismiss()
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(intent, REQUEST_CAMERA)
        }

        gallery.setOnClickListener {
            alertD.dismiss()
            val intent = Intent(
                Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            )
            intent.setType("image/*")
            startActivityForResult(
                Intent.createChooser(intent, "Select File"),
                SELECT_FILE
            )
        }
        alertD.setView(layout)
        alertD.show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE) {
                if (data != null) {
                    //onSelectFromGalleryResult(data)
                    val imageUri = data.data as Uri
                    val imageStream =
                        activity?.contentResolver?.openInputStream(imageUri) as InputStream
                    val selectedImage = BitmapFactory.decodeStream(imageStream) as Bitmap
                    document?.setImageBitmap(selectedImage)
                    val byteArrayOutputStream = ByteArrayOutputStream()
                    selectedImage.compress(Bitmap.CompressFormat.JPEG, 15, byteArrayOutputStream)
                    val imagedata = byteArrayOutputStream.toByteArray()
                    encodedBase64 = Base64.encodeToString(imagedata, Base64.DEFAULT)
                    Log.d("mdfmwrgsdig", "dfhgjsg" + encodedBase64)
                }
            } else if (requestCode == REQUEST_CAMERA) {
                try {
                    if (data != null) {
                        //onCaptureImageResult(data)
                        val photo = data.extras!!.get("data") as Bitmap
                        document?.setImageBitmap(photo)
                        val byteArrayOutputStream = ByteArrayOutputStream()
                        photo.compress(Bitmap.CompressFormat.JPEG, 15, byteArrayOutputStream)
                        val imagedata = byteArrayOutputStream.toByteArray()
                        encodedBase64 = Base64.encodeToString(imagedata, Base64.DEFAULT)
                        Log.d("mdfmwrgsdig", "dfhgjsg" + encodedBase64)
                    }
                } catch (e: IOException) {
                    e.printStackTrace()
                }

            }
        }
    }

    @Throws(IOException::class)
    private fun onCaptureImageResult(data: Intent) {
        val photo = data.getExtras()?.get("data") as Bitmap
        document?.setImageBitmap(photo)
        val tempUri = getImageUri(activity as FragmentActivity, photo)
        // CALL THIS METHOD TO GET THE ACTUAL PATH
        val finalFile = File(getRealPathFromURI(tempUri))
        Log.i("gbhvhjcbvhjcvbv:", finalFile.toString())
        if (finalFile.exists()) {
            val fileInputStreamReader: FileInputStream
            try {
                fileInputStreamReader = FileInputStream(finalFile)
                val bytes = ByteArray(finalFile.length() as Int)
                fileInputStreamReader.read(bytes)
                encodedBase64 = Base64.encodeToString(bytes, Base64.DEFAULT)
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            } catch (e: Error) {

            }
        }
    }

    fun getImageUri(inContext: Context, inImage: Bitmap): Uri {
        val bytes = ByteArrayOutputStream()
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path =
            MediaStore.Images.Media.insertImage(inContext.contentResolver, inImage, "Title", null)
        return Uri.parse(path)
    }

    fun getRealPathFromURI(uri: Uri): String? {
        val cursor = activity?.getContentResolver()?.query(uri, null, null, null, null)
        cursor?.moveToFirst()
        val idx = cursor?.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
        return idx?.let { cursor.getString(it) }
    }

    companion object {
        var insuranceNameeee: Master? = null
        fun getInstance(insuranceNamee: Master): ClaimSetteledDetailsFragment {
            insuranceNameeee = insuranceNamee
            return ClaimSetteledDetailsFragment()
        }
    }
}


package com.jslps.bimaseva.ui.claimSetteled

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
import androidx.fragment.app.FragmentActivity
import com.jslps.bimaseva.Constant
import com.jslps.bimaseva.DialogUtil
import com.jslps.bimaseva.R
import com.jslps.bimaseva.cache.AppCache
import com.jslps.bimaseva.listener.OnFragmentListItemSelectListener
import com.jslps.bimaseva.model.HeaderData
import com.jslps.bimaseva.model.Master
import com.jslps.bimaseva.ui.BaseFragment
import com.jslps.bimaseva.ui.underProcess.UnderProcessDetailsView
import com.irozon.sneaker.Sneaker
import java.io.*

class RejectDetailsFragment : BaseFragment(), UnderProcessDetailsView, OnFragmentListItemSelectListener {
    override fun showMessage(message: Any?) {
        /*Sneaker.with(activity!!) // Activity, Fragment or ViewGroup
            .setTitle(message.toString())
            .sneakSuccess()*/
        val toast = Toast.makeText(context, message.toString(), Toast.LENGTH_SHORT)
        toast.show()
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
    var presenter : ClaimSetteledDetailsPresenter? = null
    private var rootView: View? = null
    override fun onResume() {
        super.onResume()
        mListener!!.onFragmentUpdate(Constant.setTitle, HeaderData(false, AppCache.getCache().insurancetype.toString()))
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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        rootView = inflater.inflate(R.layout.reject_details, container, false)
        val nameOfInsurance: TextView? = rootView?.findViewById(R.id.insuranceName)
        val nomineeName: TextView? = rootView?.findViewById(R.id.nomineeInsurance)
        val contactNo: TextView? = rootView?.findViewById(R.id.contactNo)
        val block: TextView? = rootView?.findViewById(R.id.block)
        val village: TextView? = rootView?.findViewById(R.id.village)
        val rejectReason: EditText? = rootView?.findViewById(R.id.rejectReason)
        document = rootView?.findViewById(R.id.doucment)
        val actionButton: Button? = rootView?.findViewById(R.id.actionButton)
        val bankbranch: TextView? = rootView?.findViewById(R.id.bankbranch)
        nomineeName?.text = insuranceNameeee?.name
        block?.text = insuranceNameeee?.blockname
        village?.text = insuranceNameeee?.villagename
        contactNo?.text = insuranceNameeee?.phno_ofNominee.toString()
        bankbranch?.text = insuranceNameeee?.branchName
        nameOfInsurance?.text = AppCache.getCache().insurancetype
        actionButton?.setOnClickListener {
            if (TextUtils.isEmpty(encodedBase64)) {
                val toast = Toast.makeText(activity, "Please Write Reject Reason", Toast.LENGTH_SHORT)
                toast.show()
            } else {
                showProgress()
                presenter?.uploadClaimejected(ClaimSetteledDetailsFragment.insuranceNameeee, rejectReason?.text.toString())
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
                Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
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
                    val imageStream = activity?.contentResolver?.openInputStream(imageUri) as InputStream
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
        val path = MediaStore.Images.Media.insertImage(inContext.contentResolver, inImage, "Title", null)
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
        fun getInstance(insuranceNamee: Master): RejectDetailsFragment {
            insuranceNameeee = insuranceNamee
            return RejectDetailsFragment()
        }
    }
}


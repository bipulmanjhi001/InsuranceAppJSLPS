package com.jslps.bimaseva.activity

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import android.widget.TextView

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.FragmentManager
import com.jslps.bimaseva.R
import com.jslps.bimaseva.listener.OnFragmentInteractionListener
import com.jslps.bimaseva.ui.home.HomeFragment
import com.jslps.bimaseva.ui.scheme.SchemeDetailsFragment
import com.jslps.bimaseva.Constant
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.nio.channels.FileChannel
import com.jslps.bimaseva.model.HeaderData
import com.jslps.bimaseva.model.Master
import com.jslps.bimaseva.network.InsuranceCreate
import com.jslps.bimaseva.ui.NewInsuranceForm
import com.jslps.bimaseva.ui.claimSetteled.ClaimSetteledDetailsFragment
import com.jslps.bimaseva.ui.documentFale.DocumentFalseListFragment
import com.jslps.bimaseva.ui.documentNotReady.DocumentListFragment
import com.jslps.bimaseva.ui.documentNotReady.DocumentNotReadyDetailsFragment
import com.jslps.bimaseva.ui.insuranceList.InsuranceDetailsFragment
import com.jslps.bimaseva.ui.insuranceList.InsuranceListFragment
import com.jslps.bimaseva.ui.registration.ClaimRegistrationFragmentFamily
import com.jslps.bimaseva.ui.registration.ClaimRegistrationFragmentOthers
import com.jslps.bimaseva.ui.registration.ClaimRegistrationFragmentSHGMember
import com.jslps.bimaseva.ui.registration.RegistrationList
import com.jslps.bimaseva.ui.reports.ReportListFragment
import com.jslps.bimaseva.ui.reports.SchemeDetailsFragmentReport
import com.jslps.bimaseva.ui.underProcess.UnderProcessDetailsFragment


class MainActivity : AppCompatActivity(), OnFragmentInteractionListener {

    private var mFragmentManager: FragmentManager? = null
    private var mFragmentTag: String? = null
    private var mCurrentFragment: Int = 0
    internal var toolbar_home: Toolbar? = null
    internal var toolbar_title: TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        toolbar_home = findViewById(R.id.toolbar_home)
        toolbar_title = findViewById(R.id.toolbar_title)
        onFragmentInteraction(Constant.HOME_FRAGMENT, "")
    }

    override fun onFragmentInteraction(fragmentId: Int, data: Any) {
        mFragmentManager = supportFragmentManager
        mCurrentFragment = fragmentId
        mFragmentTag = fragmentId.toString()
        when (fragmentId) {
            Constant.HOME_FRAGMENT -> {
                mFragmentManager?.beginTransaction()!!.setCustomAnimations(
                    android.R.anim.slide_in_left,
                    android.R.anim.slide_out_right
                )
                    .addToBackStack(mFragmentTag)
                    .replace(R.id.fragment_main, HomeFragment(), mFragmentTag)
                    .commitAllowingStateLoss()
            }
            Constant.SCHEME_DETAILS_FRAGMENT -> {
                mFragmentManager?.beginTransaction()!!.setCustomAnimations(
                    android.R.anim.slide_in_left,
                    android.R.anim.slide_out_right
                )
                    .addToBackStack(mFragmentTag)
                    .replace(
                        R.id.fragment_main,
                        SchemeDetailsFragment.getInstance(data as String),
                        mFragmentTag
                    )
                    .commitAllowingStateLoss()
            }
            Constant.INSURANCE_LIST_FRAGMENT -> {
                mFragmentManager?.beginTransaction()!!.setCustomAnimations(
                    android.R.anim.slide_in_left,
                    android.R.anim.slide_out_right
                )
                    .addToBackStack(mFragmentTag)
                    .replace(
                        R.id.fragment_main,
                        InsuranceListFragment.getInstance(data as List<Master>),
                        mFragmentTag
                    )
                    .commitAllowingStateLoss()
            }
            Constant.DOCUMENT_LIST_FRAGMENT -> {
                mFragmentManager?.beginTransaction()!!.setCustomAnimations(
                    android.R.anim.slide_in_left,
                    android.R.anim.slide_out_right
                )
                    .addToBackStack(mFragmentTag)
                    .replace(
                        R.id.fragment_main,
                        DocumentListFragment.getInstance(data as List<Master>),
                        mFragmentTag
                    )
                    .commitAllowingStateLoss()
            }
            Constant.DOCUMENT_FALSE_LIST_FRAGMENT -> {
                mFragmentManager?.beginTransaction()!!.setCustomAnimations(
                    android.R.anim.slide_in_left,
                    android.R.anim.slide_out_right
                )
                    .addToBackStack(mFragmentTag)
                    .replace(
                        R.id.fragment_main,
                        DocumentFalseListFragment.getInstance(data as List<Master>),
                        mFragmentTag
                    )
                    .commitAllowingStateLoss()
            }
            Constant.INSURANCE_DETAILS_FRAGMENT -> {
                mFragmentManager?.beginTransaction()!!.setCustomAnimations(
                    android.R.anim.slide_in_left,
                    android.R.anim.slide_out_right
                )
                    .addToBackStack(mFragmentTag)
                    .replace(
                        R.id.fragment_main,
                        InsuranceDetailsFragment.getInstance(data as Master),
                        mFragmentTag
                    )
                    .commitAllowingStateLoss()
            }
            Constant.UNDER_PROCESS_DETAILS_FRAGMENT -> {
                mFragmentManager?.beginTransaction()!!.setCustomAnimations(
                    android.R.anim.slide_in_left,
                    android.R.anim.slide_out_right
                )
                    .addToBackStack(mFragmentTag)
                    .replace(
                        R.id.fragment_main,
                        UnderProcessDetailsFragment.getInstance(data as Master),
                        mFragmentTag
                    )
                    .commitAllowingStateLoss()
            }
            Constant.CLAIM_SETTELED_DETAILS_FRAGMENT -> {
                mFragmentManager?.beginTransaction()!!.setCustomAnimations(
                    android.R.anim.slide_in_left,
                    android.R.anim.slide_out_right
                )
                    .addToBackStack(mFragmentTag)
                    .replace(
                        R.id.fragment_main,
                        ClaimSetteledDetailsFragment.getInstance(data as Master),
                        mFragmentTag
                    )
                    .commitAllowingStateLoss()
            }
            Constant.REPORTS_DETAILS_FRAGMENT -> {
                mFragmentManager?.beginTransaction()!!.setCustomAnimations(
                    android.R.anim.slide_in_left,
                    android.R.anim.slide_out_right
                )
                    .addToBackStack(mFragmentTag)
                    .replace(
                        R.id.fragment_main,
                        SchemeDetailsFragmentReport.getInstance(),
                        mFragmentTag
                    )
                    .commitAllowingStateLoss()
            }
            Constant.ENTRY_FORM_INSURANCE -> {
                mFragmentManager?.beginTransaction()!!.setCustomAnimations(
                    android.R.anim.slide_in_left,
                    android.R.anim.slide_out_right
                )
                    .addToBackStack(mFragmentTag)
                    .replace(R.id.fragment_main, NewInsuranceForm.getInstance(), mFragmentTag)
                    .commitAllowingStateLoss()
            }
            Constant.Document_DETAILS_FRAGMRNT -> {
                mFragmentManager?.beginTransaction()!!.setCustomAnimations(
                    android.R.anim.slide_in_left,
                    android.R.anim.slide_out_right)
                    .addToBackStack(mFragmentTag)
                    .replace(R.id.fragment_main, DocumentNotReadyDetailsFragment.getInstance(data as Master), mFragmentTag)
                    .commitAllowingStateLoss()
            }
            Constant.INSURANCE_CREATE_INSIDE_OTHER -> {
                mFragmentManager?.beginTransaction()!!.setCustomAnimations(
                    android.R.anim.slide_in_left,
                    android.R.anim.slide_out_right)
                    .addToBackStack(mFragmentTag)
                    .replace(R.id.fragment_main, ClaimRegistrationFragmentOthers(), mFragmentTag)
                    .commitAllowingStateLoss()
            }
            Constant.INSURANCE_CREATE_INSIDE_FAMILY -> {
                mFragmentManager?.beginTransaction()!!.setCustomAnimations(
                    android.R.anim.slide_in_left,
                    android.R.anim.slide_out_right)
                    .addToBackStack(mFragmentTag)
                    .replace(R.id.fragment_main, ClaimRegistrationFragmentFamily(), mFragmentTag)
                    .commitAllowingStateLoss()
            }
            Constant.INSURANCE_CREATE_INSIDE_SHG -> {
                mFragmentManager?.beginTransaction()!!.setCustomAnimations(
                    android.R.anim.slide_in_left,
                    android.R.anim.slide_out_right)
                    .addToBackStack(mFragmentTag)
                    .replace(R.id.fragment_main, ClaimRegistrationFragmentSHGMember(), mFragmentTag)
                    .commitAllowingStateLoss()
            }
            Constant.INSURANCE_CREATE_INSIDE-> {
                mFragmentManager?.beginTransaction()!!.setCustomAnimations(
                    android.R.anim.slide_in_left,
                    android.R.anim.slide_out_right)
                    .addToBackStack(mFragmentTag)
                    .replace(R.id.fragment_main, RegistrationList(), mFragmentTag)
                    .commitAllowingStateLoss()
            }
            Constant.REPORT_LIST_FRAGMENT-> {
                mFragmentManager?.beginTransaction()!!.setCustomAnimations(
                    android.R.anim.slide_in_left,
                    android.R.anim.slide_out_right)
                    .addToBackStack(mFragmentTag)
                    .replace(R.id.fragment_main, ReportListFragment.getInstance(data as String), mFragmentTag)
                    .commitAllowingStateLoss()
            }
        }
    }

    override fun onFragmentUpdate(type: Int, data: Any) {
        when (type) {
            Constant.setTitle -> {
                val headerData = data as HeaderData
                if (headerData.isLogoRequired)
                    toolbar_home?.visibility = View.GONE
                else
                    toolbar_home?.visibility = View.VISIBLE
                toolbar_title?.text = headerData.text
            }
        }
    }

    override fun onBackPressed() {
        //super.onBackPressed();
        val count = supportFragmentManager.backStackEntryCount
        if (count <= 1) {
            closeApp()
        } else
            super.onBackPressed()

    }

    private fun closeApp() {
        val alertDialogBuilder = AlertDialog.Builder(this, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT)
        alertDialogBuilder.setMessage(getString(R.string.exit_message))
        alertDialogBuilder.setPositiveButton(getString(R.string.yes)) { arg0, arg1 -> finish() }
        alertDialogBuilder.setNegativeButton(getString(R.string.no)) { dialog, which -> dialog.dismiss() }
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }

    companion object {

        @Throws(IOException::class)
        fun copyFile(sourceFile: File, destFile: File): Boolean {
            //        if (!destFile.exists()) {
            destFile.createNewFile()
            var source: FileChannel? = null
            var destination: FileChannel? = null
            try {
                source = FileInputStream(sourceFile).channel
                destination = FileOutputStream(destFile).channel
                destination!!.transferFrom(source, 0, source!!.size())
            } finally {
                source?.close()
                destination?.close()
            }
            return true
        }
    }


}

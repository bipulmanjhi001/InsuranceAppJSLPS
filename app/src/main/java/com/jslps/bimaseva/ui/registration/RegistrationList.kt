package com.jslps.bimaseva.ui.registration


import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.Spinner
import com.irozon.sneaker.Sneaker
import com.jslps.bimaseva.Constant
import com.jslps.bimaseva.R
import com.jslps.bimaseva.cache.AppCache
import com.jslps.bimaseva.model.HeaderData
import com.jslps.bimaseva.ui.BaseFragment

class RegistrationList : BaseFragment() {
    var linearLayout1: LinearLayout? = null
    var linearLayout2: LinearLayout? = null
    var linearLayout3: LinearLayout? = null
    var spinner: Spinner? = null
    val otherStrings = arrayOf("All Scheme", "PMSBY", "PMJJBY","PMJAY","Asset Insurance (Crop, Livestock, Others)")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.activity_claim_registration, container, false)
        linearLayout1 = rootView.findViewById(R.id.shgRegistration)
        linearLayout2 = rootView.findViewById(R.id.shgFamilyregistration)
        linearLayout3 = rootView.findViewById(R.id.registrationothers)
        spinner = rootView.findViewById(R.id.sppiner_scheme)
        val scheme = resources.getStringArray(R.array.schemeArray)

        val adapter = activity?.let {
            ArrayAdapter(
                it,
                R.layout.spiner_row, otherStrings
            )
        }
        spinner?.adapter = adapter
        linearLayout1?.setOnClickListener {
            if (!TextUtils.isEmpty(schemeId)) {
                AppCache.getCache().schemeID = schemeId
                mListener?.onFragmentInteraction(Constant.INSURANCE_CREATE_INSIDE_SHG, "")
            } /*else
                Sneaker.with(activity!!) // Activity, Fragment or ViewGroup
                    .setTitle("Please Select Any Scheme")
                    .sneakError()*/

        }
        linearLayout2?.setOnClickListener {
            if (!TextUtils.isEmpty(schemeId)) {
                AppCache.getCache().schemeID = schemeId
                mListener?.onFragmentInteraction(Constant.INSURANCE_CREATE_INSIDE_FAMILY, "")
            } /*else
                Sneaker.with(activity!!) // Activity, Fragment or ViewGroup
                    .setTitle("Please Select Any Scheme")
                    .sneakError()*/
        }
        linearLayout3?.setOnClickListener {
            if (!TextUtils.isEmpty(schemeId)) {
                AppCache.getCache().schemeID = schemeId
                mListener?.onFragmentInteraction(Constant.INSURANCE_CREATE_INSIDE_OTHER, "")
            } /*else
                Sneaker.with(activity!!) // Activity, Fragment or ViewGroup
                    .setTitle("Please Select Any Scheme")
                    .sneakError()*/
        }

       /* spinner?.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                selectedView: View, position: Int, id: Long) {
                when (position) {


                    1 -> {
                        schemeId = "1"

                    }
                    2 -> {
                        schemeId = "2"

                    }
                    3 -> {
                        schemeId = "3"


                    }
                    4 -> {
                        schemeId = "4"


                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // write code to perform some action
            }
        }*/
        spinner?.onItemSelectedListener=object :AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                when (position) {


                    1 -> {
                        schemeId = "1"

                    }
                    2 -> {
                        schemeId = "2"

                    }
                    3 -> {
                        schemeId = "3"


                    }
                    4 -> {
                        schemeId = "4"


                    }
                }
            }


        }


        return rootView
    }

    var schemeId: String? = null
    override fun onResume() {
        super.onResume()
        mListener!!.onFragmentUpdate(Constant.setTitle, HeaderData(false, "Registration Type"))

    }
}

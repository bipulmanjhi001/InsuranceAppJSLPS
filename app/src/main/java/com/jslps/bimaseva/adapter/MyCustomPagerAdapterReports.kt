package com.jslps.bimaseva.activity.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ScrollView
import android.widget.TextView
import androidx.viewpager.widget.PagerAdapter
import com.jslps.bimaseva.R
import com.jslps.bimaseva.assetsReg.AssetsRegistration
import com.jslps.bimaseva.model.welcomePageReports.Listdetails

class MyCustomPagerAdapterReports(internal var context: Context, internal var itemList: ArrayList<Listdetails>) : PagerAdapter() {
    internal var layoutInflater: LayoutInflater


    init {
        layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    }

    override fun getCount(): Int {
        return itemList.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object` as ScrollView
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val mView = layoutInflater.inflate(R.layout.welcome_adapter_row, container, false)

        val textrowheading: TextView = mView.findViewById(R.id.heading)
        val registerd_count: TextView = mView.findViewById(R.id.registerd_count)
        val underprocess_count: TextView = mView.findViewById(R.id.underprocess_count)
        val completed_count: TextView = mView.findViewById(R.id.completed_count)
        val rejected_count: TextView = mView.findViewById(R.id.rejected_count)
        val registerd: TextView = mView.findViewById(R.id.registerd)

        registerd.setOnClickListener(View.OnClickListener {
            val intent = Intent(context, AssetsRegistration::class.java)
            context.startActivity(intent);

        })
//        imageView.setImageResource(images[position])

        val baseClassReportsWelcomwPage = itemList.get(position)
        textrowheading.text = baseClassReportsWelcomwPage.insurance
        completed_count.text =
            baseClassReportsWelcomwPage.completed.toString()
        registerd_count.text =
            baseClassReportsWelcomwPage.register.toString()

            rejected_count.text =
            baseClassReportsWelcomwPage.reject.toString()
        underprocess_count.text =
            baseClassReportsWelcomwPage.underProcess.toString()

        container.addView(mView)

        //listening to image click
        return mView
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as ScrollView)
    }
}

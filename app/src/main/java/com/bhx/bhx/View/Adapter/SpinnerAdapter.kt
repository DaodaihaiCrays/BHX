package com.bhx.bhx.View.Adapter

import android.content.Context
import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class SpinnerAdapter(context: Context,resource: Int,  itemList: List<String>): ArrayAdapter<String>(context, resource, itemList) {

    override fun isEnabled(position: Int): Boolean {
        // Disable the first item from Spinner
        // First item will be used for hint
        return position != 0
    }

    override fun getDropDownView(
        position: Int,
        convertView: View?,
        parent: ViewGroup
    ): View {
        val view: TextView = super.getDropDownView(position, convertView, parent) as TextView
        //set the color of first item in the drop down list to gray
        if(position == 0) {
            view.setTextColor(Color.GRAY)
        } else {
            //here it is possible to define color for other items by
            //view.setTextColor(Color.RED)
        }
        return view
    }
}
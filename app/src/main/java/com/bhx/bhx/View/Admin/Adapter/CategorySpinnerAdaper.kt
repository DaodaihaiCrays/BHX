package com.bhx.bhx.View.Admin.Adapter

import android.content.Context
import android.widget.ArrayAdapter
import com.bhx.bhx.Model.Category
import com.bhx.bhx.R

class CategorySpinnerAdapter(context: Context, private val objects: List<Category>) :
    ArrayAdapter<Category>(context, R.layout.spinner_item, objects) {

    init {
        setDropDownViewResource(R.layout.spinner_item)
    }

    override fun getItem(position: Int): Category {
        return objects[position]
    }

    override fun getItemId(position: Int): Long {
        return objects[position].id!!.toLong()
    }

    override fun getCount(): Int {
        return objects.size
    }
}
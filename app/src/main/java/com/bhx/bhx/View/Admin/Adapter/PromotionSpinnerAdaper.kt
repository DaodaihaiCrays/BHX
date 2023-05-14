package com.bhx.bhx.View.Admin.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.bhx.bhx.Model.Category
import com.bhx.bhx.Model.Promotion
import com.bhx.bhx.R

class PromotionSpinnerAdaper(context: Context, private val objects: List<Promotion?>) :
    ArrayAdapter<Promotion>(context, R.layout.spinner_item, objects) {

    init {
        setDropDownViewResource(R.layout.spinner_item)
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.spinner_item, parent, false)
        val textView = view.findViewById<TextView>(R.id.tv_spinner_item)
        textView.text = objects[position]?.name ?: "Chọn mã giảm giá"
        return view
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(android.R.layout.simple_spinner_dropdown_item, parent, false)
        val textView = view.findViewById<TextView>(android.R.id.text1)
        textView.text = objects[position]?.name ?: "Chọn mã giảm giá"
        return view
    }

    override fun getItem(position: Int): Promotion? {
        return objects[position]
    }

    override fun getItemId(position: Int): Long {
        return objects[position]?.id?.toLong() ?: -1L
    }

    override fun getCount(): Int {
        return objects.size
    }
}
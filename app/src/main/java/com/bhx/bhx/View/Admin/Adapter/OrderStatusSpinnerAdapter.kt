package com.bhx.bhx.View.Admin.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.bhx.bhx.Model.Promotion
import com.bhx.bhx.R
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class OrderStatus (
    @SerializedName("id")
    @Expose()
    val id: Int,

    @SerializedName("status")
    @Expose()
    val status: String,
)

val orderStatuses = listOf<OrderStatus>(OrderStatus(1, "PREPARING"), OrderStatus(2, "DELIVERING"), OrderStatus(3, "DELIVERED"), OrderStatus(4, "CANCLED"))

class OrderStatusSpinnerAdapter(context: Context) :
    ArrayAdapter<OrderStatus>(context, R.layout.spinner_item, orderStatuses) {

        init {
            setDropDownViewResource(R.layout.spinner_item)
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.spinner_item, parent, false)
            val textView = view.findViewById<TextView>(R.id.tv_spinner_item)
            textView.text = orderStatuses[position]?.status ?: "Chọn mã giảm giá"
            return view
        }

        override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
            val view = convertView ?: LayoutInflater.from(context).inflate(android.R.layout.simple_spinner_dropdown_item, parent, false)
            val textView = view.findViewById<TextView>(android.R.id.text1)
            textView.text = orderStatuses[position]?.status ?: "Chọn mã giảm giá"
            return view
        }

        override fun getItem(position: Int): OrderStatus? {
            return orderStatuses[position]
        }

        override fun getItemId(position: Int): Long {
            return orderStatuses[position]?.id?.toLong() ?: -1L
        }

        override fun getCount(): Int {
            return orderStatuses.size
        }
}
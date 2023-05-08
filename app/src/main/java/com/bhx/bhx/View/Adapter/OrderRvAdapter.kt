package com.bhx.bhx.View.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bhx.bhx.Model.Order
import com.bhx.bhx.R

class OrderRvAdapter(private val orderList: List<Order>,  private var onClickListener: OnClickListener?) :  RecyclerView.Adapter<OrderRvAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val icon: ImageView?
        val orderName: TextView?
        val delveryTime: TextView?
        val totalPrice: TextView?
        init {
            // Define click listener for the ViewHolder's View
            icon = view.findViewById(R.id.icon)
            orderName = view.findViewById(R.id.order_name)
            delveryTime = view.findViewById(R.id.delivery_time)
            totalPrice = view.findViewById(R.id.total_price)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.order_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.icon?.setImageResource(R.drawable.xoai)
        var totalPrice: Double = 0.0
        var title: String = "${orderList[position].list.first().product.name} X${orderList[position].list.first().quantity}"
        if (orderList[position].list.size > 1){
            title += " +${orderList[position].list.size - 1}"
        }

        holder.orderName?.text = title
        holder.delveryTime?.text = orderList[position].deliveryDate.toString();
        val totalPriceStr = "${orderList[position].totalPrice} vnd"
        holder.totalPrice?.text = totalPriceStr

        holder.itemView.setOnClickListener{
            if (this.onClickListener != null) {
                this.onClickListener!!.onClick(position, orderList[position])
            }
        }
    }

    override fun getItemCount() = orderList.size

    interface OnClickListener{
        fun onClick(position: Int, model: Order)
    }
}
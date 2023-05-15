package com.bhx.bhx.View.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bhx.bhx.Model.Order
import com.bhx.bhx.R
import com.bumptech.glide.Glide
import java.text.NumberFormat
import java.util.*

class OrderRvAdapter(private val context: Context, private val orderList: List<Order>,  private var onClickListener: OnClickListener?) :  RecyclerView.Adapter<OrderRvAdapter.ViewHolder>() {
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

        Glide.with(context).load(orderList[position].list[0].product.banner).error(R.drawable.xoai)
            .into(holder.icon!!);
        var totalPrice: Double = 0.0
        var title: String = "${orderList[position].list.first().product.name} X${orderList[position].list.first().quantity}"
        if (orderList[position].list.size > 1){
            title += " +${orderList[position].list.size - 1}"
        }

        holder.orderName?.text = title
        var deliverytime = orderList[position].deliveryDate.toString();

        when (orderList[position].status) {
            "PREPARING" -> deliverytime = "$deliverytime, Đang chuẩn bị hàng"
            "DElIVERING" -> deliverytime = "$deliverytime, Đang giao"
            "DELIVERED" -> deliverytime = "$deliverytime, Đã giao"
            "CANCLED" -> deliverytime = "$deliverytime, Hủy"
        }
        holder.delveryTime?.text = deliverytime

//        val totalPriceStr = "${orderList[position].totalPrice} vnd"

        val formatter = NumberFormat.getCurrencyInstance(Locale("vi", "VN"));
        formatter.currency = Currency.getInstance("VND");
        holder.totalPrice?.text = formatter.format(orderList[position].totalPrice)

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
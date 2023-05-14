package com.bhx.bhx.View.Promotions

import android.content.Context
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bhx.bhx.Model.Product
import com.bhx.bhx.Model.PromotionsWithProducts
import com.bhx.bhx.R
import com.bhx.bhx.View.HomeFragment.ListProductAdapter

class SalePageItemAdapter(private val promotionsWithProducts: List<PromotionsWithProducts>, private val context: Context): RecyclerView.Adapter<SalePageItemAdapter.SalePageItemViewHolder>() {
    class SalePageItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val promotionName: TextView = itemView.findViewById(R.id.promotionName);
        val day_left: TextView = itemView.findViewById(R.id.day_left);
        val productListItemRecyclerView: RecyclerView = itemView.findViewById(R.id.productListRecyclerView);
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SalePageItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.sale_page_item_layout, parent, false);
        return SalePageItemViewHolder(view);
    }

    override fun onBindViewHolder(holder: SalePageItemViewHolder, position: Int) {
        holder.promotionName.text = promotionsWithProducts[position].name;
        holder.day_left.text = "Còn ${promotionsWithProducts[position].day_left} ngày";

        var adapter = ListProductAdapter(promotionsWithProducts[position].products as MutableList<Product>, context);
        holder.productListItemRecyclerView.adapter = adapter;
        holder.productListItemRecyclerView.layoutManager = GridLayoutManager(context, 3, RecyclerView.VERTICAL, false);
    }

    override fun getItemCount(): Int {
        return promotionsWithProducts.size
    }
}
package com.bhx.bhx.View.HomeFragment

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.bhx.bhx.Global.ShoppingCart
import com.bhx.bhx.Model.Product
import com.bhx.bhx.R
import com.bhx.bhx.View.DetailProduct.DetailProductFragment
import com.bumptech.glide.Glide

class ListProductAdapter(private val listProduct: List<Product>, private val context: Context):
    RecyclerView.Adapter<ListProductAdapter.ListProductViewHolder>()
{


    class ListProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val tvName: TextView = itemView.findViewById(R.id.tvName)
        val imageView: ImageView = itemView.findViewById(R.id.imageView)
        val tvPrice: TextView = itemView.findViewById(R.id.tvPrice)
        val tvBuy: TextView = itemView.findViewById(R.id.tvBuy)
//        val ln0: LinearLayout = itemView.findViewById(R.id.ln)
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListProductViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_product, parent, false)
        return ListProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListProductViewHolder, position: Int) {
        holder.tvName.text = listProduct[position].name
        holder.tvPrice.text = listProduct[position].unit_price.toString()
        Glide.with(context).load(listProduct[position].banner).error(R.drawable.xoai).into(holder.imageView)

        holder.tvName.setOnClickListener {
            val fragmentManager = (context as AppCompatActivity).supportFragmentManager
            fragmentManager.beginTransaction().replace(
                R.id.container,
                DetailProductFragment(listProduct[position])
            ).commit()
        }

        holder.tvPrice.setOnClickListener {
            val fragmentManager = (context as AppCompatActivity).supportFragmentManager
            fragmentManager.beginTransaction().replace(
                R.id.container,
                DetailProductFragment(listProduct[position])
            ).commit()
        }

        holder.imageView.setOnClickListener{
            val fragmentManager = (context as AppCompatActivity).supportFragmentManager
            fragmentManager.beginTransaction().replace(
                R.id.container,
                DetailProductFragment(listProduct[position])
            ).commit()
        }

        holder.tvBuy.setOnClickListener {
            ShoppingCart.getInstance().addItem(listProduct[position]);
//            Toast.makeText(holder.itemView.context, "Đã thêm vào giỏ hàng", Toast.LENGTH_SHORT).show();
        }
    }

    override fun getItemCount(): Int {
        return listProduct.size
    }

}
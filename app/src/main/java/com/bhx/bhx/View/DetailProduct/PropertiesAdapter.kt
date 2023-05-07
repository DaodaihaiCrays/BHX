package com.bhx.bhx.View.DetailProduct

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bhx.bhx.R

class PropertiesAdapter (private val attribute_label: List<String>?, private val attribute_value: List<String>?) :
    RecyclerView.Adapter<PropertiesAdapter.ProductViewHolder>(){

    class ProductViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvPropertyLable: TextView = view.findViewById(R.id.tvPropertyLable)
        val tvPropertyValue: TextView = view.findViewById(R.id.tvPropertyValue)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.property_item, parent, false)
        return ProductViewHolder(view)
    }

    override fun getItemCount(): Int {
        return if (attribute_label==null) 0 else attribute_label!!.size
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val lable = attribute_label!!.get(position)
        val value = attribute_value!!.get(position)
        holder.tvPropertyLable.setText(lable)
        holder.tvPropertyValue.setText(value)


    }
}
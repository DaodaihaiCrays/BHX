package com.bhx.bhx.View.Menu

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bhx.bhx.Model.SubCategory
import com.bhx.bhx.R
import com.bumptech.glide.Glide

class SubCategoryAdapter(private val subCategories: List<SubCategory>, private  val context: Context):
    RecyclerView.Adapter<SubCategoryAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgSubCate: ImageView = itemView.findViewById(R.id.imgSubCate)
        val tvNameCate: TextView = itemView.findViewById(R.id.tvNameCate)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SubCategoryAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.sub_category_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: SubCategoryAdapter.ViewHolder, position: Int) {
         val subCate = subCategories[position]
         holder.tvNameCate.text = subCate.name
         Glide.with(context).load(subCate.img).into(holder.imgSubCate)

    }

    override fun getItemCount(): Int {
        return subCategories.size
    }

}
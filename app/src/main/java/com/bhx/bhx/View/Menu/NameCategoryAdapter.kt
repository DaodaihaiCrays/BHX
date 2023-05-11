package com.bhx.bhx.View.Menu

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bhx.bhx.Model.ReviewCategory
import com.bhx.bhx.R
import com.bhx.bhx.View.HomeFragment.ListProductAdapter
import com.bhx.bhx.View.HomeFragment.ProductAdapter
import com.bhx.bhx.View.ProductOfCateFragment.ProductOfCateFragment

class NameCategoryAdapter(private val categories: List<ReviewCategory>, private  val context:Context):
    RecyclerView.Adapter<NameCategoryAdapter.NameCategoryHolder>() {

    class NameCategoryHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvCate: TextView = itemView.findViewById(R.id.tvCate)
        val tvCount: TextView = itemView.findViewById(R.id.tvCount)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NameCategoryAdapter.NameCategoryHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.menu_item, parent, false)
        return NameCategoryHolder(view)
    }

    override fun onBindViewHolder(holder: NameCategoryAdapter.NameCategoryHolder, position: Int) {
        holder.tvCate.text = categories[position].name
        holder.tvCount.text = "(" + categories[position].countProducts + ")"

        holder.tvCate.setOnClickListener {

            val fragmentManager = (context as AppCompatActivity).supportFragmentManager
            fragmentManager.beginTransaction().replace(
                R.id.container,
                ProductOfCateFragment(categories[position])
            ).addToBackStack(null).commit()
        }

    }

    override fun getItemCount(): Int {
        return categories.size
    }

}
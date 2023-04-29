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
import com.bhx.bhx.Model.SubCategory
import com.bhx.bhx.R
import com.bhx.bhx.View.HomeFragment.ListProductAdapter
import com.bhx.bhx.View.HomeFragment.ProductAdapter
import com.bhx.bhx.View.ProductOfCateFragment.ProductOfCateFragment

class NameCategoryAdapter(
    private val categories: List<ReviewCategory>,
    private val context: Context
) :
    RecyclerView.Adapter<NameCategoryAdapter.NameCategoryHolder>() {

    class NameCategoryHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvCate: TextView = itemView.findViewById(R.id.tvCate)
        val tvCount: TextView = itemView.findViewById(R.id.tvCount)
        val revMenu: RecyclerView = itemView.findViewById(R.id.revMenu)
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

        var isCollap = false

        holder.tvCate.setOnClickListener {
            if (!isCollap) {
                //call api
                val subCates = listOf<SubCategory>(
                    SubCategory(1, "Thit cac loai", "https://cdn.tgdd.vn/2022/05/CookRecipe/GalleryStep/thanh-pham-20.jpg"),
                    SubCategory(2, "Ca, hai san", "https://afamilycdn.com/150157425591193600/2021/4/1/thit-kho-tau-1-1617264490364992329242.jpeg"),
                    SubCategory(3, "Trung", "https://afamilycdn.com/150157425591193600/2021/4/1/thit-kho-tau-1-1617264490364992329242.jpeg"),
                    SubCategory(4, "Thuc pham lam", "https://cdn.tgdd.vn/2022/05/CookRecipe/GalleryStep/thanh-pham-20.jpg"),
                )
                val subAdapter = SubCategoryAdapter(subCates, context);
                val layoutManager = GridLayoutManager(context, 3, RecyclerView.VERTICAL, false)
                holder.revMenu.adapter = subAdapter
                holder.revMenu.layoutManager = layoutManager
                holder.revMenu.visibility = View.VISIBLE
                isCollap = true
            } else {
                holder.revMenu.visibility = View.GONE
                isCollap = false
            }

//            val fragmentManager = (context as AppCompatActivity).supportFragmentManager
//            fragmentManager.beginTransaction().replace(
//                R.id.container,
//                ProductOfCateFragment(categories[position])
//            ).addToBackStack(null).commit()
        }

    }

    override fun getItemCount(): Int {
        return categories.size
    }

}
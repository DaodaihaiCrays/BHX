package com.bhx.bhx.View.HomeFragment

import android.content.Context
import android.opengl.Visibility
import android.view.ActionProvider.VisibilityListener
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bhx.bhx.Model.Category
import com.bhx.bhx.Model.Product
import com.bhx.bhx.R
import com.bumptech.glide.Glide
import org.w3c.dom.Text

class ProductAdapter(private val categories: List<Category>, private val context: Context) :
    RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvCateName: TextView = itemView.findViewById(R.id.tvCateName)
        val btnSeeMore: Button = itemView.findViewById(R.id.btnSeeMore)
        //0
        val tvName0: TextView = itemView.findViewById(R.id.tvName0)
        val imageView0: ImageView = itemView.findViewById(R.id.imageView0)
        val tvPrice0: TextView = itemView.findViewById(R.id.tvPrice0)
        val tvBuy0: TextView = itemView.findViewById(R.id.tvBuy0)
        val ln0: LinearLayout = itemView.findViewById(R.id.ln0)
        //1
        val tvName1: TextView = itemView.findViewById(R.id.tvName1)
        val imageView1: ImageView = itemView.findViewById(R.id.imageView1)
        val tvPrice1: TextView = itemView.findViewById(R.id.tvPrice1)
        val tvBuy1: TextView = itemView.findViewById(R.id.tvBuy1)
        val ln1: LinearLayout = itemView.findViewById(R.id.ln1)
        //2
        val tvName2: TextView = itemView.findViewById(R.id.tvName2)
        val imageView2: ImageView = itemView.findViewById(R.id.imageView2)
        val tvPrice2: TextView = itemView.findViewById(R.id.tvPrice2)
        val tvBuy2: TextView = itemView.findViewById(R.id.tvBuy2)
        val ln2: LinearLayout = itemView.findViewById(R.id.ln2)
        //3
        val tvName3: TextView = itemView.findViewById(R.id.tvName3)
        val imageView3: ImageView = itemView.findViewById(R.id.imageView3)
        val tvPrice3: TextView = itemView.findViewById(R.id.tvPrice3)
        val tvBuy3: TextView = itemView.findViewById(R.id.tvBuy3)
        val ln3: LinearLayout = itemView.findViewById(R.id.ln3)
        //4
        val tvName4: TextView = itemView.findViewById(R.id.tvName4)
        val imageView4: ImageView = itemView.findViewById(R.id.imageView4)
        val tvPrice4: TextView = itemView.findViewById(R.id.tvPrice4)
        val tvBuy4: TextView = itemView.findViewById(R.id.tvBuy4)
        val ln4: LinearLayout = itemView.findViewById(R.id.ln4)
        //5
        val tvName5: TextView = itemView.findViewById(R.id.tvName5)
        val imageView5: ImageView = itemView.findViewById(R.id.imageView5)
        val tvPrice5: TextView = itemView.findViewById(R.id.tvPrice5)
        val tvBuy5: TextView = itemView.findViewById(R.id.tvBuy5)
        val ln5: LinearLayout = itemView.findViewById(R.id.ln5)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.product_item, parent, false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.tvCateName.text = categories[position].name
        holder.btnSeeMore.text = "Xem thêm " + (categories[position].quantity-6)+ " sản phẩm"

        //0
        if (categories[position].products[0]==null){
            holder.ln0.visibility = View.GONE
        }else {
            holder.tvName0.text = categories[position].products[0].name
            holder.tvPrice0.text = categories[position].products[0].price.toString()
            Glide.with(context).load(categories[position].products[0].img).error(R.drawable.xoai).into(holder.imageView0)
        }

        //1
        if (categories[position].products[1]==null){
            holder.ln1.visibility = View.GONE
        }else {
            holder.tvName1.text = categories[position].products[1].name
            holder.tvPrice1.text = categories[position].products[1].price.toString()
            Glide.with(context).load(categories[position].products[1].img).error(R.drawable.xoai).into(holder.imageView1)
        }

        //2
        if (categories[position].products[2]==null){
            holder.ln2.visibility = View.GONE
        }else {
            holder.tvName2.text = categories[position].products[2].name
            holder.tvPrice2.text = categories[position].products[2].price.toString()
            Glide.with(context).load(categories[position].products[2].img).error(R.drawable.xoai).into(holder.imageView2)
        }

        //3
        if (categories[position].products[3]==null){
            holder.ln3.visibility = View.GONE
        }else {
            holder.tvName3.text = categories[position].products[3].name
            holder.tvPrice3.text = categories[position].products[3].price.toString()
            Glide.with(context).load(categories[position].products[3].img).error(R.drawable.xoai).into(holder.imageView3)
        }

        //4
        if (categories[position].products[4]==null){
            holder.ln4.visibility = View.GONE
        }else {
            holder.tvName4.text = categories[position].products[4].name
            holder.tvPrice4.text = categories[position].products[4].price.toString()
            Glide.with(context).load(categories[position].products[4].img).error(R.drawable.xoai).into(holder.imageView4)
        }

        //5
        if (categories[position].products[5]==null){
            holder.ln5.visibility = View.GONE
        }else {
            holder.tvName5.text = categories[position].products[5].name
            holder.tvPrice5.text = categories[position].products[5].price.toString()
            Glide.with(context).load(categories[position].products[5].img).error(R.drawable.xoai).into(holder.imageView5)
        }

    }

    override fun getItemCount(): Int {
        return categories.size
    }
}
package com.bhx.bhx.View.ShoppingCart

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bhx.bhx.Model.CartItem
import com.bhx.bhx.R

class CartItemAdapter (private val items: ArrayList<CartItem>) : RecyclerView.Adapter<CartItemAdapter.ViewHolder>(){
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val itemName: TextView;
        val itemNote: TextView;
        val itemPrice: TextView;
        val itemQuantity: EditText;
        val itemBanner: ImageView;

        init {
            itemName = view.findViewById(R.id.itemName);
            itemNote = view.findViewById(R.id.itemNote);
            itemPrice = view.findViewById(R.id.itemPrice);
            itemQuantity = view.findViewById(R.id.itemQuantity);
            itemBanner = view.findViewById(R.id.itemBanner);
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cart_item, parent, false);

        return ViewHolder(view);
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

    }

    override fun getItemCount(): Int {
        return items.size;
    }
}
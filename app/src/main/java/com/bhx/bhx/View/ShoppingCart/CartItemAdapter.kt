package com.bhx.bhx.View.ShoppingCart

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.recyclerview.widget.RecyclerView
import com.bhx.bhx.Global.ShoppingCart
import com.bhx.bhx.Model.CartItem
import com.bhx.bhx.R
import com.bumptech.glide.Glide
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList

class CartItemAdapter(
    private val items: ArrayList<CartItem>,
    private val context: Context,
    private val cartPrice: TextView
) :
    RecyclerView.Adapter<CartItemAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val itemName: TextView;
        val itemNote: TextView;
        val itemPrice: TextView;
        val itemQuantity: TextView;
        val itemBanner: ImageView;
        val itemIncreaseBtn: AppCompatButton;
        val itemDescreaseBtn: AppCompatButton;

        init {
            itemName = view.findViewById(R.id.itemName);
            itemNote = view.findViewById(R.id.itemNote);
            itemPrice = view.findViewById(R.id.itemPrice);
            itemQuantity = view.findViewById(R.id.itemQuantity);
            itemBanner = view.findViewById(R.id.itemBanner);
            itemIncreaseBtn = view.findViewById(R.id.itemInscreaseBtn);
            itemDescreaseBtn = view.findViewById(R.id.itemDescreaseBtn);
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cart_item, parent, false);

        return ViewHolder(view);
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemName.text = items[position].product.name;
        holder.itemNote.text = items[position].product.note?.toString();
        val formatter = NumberFormat.getCurrencyInstance(Locale("vi", "VN"));
        formatter.currency = Currency.getInstance("VND");

        val unitPrice = if(items[position].product.sale_percent != null) {
            items[position].product.unit_price - items[position].product.unit_price * items[position].product.sale_percent!! / 100;

        } else {
            items[position].product.unit_price
        }
        holder.itemPrice.text = formatter.format(
            (unitPrice * items[position].quantity)
        );
        holder.itemQuantity.text = items[position].quantity.toString();
        Glide.with(context).load(items[position].product.banner).error(R.drawable.xoai)
            .into(holder.itemBanner);

        holder.itemIncreaseBtn.setOnClickListener {
            ShoppingCart.getInstance().addItem(items[holder.adapterPosition].product);
            notifyItemChanged(position);
            cartPrice.text = formatter.format(ShoppingCart.getInstance().sumPrice());
        }

        holder.itemDescreaseBtn.setOnClickListener {
            if (items[position].quantity > 1) {
                ShoppingCart.getInstance().reduceItem(position);
                notifyItemChanged(position);
            }
            else {
                ShoppingCart.getInstance().removeItem(position);
                notifyDataSetChanged();
            }
            cartPrice.text = formatter.format(ShoppingCart.getInstance().sumPrice());
        }
    }

    override fun getItemCount(): Int {
        return items.size;
    }
}
package com.bhx.bhx.View.ShoppingCart

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.widget.AppCompatButton
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bhx.bhx.Global.ShoppingCart
import com.bhx.bhx.Model.CartItem
import com.bhx.bhx.R
import java.text.NumberFormat
import java.util.*

class ShoppingCartActivity : AppCompatActivity() {
    lateinit var cart: ShoppingCart;

    lateinit var backBtn: AppCompatButton;
    lateinit var itemList: RecyclerView;
    lateinit var cartPrice: TextView;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);
        val actionBar: ActionBar? = supportActionBar;
        actionBar?.hide();

        cart = ShoppingCart.getInstance();

        backBtn = findViewById(R.id.btnBack);
        itemList = findViewById(R.id.itemList);
        cartPrice = findViewById(R.id.cartPrice);
    }

    override fun onStart() {
        super.onStart();
        backBtn.setOnClickListener { finish(); }

        val formatter = NumberFormat.getCurrencyInstance(Locale("vi", "VN"));
        formatter.currency = Currency.getInstance("VND");
        cartPrice.text = formatter.format(cart.sumPrice());

        val adapter = CartItemAdapter(cart.getItems()!!, this, cartPrice);
        itemList.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false);

        itemList.adapter = adapter;


    }
}
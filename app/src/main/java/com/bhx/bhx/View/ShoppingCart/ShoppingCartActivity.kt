package com.bhx.bhx.View.ShoppingCart

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import androidx.appcompat.app.ActionBar
import androidx.appcompat.widget.AppCompatButton
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bhx.bhx.Global.ShoppingCart
import com.bhx.bhx.Model.CartItem
import com.bhx.bhx.R

class ShoppingCartActivity : AppCompatActivity() {
    lateinit var cart: ShoppingCart;

    lateinit var backBtn: AppCompatButton;
    lateinit var itemList: RecyclerView;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);
        val actionBar: ActionBar? = supportActionBar;
        actionBar?.hide();

        cart = ShoppingCart.getInstance();

        backBtn = findViewById(R.id.btnBack);
        itemList = findViewById(R.id.itemList);

    }

    override fun onStart() {
        super.onStart();
        backBtn.setOnClickListener { finish(); }

        val adapter = CartItemAdapter(cart.getItems()!!, this);
        itemList.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false);

        itemList.adapter = adapter;
    }
}
package com.bhx.bhx.View.ShoppingCart

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.ActionBar
import androidx.recyclerview.widget.RecyclerView
import com.bhx.bhx.Global.ShoppingCart
import com.bhx.bhx.Model.CartItem
import com.bhx.bhx.R

class ShoppingCartActivity : AppCompatActivity() {
    lateinit var cart: ShoppingCart;

    lateinit var homeBtn: ImageButton;
    lateinit var itemList: RecyclerView;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);
        val actionBar: ActionBar? = supportActionBar;
        actionBar?.hide();

        cart = ShoppingCart.getInstance();

        homeBtn = findViewById(R.id.homeBtn);
        itemList = findViewById(R.id.itemList);

    }

    override fun onStart() {
        super.onStart();
        homeBtn.setOnClickListener { finish(); }

        val adapter = CartItemAdapter(cart.getItems(), this);
        itemList.adapter = adapter;
    }
}
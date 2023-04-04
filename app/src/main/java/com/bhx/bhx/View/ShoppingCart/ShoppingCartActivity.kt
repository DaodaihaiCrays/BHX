package com.bhx.bhx.View.ShoppingCart

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.ActionBar
import com.bhx.bhx.R

class ShoppingCartActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);
        val actionBar: ActionBar? = supportActionBar;
        actionBar?.hide();

        val homeBtn: ImageButton = findViewById(R.id.homeBtn);
        homeBtn.setOnClickListener { finish(); }
    }
}
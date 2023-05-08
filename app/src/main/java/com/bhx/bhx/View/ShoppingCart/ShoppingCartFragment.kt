package com.bhx.bhx.View.ShoppingCart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bhx.bhx.Global.ShoppingCart
import com.bhx.bhx.R
import com.bhx.bhx.View.HomeFragment.HomeFragment
import java.text.NumberFormat
import java.util.*

class ShoppingCartFragment: Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
    }

    lateinit var cart: ShoppingCart;

    lateinit var backBtn: AppCompatButton;
    lateinit var itemList: RecyclerView;
    lateinit var cartPrice: TextView;

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view: View = inflater.inflate(R.layout.fragment_cart, container, false);

        cart = ShoppingCart.getInstance();

        backBtn = view.findViewById(R.id.btnBack);
        itemList = view.findViewById(R.id.itemList);
        cartPrice = view.findViewById(R.id.cartPrice);

        val formatter = NumberFormat.getCurrencyInstance(Locale("vi", "VN"));
        formatter.currency = Currency.getInstance("VND");
        cartPrice.text = formatter.format(cart.sumPrice());

        val adapter = CartItemAdapter(cart.getItems()!!, container?.context!!, cartPrice);
        itemList.layoutManager = LinearLayoutManager(container.context, RecyclerView.VERTICAL, false);

        itemList.adapter = adapter;

        backBtn.setOnClickListener {
            val fragmentManager = (context as AppCompatActivity).supportFragmentManager
            fragmentManager.beginTransaction().replace(
                R.id.container,
                HomeFragment()
            ).commit();
        }

        return view;
    }
}
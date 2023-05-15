package com.bhx.bhx.View.ShoppingCart

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bhx.bhx.Global.ShoppingCart
import com.bhx.bhx.R
import com.bhx.bhx.View.Checkout.Checkout
import com.bhx.bhx.View.HomeFragment.HomeFragment
import com.google.firebase.auth.FirebaseAuth
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

    lateinit var checkoutBtn: Button

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

        checkoutBtn = view.findViewById(R.id.checkoutBtn)
        checkoutBtn.setOnClickListener{
            val cartItem = cart.getItems()
            if (cartItem!!.isEmpty()) {
                Toast.makeText(container.context, "Chưa có sản phẩm để thanh toán", Toast.LENGTH_SHORT).show()
            }
            else if(FirebaseAuth.getInstance().currentUser==null) {
                Toast.makeText(context, "Bạn phải đăng nhập để sử dụng tính năng này", Toast.LENGTH_SHORT).show()
            }
            else {
                val intent = Intent(context, Checkout::class.java)
                startActivity(intent)
            }
        }

        return view;
    }
}
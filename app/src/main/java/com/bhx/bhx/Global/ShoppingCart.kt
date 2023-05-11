package com.bhx.bhx.Global

import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bhx.bhx.Controller.ProductController
import com.bhx.bhx.Controller.RetrofitInstance
import com.bhx.bhx.Model.CartItem
import com.bhx.bhx.Model.Product
import com.bhx.bhx.View.DetailProduct.PropertiesAdapter
import com.bhx.bhx.View.HomeFragment.ListProductAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ShoppingCart private constructor() {
    private var items: ArrayList<CartItem> = ArrayList();

    companion object {
        @Volatile
        private var instance: ShoppingCart? = null;

        fun getInstance(): ShoppingCart {
            if (instance == null) {
                synchronized(this) {
                    if (instance == null) {
                        instance = ShoppingCart();
                    }
                }
            }
            return instance!!;
        }
    }

    fun getItems(): ArrayList<CartItem>? {
        return instance?.items;
    }

    fun addItem(product: Product) {
//        RetrofitInstance.getInstance().create(ProductController::class.java)
//            .getProductWithoutRelated(product.id).enqueue(object :
//                Callback<Product> {
//                override fun onResponse(call: Call<Product>, response: Response<Product>) {
//                    if (response.isSuccessful) {
//                        val currentProduct: Product? = response.body()

                        for (item in instance?.items!!) {
                            if (item.product.id == product.id) {
                                if (item.quantity + 1 <= product.stock) {
                                    item.quantity += 1;
                                }
                                return;
                            }
                        }

//                        if (currentProduct?.stock!! < 1) return;
                        val item = CartItem(product);
                        instance?.items!!.add(item);
                    }
//                }
//
//                override fun onFailure(call: Call<Product>, t: Throwable) {
//                    TODO("Not yet implemented")
//                }
//            });
//    }

    fun reduceItem(index: Int) {
        instance?.items?.get(index)?.quantity = instance?.items?.get(index)?.quantity!! - 1;
    }

    fun removeItem(index: Int) {
        instance?.items!!.removeAt(index);
    }

    fun sumPrice(): Int {
        return items.fold(0) { acc, cartItem -> acc + cartItem.product.unit_price * cartItem.quantity }
    }
}
package com.bhx.bhx.View.AccountActivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bhx.bhx.Controller.OrderController
import com.bhx.bhx.Controller.RetrofitInstance
import com.bhx.bhx.Model.Order
import com.bhx.bhx.R
import com.bhx.bhx.View.Adapter.OrderRvAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AccountOrderActivity : AppCompatActivity() {
    var backBtn: Button? = null
    var orderList: RecyclerView? = null
    lateinit var apiCategoryInstance: OrderController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account_order)
        val actionBar: ActionBar? = supportActionBar
        actionBar?.hide()
        orderList = findViewById(R.id.order_list)

        apiCategoryInstance = RetrofitInstance.getInstance().create(OrderController::class.java)
        apiCategoryInstance.getOrderList("111").enqueue(object : Callback<List<Order>> {
            override fun onResponse(
                call: Call<List<Order>>,
                response: Response<List<Order>>
            ) {
                if (response.isSuccessful) {
                    Toast.makeText(baseContext,"ok", Toast.LENGTH_SHORT).show()
                    val data = response.body()

                    var adapter = OrderRvAdapter(data?: listOf<Order>(), null)
                    orderList?.layoutManager = LinearLayoutManager(baseContext, RecyclerView.VERTICAL, false)
                    orderList?.adapter= adapter
                }else{
                    Toast.makeText(baseContext, "fail", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<List<Order>>, t: Throwable) {
                Toast.makeText(baseContext ,t.message, Toast.LENGTH_SHORT).show()
                println(t.message)
            }
        })

        backBtn = findViewById(R.id.back_btn)
        backBtn?.setOnClickListener{
            finish()
        }
    }

}
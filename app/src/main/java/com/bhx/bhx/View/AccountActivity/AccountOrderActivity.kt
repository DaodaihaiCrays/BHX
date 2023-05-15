package com.bhx.bhx.View.AccountActivity

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
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
import com.google.firebase.auth.FirebaseAuth
import com.google.gson.Gson
import kotlinx.serialization.json.Json
import kotlinx.serialization.encodeToString
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

        val dialog = ProgressDialog(this)
        dialog.create()
        dialog.setContentView(R.layout.custom_progress_dialog)
        dialog.setCancelable(false) //outside touch doesn't dismiss you
        dialog.show()
        val displayMetrics = resources?.displayMetrics
        val screenWidth = displayMetrics?.widthPixels
        val width = (screenWidth?.times(0.5))?.toInt()
        if (width != null) {
            dialog.window?.setLayout(width, WindowManager.LayoutParams.WRAP_CONTENT)
        }

        apiCategoryInstance = RetrofitInstance.getInstance().create(OrderController::class.java)
        val uid = FirebaseAuth.getInstance().uid!!
        apiCategoryInstance.getOrderList(uid).enqueue(object : Callback<List<Order>> {
            override fun onResponse(
                call: Call<List<Order>>,
                response: Response<List<Order>>
            ) {
                if (response.isSuccessful) {
                    Toast.makeText(baseContext,"ok", Toast.LENGTH_SHORT).show()
                    val data = response.body()

                    val adapter = OrderRvAdapter(this@AccountOrderActivity,data?: listOf<Order>(), object: OrderRvAdapter.OnClickListener{
                        override fun onClick(position: Int, model: Order) {
                            val intent = Intent(this@AccountOrderActivity, OrderDetail::class.java)
                            val json = Gson()
                            val orderStr = json.toJson(model)
                            Log.i("test", orderStr)
                            intent.putExtra("order", orderStr)
                            startActivity(intent)
                        }
                    })
                    orderList?.layoutManager = LinearLayoutManager(baseContext, RecyclerView.VERTICAL, false)
                    orderList?.adapter= adapter

                    dialog.dismiss()
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
package com.bhx.bhx.View.AccountActivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bhx.bhx.Controller.OrderController
import com.bhx.bhx.Controller.RetrofitInstance
import com.bhx.bhx.Global.LocationData
import com.bhx.bhx.Model.Order
import com.bhx.bhx.Model.Product
import com.bhx.bhx.Model.Province.District
import com.bhx.bhx.Model.Province.Province
import com.bhx.bhx.Model.Province.Ward
import com.bhx.bhx.Model.Timerange
import com.bhx.bhx.R
import com.bhx.bhx.View.HomeFragment.ListProductAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.gson.Gson
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.decodeFromString
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.NumberFormat
import java.util.*

class OrderDetail : AppCompatActivity() {
    lateinit var backBtn: Button;
    lateinit var orderName: TextView
    lateinit var deliveryDate: TextView
    lateinit var deliveryTime: TextView
    lateinit var deliveryLocation: TextView
    lateinit var recieverInfo: TextView
    lateinit var deliverItem: TextView
    lateinit var price: TextView

    private lateinit var provinceList: List<Province>
    private lateinit var districtList: List<District>
    private lateinit var wardList: List<Ward>

    private lateinit var order: Order

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_detail)
        val actionBar: ActionBar? = supportActionBar
        actionBar?.hide()

        val instance = RetrofitInstance.getInstance().create(OrderController::class.java)
        val user = FirebaseAuth.getInstance().currentUser?.uid


        backBtn = findViewById(R.id.back_btn)
        backBtn.setOnClickListener{
            intent.data = null
            finish()
        }

        orderName = findViewById(R.id.order_name)
        deliveryDate = findViewById(R.id.delivery_date)
        deliveryTime = findViewById(R.id.delivery_time)
        deliveryLocation = findViewById(R.id.delivery_location)
        recieverInfo = findViewById(R.id.reciever_info)
        deliverItem = findViewById(R.id.deliver_item)
        price = findViewById(R.id.price)




        val orderStr = intent.getStringExtra("order") as String
        val gson = Gson()
        order = gson.fromJson(orderStr, Order::class.java)


        var title: String = "${order.list.first().product.name} X${order.list.first().quantity}"
        if (order.list.size > 1){
            title += " +${order.list.size - 1}"
        }

        orderName.text = title
        deliveryDate.text = order.deliveryDate.toString()

        instance.getTimeRange(user?:"").enqueue(object : Callback<List<Timerange>> {
            override fun onResponse(
                call: Call<List<Timerange>>,
                response: Response<List<Timerange>>
            ){
                if (response.isSuccessful) {

                    val timeRangelist = response.body()?: listOf()

                    val curTimerange =  timeRangelist.filter { it.id == order.deliveryTimerange}.first()

                    deliveryTime.text = "${curTimerange.startTime} - ${curTimerange.endTime}"
                }
            }

            override fun onFailure(call: Call<List<Timerange>>, t: Throwable) {
                deliveryTime.text = ""
            }
        })

        provinceList = LocationData.getInstance().getProvinceList()
        districtList = LocationData.getInstance().getDistrictList()
        wardList = LocationData.getInstance().getWardList()

        val province = findProvinceValue()
        val district = findDistrictValue()
        val ward = findWardValue()

        deliveryLocation.text = "${order.address}, $ward,\n$district, $province"

        recieverInfo.text= "Tên: ${order.fullname}\nEmail: ${order.email}\nGiới tính: ${order.gender}"

        var listStr = ""

        val proList = arrayListOf<Product>()
        for (i in order.list) {
            proList.add(i.product)
            val pricePerPro = i.product.unit_price * i.quantity
            listStr = "$listStr+ ${i.product.name} x ${i.quantity}: ${pricePerPro} vnd\n"
        }

        deliverItem.text = listStr

        var deliveryStatus = ""

        val paidState = if (order.isPaid) "Đã trả" else "Trả vào lúc nhận hàng"

        when (order.status) {
            "PREPARING" -> deliveryStatus = "$deliveryStatus, Đang chuẩn bị hàng"
            "DElIVERING" -> deliveryStatus = "$deliveryStatus, Đang giao"
            "DELIVERED" -> deliveryStatus = "$deliveryStatus, Đã giao"
            "CANCLED" -> deliveryStatus = "$deliveryStatus, Hủy"
        }

        val formatter = NumberFormat.getCurrencyInstance(Locale("vi", "VN"));
        formatter.currency = Currency.getInstance("VND");
        price.text = "Tổng cộng: ${formatter.format(order.totalPrice)}\nTrạng thái: $deliveryStatus\nTình trạng phí: $paidState"
    }

    private fun findProvinceValue(): String {
        provinceList.forEach{ value ->
            if (value.id == order.province) return value.name
        }
        return ""
    }

    private fun findDistrictValue(): String {
        districtList.forEach{ value ->
            if (value.id == order.district) return value.name
        }
        return ""
    }

    private fun findWardValue(): String {
        wardList.forEach{ value ->
            if (value.id == order.ward) return value.name
        }
        return ""
    }
}
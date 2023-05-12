package com.bhx.bhx.View.AccountActivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bhx.bhx.Global.LocationData
import com.bhx.bhx.Model.Order
import com.bhx.bhx.Model.Product
import com.bhx.bhx.Model.Province.District
import com.bhx.bhx.Model.Province.Province
import com.bhx.bhx.Model.Province.Ward
import com.bhx.bhx.R
import com.bhx.bhx.View.HomeFragment.ListProductAdapter
import com.google.gson.Gson
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.decodeFromString

class OrderDetail : AppCompatActivity() {
    lateinit var backBtn: Button;
    lateinit var orderName: TextView
    lateinit var deliveryDate: TextView
    lateinit var deliveryLocation: TextView
    lateinit var recieverInfo: TextView
    lateinit var deliverItem: RecyclerView
    lateinit var price: TextView

    private lateinit var provinceList: List<Province>
    private lateinit var districtList: List<District>
    private lateinit var wardList: List<Ward>

    private lateinit var order: Order

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_detail)

        backBtn = findViewById(R.id.back_btn)
        backBtn.setOnClickListener{
            intent.data = null
            finish()
        }

        orderName = findViewById(R.id.order_name)
        deliveryDate = findViewById(R.id.delivery_date)
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

        provinceList = LocationData.getInstance().getProvinceList()
        districtList = LocationData.getInstance().getDistrictList()
        wardList = LocationData.getInstance().getWardList()

        val province = findProvinceValue()
        val district = findDistrictValue()
        val ward = findWardValue()

        deliveryLocation.text = "${order.address}, $ward, $district, $province"

        recieverInfo.text= "Tên: ${order.fullname}\nEmail: ${order.email}\nGiới tính: ${order.gender}"

        var priceStr = ""

        val proList = arrayListOf<Product>()
        for (i in order.list) {
            proList.add(i.product)
            val pricePerPro = i.product.unit_price * i.quantity
            priceStr = priceStr + "+ ${i.product.name} x ${i.quantity}: ${pricePerPro}\n"
        }

        priceStr = priceStr + "Total: ${order.totalPrice}"

        val adapter = ListProductAdapter(proList, this)
        deliverItem.adapter = adapter
        deliverItem.layoutManager = GridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false)

        price.text = priceStr
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
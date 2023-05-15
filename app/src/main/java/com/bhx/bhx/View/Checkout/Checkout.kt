package com.bhx.bhx.View.Checkout

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.ActionBar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bhx.bhx.Controller.OrderController
import com.bhx.bhx.Controller.RetrofitInstance
import com.bhx.bhx.Global.LocationData
import com.bhx.bhx.Global.ShoppingCart
import com.bhx.bhx.Global.UserInfo
import com.bhx.bhx.Model.CartItem

import com.bhx.bhx.Model.Order
import com.bhx.bhx.Model.Province.District
import com.bhx.bhx.Model.Province.Province
import com.bhx.bhx.Model.Province.Ward
import com.bhx.bhx.Model.Timerange
import com.bhx.bhx.Model.User
import com.bhx.bhx.R
import com.bhx.bhx.View.Adapter.SpinnerAdapter
import com.bhx.bhx.View.ShoppingCart.CartItemAdapter
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.gson.Gson
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.encodeToJsonElement
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody
import okhttp3.ResponseBody
import org.json.JSONObject
import org.w3c.dom.Text
import retrofit2.*
import java.sql.Time
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

class Checkout : AppCompatActivity() {
    lateinit var backBtn: Button

    lateinit var fullname: TextInputLayout
    lateinit var phone: TextInputLayout
    lateinit var gender: RadioGroup
    lateinit var email: TextInputLayout

    var provinceSpinner: Spinner? = null
    var districtSpinner: Spinner? = null
    var wardSpinner: Spinner? = null
    var acc_address: TextInputLayout? = null

    private lateinit var provinceList: List<Province>
    private lateinit var districtList: List<District>
    private lateinit var wardList: List<Ward>

    lateinit var provinceAdapter: ArrayAdapter<String>
    lateinit var districtAdapter: ArrayAdapter<String>
    lateinit var wardAdapter: ArrayAdapter<String>



    lateinit var dateBtn: Button
    lateinit var timeRange: Spinner
    lateinit var timeRangelist: List<Timerange>
    lateinit var timeRangeAdapter : ArrayAdapter<String>
    var cal = Calendar.getInstance()
    var currentTimerange : Timerange? = null
    lateinit var deliveryInfo: TextView
    lateinit var paymentType: Spinner

    lateinit var cart: ShoppingCart
    lateinit var itemList: RecyclerView
    lateinit var totalPrice: TextView

    lateinit var saveBtn: Button

    lateinit var userInfo : User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkout)
        val actionBar: ActionBar? = supportActionBar
        actionBar?.hide()

        backBtn = findViewById(R.id.back_btn)
        backBtn.setOnClickListener{
            finish()
        }

        userInfo = UserInfo.getInstance().getUser()

        fullname = findViewById(R.id.acc_full_name)
        fullname.editText?.setText(userInfo.fullname)
        phone = findViewById(R.id.acc_phone_num)
        phone.editText?.setText(userInfo.phoneNumber)
        email = findViewById(R.id.acc_email)
        email.editText?.setText(userInfo.email)
        gender = findViewById(R.id.acc_gender)



//=================================================================================
        val locationData  = LocationData.getInstance()
        provinceList = locationData.getProvinceList()
        districtList = locationData.getDistrictList()
        wardList = locationData.getWardList()

        provinceSpinner = findViewById(R.id.acc_province)
        districtSpinner = findViewById(R.id.acc_district)
        wardSpinner = findViewById(R.id.acc_ward)

        //spinner data setup
        provinceAdapter = ArrayAdapter(this, R.layout.custom_spinner_item)
        provinceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        provinceSpinner?.adapter = provinceAdapter


        districtAdapter= ArrayAdapter(this, R.layout.custom_spinner_item)
        districtAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        //districtAdapter.setNotifyOnChange(true)
        districtSpinner?.adapter = districtAdapter


        wardAdapter = ArrayAdapter(this, R.layout.custom_spinner_item)
        wardAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        //wardAdapter.setNotifyOnChange(true)
        wardSpinner?.adapter = wardAdapter


        provinceAdapter.addAll(provinceList.map { it.name })
        provinceAdapter.notifyDataSetChanged()
        //spinner behavior setup
        provinceSpinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selectedProvince = provinceList[position]

                val filteredDistricts = districtList.filter { it.province_id == selectedProvince.id || it.province_id == -1}
                districtAdapter.clear()
                districtAdapter.addAll(filteredDistricts.map { it.name })
                districtAdapter.notifyDataSetChanged()
                //districtSpinner?.setSelection(districtAdapter.getPosition(resources.getString(R.string.address_district_description)))

                districtSpinner?.isEnabled = (selectedProvince.id != -1)
                wardSpinner?.isEnabled = (selectedProvince.id != -1)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }

        districtSpinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selectedDistrict = districtList[position]
                val selectedProvince = provinceList[provinceAdapter.getPosition(provinceSpinner?.selectedItem.toString())]

                val filteredWards = wardList.filter { it.district_id == selectedDistrict.id || it.district_id == -1}
                wardAdapter.clear()
                wardAdapter.addAll(filteredWards.map { it.name })
                wardAdapter.notifyDataSetChanged()

                wardSpinner?.isEnabled = (selectedDistrict.id != -1 && selectedProvince.id != -1)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }

        wardSpinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selectedWard = wardList[position]
//                user.wardId = selectedWard.id
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }

        acc_address = findViewById(R.id.acc_address)


//=================================================================================
        dateBtn = findViewById(R.id.date_btn)
        deliveryInfo = findViewById(R.id.deliver_info)

        val dateSetListener =
            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                //updateDateInView()
            }

        dateBtn.setOnClickListener{
            DatePickerDialog(this@Checkout,
                dateSetListener,
                // set DatePickerDialog to point to today's date when it loads up
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)).show()
                updateDeliveryInfo()
        }

        val user = FirebaseAuth.getInstance().currentUser?.uid

        val instance = RetrofitInstance.getInstance().create(OrderController::class.java)

        instance.getTimeRange(user?:"").enqueue(object : Callback<List<Timerange>> {
            override fun onResponse(
                call: Call<List<Timerange>>,
                response: Response<List<Timerange>>
            ){
                if (response.isSuccessful) {

                    timeRangelist = response.body()?: listOf()

                    timeRangeAdapter = ArrayAdapter<String>(this@Checkout, R.layout.custom_spinner_item, timeRangelist.map{"${it.startTime} - ${it.endTime}"})
                    timeRangeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    timeRange.adapter = timeRangeAdapter
                }
            }

            override fun onFailure(call: Call<List<Timerange>>, t: Throwable) {
                Log.i("error", t.message!!);
                timeRangelist = listOf()
            }
        })

        timeRange = findViewById(R.id.time_range)


        timeRange.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                currentTimerange = timeRangelist[position]
                updateDeliveryInfo()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }

        paymentType = findViewById(R.id.payment_type)

        paymentType.adapter = SpinnerAdapter(this, R.layout.custom_spinner_item, listOf("Trả vào lúc giao"))
        paymentType.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {

                updateDeliveryInfo()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }


//=================================================================================
        totalPrice = findViewById(R.id.total_price)
        itemList = findViewById(R.id.itemList)

        cart = ShoppingCart.getInstance();
        val adapter = CartItemAdapter(cart.getItems()!!, this, totalPrice);
        itemList.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false);

        val formatter = NumberFormat.getCurrencyInstance(Locale("vi", "VN"));
        formatter.currency = Currency.getInstance("VND");
        totalPrice.text = formatter.format(cart.sumPrice());

        itemList.adapter = adapter;
//=================================================================================
        saveBtn = findViewById(R.id.save_btn)
        saveBtn.setOnClickListener{
            if (generateOrder()) finish()
            else {
                Toast.makeText(this," Hệ thống đã gặp lỗi hoặc chưa nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show()
            }
        }

    }

    fun updateDeliveryInfo() {
        val myFormat = "dd/MM/yyyy" // mention the format you need
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        val deliveryDate = sdf.format(cal.getTime())
        val currentPayment = paymentType.selectedItem as String

        deliveryInfo.text = "Giao vào ngày: $deliveryDate\nVào lúc: ${currentTimerange?.startTime} - ${currentTimerange?.endTime}\nThanh toán: $currentPayment "
    }

    fun generateOrder(): Boolean{
        var result = true

        var items = cart.getItems() as List<CartItem>

        var uFullname = fullname.editText?.text.toString()
        var uPhone = phone.editText?.text.toString()
        var uEmail = email.editText?.text.toString()

        if (uFullname == "" || uPhone == "") return false;
        var uGender = "MALE"
        val genderCheck = gender.checkedRadioButtonId
        when (genderCheck) {
            R.id.male_gender -> uGender = "MALE"
            R.id.female_gender -> uGender = "FEMALE"
            else -> uGender = "MALE"
        }

        val provinceId = provinceList[provinceAdapter.getPosition(provinceSpinner?.selectedItem.toString())].id
        val districtId = districtList[districtAdapter.getPosition(districtSpinner?.selectedItem.toString())].id
        val wardId = wardList[wardAdapter.getPosition(wardSpinner?.selectedItem.toString())].id
        val myFormat = "yyyy-dd-MM" // mention the format you need
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        val uDeliveryDate = sdf.format(cal.getTime())
        val timerange = timeRangelist[timeRangeAdapter.getPosition(timeRange.selectedItem.toString())].id
        val address = acc_address?.editText?.text.toString()

        val itemJson = Gson().toJson(items)

        val orderData = JSONObject().apply {
            put("items", itemJson)
            put("fullname", uFullname)
            put("phone", uPhone)
            put("email", uEmail)
            put("gender", uGender)
            put("province_id", provinceId)
            put("district_id", districtId)
            put("ward_id", wardId)
            put("address", address)
            put("delivery_date", uDeliveryDate)
            put("delivery_timerange_id", timerange)
            put("payment_method_id", 1)
        }

        Log.i("test",  orderData.toString())
        val requestBody = RequestBody.create(
            "application/json".toMediaType(),
            orderData.toString()
        )

        val uid = FirebaseAuth.getInstance().currentUser?.uid
        if (uid != null)
            RetrofitInstance.getInstance().create(OrderController::class.java).newOrder(uid, requestBody).enqueue(object :
                Callback<ResponseBody> {
                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                    if (!response.isSuccessful){
                        result = false
                    }
                    else {
                        cart.clearItem()
                    }
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    result = false
                }
            })

        return result
    }
}
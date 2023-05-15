package com.bhx.bhx.View.AccountActivity

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import android.widget.AdapterView.OnItemSelectedListener
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import com.bhx.bhx.Controller.LocationController
import com.bhx.bhx.Controller.RetrofitInstance
import com.bhx.bhx.Global.LocationData
import com.bhx.bhx.Global.UserInfo
import com.bhx.bhx.Model.Province.District
import com.bhx.bhx.Model.Province.Province
import com.bhx.bhx.Model.Province.Ward
import com.bhx.bhx.Model.User
import com.bhx.bhx.R
import com.bhx.bhx.View.Adapter.SpinnerAdapter
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AccountInfoActivity : AppCompatActivity() {
    var backBtn: Button? = null
    var saveBtn: Button? = null

    var acc_full_name: TextInputLayout? = null
    var acc_phone_num: TextInputLayout? = null
    var acc_gender: RadioGroup? = null

    var provinceSpinner: Spinner? = null
    var districtSpinner: Spinner? = null
    var wardSpinner: Spinner? = null
    lateinit var provinceAdapter: ArrayAdapter<String>
    lateinit var districtAdapter: ArrayAdapter<String>
    lateinit var wardAdapter: ArrayAdapter<String>
    var acc_address: TextInputLayout? = null


    lateinit var  user: User

    private lateinit var provinceList: List<Province>
    private lateinit var districtList: List<District>
    private lateinit var wardList: List<Ward>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account_info)
        val actionBar: ActionBar? = supportActionBar
        //data init
        user = UserInfo.getInstance().getUser()

        val locationData  = LocationData.getInstance()
        provinceList = locationData.getProvinceList()
        districtList = locationData.getDistrictList()
        wardList = locationData.getWardList()

        actionBar?.hide()
        //ui component init
        backBtn = findViewById(R.id.back_btn)
        backBtn?.setOnClickListener{
            finish()
        }
        saveBtn = findViewById(R.id.save_btn)
        saveBtn?.setOnClickListener{
            if (updateUserToApi()) {
                Toast.makeText(this, "Lưu thông tin thành công", Toast.LENGTH_SHORT).show()
                finish()
            }
            else {
                Toast.makeText(this, "Hệ thống đã gặp lỗi", Toast.LENGTH_SHORT).show()
            }
        }

        acc_full_name = findViewById(R.id.acc_full_name)
        acc_phone_num = findViewById(R.id.acc_phone_num)
        acc_gender = findViewById(R.id.acc_gender)

        provinceSpinner = findViewById(R.id.acc_province)
        districtSpinner = findViewById(R.id.acc_district)
        wardSpinner = findViewById(R.id.acc_ward)

        acc_address = findViewById(R.id.acc_address)
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
        provinceSpinner?.onItemSelectedListener = object : OnItemSelectedListener {
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

        districtSpinner?.onItemSelectedListener = object : OnItemSelectedListener {
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

        wardSpinner?.onItemSelectedListener = object : OnItemSelectedListener {
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

        provinceSpinner?.setSelection(provinceAdapter.getPosition(resources.getString(R.string.address_province_description)))
        //data to ui
        updateUserToUI()
    }



    private fun findProvinceValue(): String {
        provinceList.forEach{ value ->
            if (value.id == user.provinceId) return value.name
        }
        return ""
    }

    private fun findDistrictValue(): String {
        districtList.forEach{ value ->
            if (value.id == user.districtId) return value.name
        }
        return ""
    }

    private fun findWardValue(): String {
        wardList.forEach{ value ->
            if (value.id == user.wardId) return value.name
        }
        return ""
    }

    private fun updateUserToUI() {
        acc_full_name?.editText?.setText(user.fullname)
        acc_phone_num?.editText?.setText(user.phoneNumber)

        var genderCheck = findViewById<RadioButton>(R.id.other_gender)
        when(user.gender){
            "MALE" -> genderCheck = findViewById(R.id.male_gender)
            "FEMALE" -> genderCheck = findViewById(R.id.female_gender)
            else -> genderCheck = findViewById(R.id.other_gender)
        }
        genderCheck.isChecked = true

        val province = findProvinceValue()
        if (province != "") {
            val district = findDistrictValue()
            provinceSpinner?.setSelection(provinceAdapter.getPosition(province))
            if (district != "") {
                districtSpinner?.setSelection(districtAdapter.getPosition(district))
                val ward = findWardValue()
                Log.i("Test", ward)
                if (ward != "") wardSpinner?.setSelection(wardAdapter.getPosition(ward))
            }
        }
        acc_address?.editText?.setText(user.address)
    }

    private fun updateUserToApi() : Boolean {
        user.fullname = acc_full_name?.editText?.text.toString()
        user.phoneNumber = acc_phone_num?.editText?.text.toString()

        val genderCheck = acc_gender?.checkedRadioButtonId
        when (genderCheck) {
            R.id.male_gender -> user.gender = "MALE"
            R.id.female_gender -> user.gender = "FEMALE"
            else -> user.gender = "MALE"
        }

        user.provinceId = provinceList[provinceAdapter.getPosition(provinceSpinner?.selectedItem.toString())].id
        user.districtId = districtList[districtAdapter.getPosition(districtSpinner?.selectedItem.toString())].id
        user.wardId = wardList[wardAdapter.getPosition(wardSpinner?.selectedItem.toString())].id
        user.address = acc_address?.editText?.text.toString()

        return UserInfo.getInstance().updateToApi(user)
    }
}
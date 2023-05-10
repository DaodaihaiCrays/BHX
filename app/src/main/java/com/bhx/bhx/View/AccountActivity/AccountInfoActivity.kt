package com.bhx.bhx.View.AccountActivity

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
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
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AccountInfoActivity : AppCompatActivity() {
    var backBtn: Button? = null
    var provinceSpinner: Spinner? = null
    var districtSpinner: Spinner? = null
    var wardSpinner: Spinner? = null

    lateinit var  user: User

    private lateinit var provinceList: List<Province>
    private lateinit var districtList: List<District>
    private lateinit var wardList: List<Ward>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account_info)
        val actionBar: ActionBar? = supportActionBar

        user = UserInfo.getInstance().getUser()

        val locationData  = LocationData.getInstance()
        provinceList = locationData.getProvinceList()
        districtList = locationData.getDistrictList()
        wardList = locationData.getWardList()

        actionBar?.hide()

        backBtn = findViewById(R.id.back_btn)
        backBtn?.setOnClickListener{
            finish()
        }



        provinceSpinner = findViewById(R.id.acc_province)
        districtSpinner = findViewById(R.id.acc_district)
        wardSpinner = findViewById(R.id.acc_ward)




        val provinceAdapter = ArrayAdapter<String>(this, R.layout.custom_spinner_item)
//        provinceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        provinceSpinner?.adapter = provinceAdapter

        val districtAdapter= ArrayAdapter<String>(this, R.layout.custom_spinner_item)
        districtAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        districtSpinner?.adapter = districtAdapter

        val wardAdapter = ArrayAdapter<String>(this, R.layout.custom_spinner_item)
        wardAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        wardSpinner?.adapter = wardAdapter

        provinceAdapter.addAll(provinceList.map { it.name })
        provinceAdapter.notifyDataSetChanged()

        provinceSpinner?.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selectedProvince = provinceList[position]
                user.provinceId = selectedProvince.id

                val filteredDistricts = districtList.filter { it.province_id == selectedProvince.id }
                districtAdapter.clear()
                districtAdapter.addAll(filteredDistricts.map { it.name })
                districtAdapter.notifyDataSetChanged()
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
                user.districtId = selectedDistrict.id
                val filteredWards = wardList.filter { it.district_id == selectedDistrict.id }
                wardAdapter.clear()
                wardAdapter.addAll(filteredWards.map { it.name })
                wardAdapter.notifyDataSetChanged()
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
                user.wardId = selectedWard.id
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }
    }



    private fun findProvinceIndex(): Int {
        provinceList.forEachIndexed{index, value ->
            if (value.id == user.provinceId) return index
        }
        return -1
    }

    private fun findDistrictIndex(): Int {
        districtList.forEachIndexed{index, value ->
            if (value.id == user.districtId) return index
        }
        return -1
    }

    private fun findWardIndex(): Int {
        wardList.forEachIndexed{index, value ->
            if (value.id == user.wardId) return index
        }
        return -1
    }
}
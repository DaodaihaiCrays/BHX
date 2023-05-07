package com.bhx.bhx.View

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import com.bhx.bhx.Global.UserInfo
import com.bhx.bhx.Model.User
import com.bhx.bhx.R
import com.bhx.bhx.View.Adapter.SpinnerAdapter
import android.widget.ArrayAdapter as ArrayAdapter

class AccountInfoActivity : AppCompatActivity() {
    var backBtn: Button? = null
    var provinceSpinner: Spinner? = null
    var districtSpinner: Spinner? = null
    var wardSpinner: Spinner? = null

    var user: User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account_info)
        val actionBar: ActionBar? = supportActionBar

        user = UserInfo.getInstance().getUser()
        actionBar?.hide()

        backBtn = findViewById(R.id.back_btn)
        backBtn?.setOnClickListener{
            finish()
        }

        provinceSpinner = findViewById(R.id.acc_province)
        districtSpinner = findViewById(R.id.acc_district)
        wardSpinner = findViewById(R.id.acc_ward)

        var provinceList = listOf(resources.getString(R.string.address_province_description), "Hồ Chí Minh")
        var districtList= listOf(resources.getString(R.string.address_district_description), "Hóc môn")
        var wardList = listOf(resources.getString(R.string.address_ward_description), "Tân Hiệp")

        val provinceAdapter= SpinnerAdapter(this, R.layout.custom_spinner_item, provinceList)
        provinceSpinner?.adapter = provinceAdapter

        val districtAdapter= SpinnerAdapter(this, R.layout.custom_spinner_item, districtList)
        districtSpinner?.adapter = districtAdapter

        val wardAdapter = SpinnerAdapter(this, R.layout.custom_spinner_item, wardList)
        wardSpinner?.adapter = wardAdapter
    }
}
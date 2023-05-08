package com.bhx.bhx.Global

import com.bhx.bhx.Controller.ProvinceController
import com.bhx.bhx.Controller.RetrofitInstance
import com.bhx.bhx.Model.Province.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LocationData {
    private var provinceList: List<Province>? = null
    private var provinceData: ExtendProvince? = null
    private lateinit var wardList: List<Ward>
    fun getProvinceList(): List<String>{
        var result = arrayListOf<String>()
        for (i in provinceList!!) result.add(i.name)
        return result
    }


    fun getDistrictList(): List<String>{
        var result = arrayListOf<String>()
        for (i in provinceData!!.districts) result.add(i.name)
        return result
    }

    fun getDistrictId(district_name: String): Int {
        for (i in provinceData!!.districts)
            if (i.name == district_name) return i.id
        return -1;
        
    }

    fun getWardList(district_name: String): List<String>{
        var result = arrayListOf<String>()
        for (i in provinceData!!.districts){
            wardList = i.wards
            if (i.name == district_name){
                for (j in i.wards) result.add(j.name)
            }
            return result
        }
        return result
    }

    fun getWardId(ward_name: String): Int {
        for (i in wardList){
            if (i.name == ward_name) return i.id
        }
        return -1
    }

   fun updateProvinceList() = runBlocking {
        launch(Dispatchers.Unconfined) {
            syncFromApi()
        }
    }

    fun updateProvinceData(name: String) = runBlocking {
        for (i in provinceList!!){
            if (i.name == name)
            launch(Dispatchers.Unconfined) {
                syncProvinceDataFromApi(i.id)
            }
            break
        }

    }


    private suspend fun syncFromApi() {
        val apiInstance = RetrofitInstance.getInstance().create(ProvinceController::class.java)
        apiInstance.getProvinceList().enqueue(object : Callback<List<Province>> {
            override fun onResponse(
                call: Call<List<Province>>,
                response: Response<List<Province>>
            ){
                if (response.isSuccessful) {
                    provinceList = response.body()
                }
            }

            override fun onFailure(call: Call<List<Province>>, t: Throwable) {

            }
        })
    }

    private suspend fun syncProvinceDataFromApi(id: Int) {
        val apiInstance = RetrofitInstance.getInstance().create(ProvinceController::class.java)
        apiInstance.getProvinceData(id).enqueue(object : Callback<ExtendProvince> {
            override fun onResponse(
                call: Call<ExtendProvince>,
                response: Response<ExtendProvince>
            ){
                if (response.isSuccessful) {
                    provinceData = response.body()
                }
            }

            override fun onFailure(call: Call<ExtendProvince>, t: Throwable) {

            }
        })
    }


    companion object {
        @Volatile
        private var instance: LocationData? = null;

        fun getInstance(): LocationData {
            if (instance == null) {
                synchronized(this) {
                    if (instance == null) {
                        instance = LocationData();
                    }
                }
            }
            return instance!!;
        }
    }
}
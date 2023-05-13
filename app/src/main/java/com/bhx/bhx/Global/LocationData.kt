package com.bhx.bhx.Global

import android.content.res.Resources
import com.bhx.bhx.Controller.LocationController
import com.bhx.bhx.Controller.RetrofitInstance
import com.bhx.bhx.Model.Province.*
import com.bhx.bhx.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LocationData {
    private var provinceList: List<Province>? = null
    private var districtList: List<District>? = null
    private var wardList: List<Ward>? = null

    private constructor(){
        syncFromApi()
    }

    fun getProvinceList() = provinceList?: listOf()
    fun getDistrictList() = districtList?: listOf()
    fun getWardList() = wardList?: listOf()
    fun syncFromApi() = runBlocking {
        launch {
            getProvince()
            getDistrict()
            getWard()
        }
    }
    suspend fun getProvince() : List<Province> {
        val apiInstance = RetrofitInstance.getInstance().create(LocationController::class.java)
        var result: List<Province> = listOf()
        apiInstance.getProvinceList().enqueue(object : Callback<List<Province>> {
            override fun onResponse(
                call: Call<List<Province>>,
                response: Response<List<Province>>
            ) {
                if (response.isSuccessful) {
                    provinceList = response.body()
//                    provinceList = provinceList!! + listOf(Province(-1, "Tỉnh/Thành Phố"))
                }
            }

            override fun onFailure(call: Call<List<Province>>, t: Throwable) {
//                provinceList = listOf(Province(-1, "Tỉnh/Thành Phố"))
                provinceList = listOf()
            }
        })


        return result
    }


    suspend fun getDistrict() : List<District> {
        val apiInstance = RetrofitInstance.getInstance().create(LocationController::class.java)
        var result: List<District> = listOf()
        apiInstance.getDistrictList().enqueue(object : Callback<List<District>> {
            override fun onResponse(
                call: Call<List<District>>,
                response: Response<List<District>>
            ) {
                if (response.isSuccessful) {
                    districtList = response.body()
//                    districtList = districtList!! + listOf(District(-1, "Quận/Huyện", -1))
                }
            }

            override fun onFailure(call: Call<List<District>>, t: Throwable) {
//                districtList = listOf(District(-1, "Quận/Huyện", -1))
                districtList = listOf()
            }
        })


        return result
    }

    suspend fun getWard() : List<Ward> {
        val apiInstance = RetrofitInstance.getInstance().create(LocationController::class.java)
        var result: List<Ward> = listOf()
        apiInstance.getWardList().enqueue(object : Callback<List<Ward>> {
            override fun onResponse(
                call: Call<List<Ward>>,
                response: Response<List<Ward>>
            ) {
                if (response.isSuccessful) {
                    wardList = response.body()
//                    wardList = wardList!! + listOf(Ward(-1, "Ấp/Xã", -1))
                }
            }

            override fun onFailure(call: Call<List<Ward>>, t: Throwable) {
//                wardList = listOf(Ward(-1, "Ấp/Xã", -1))
                wardList = listOf()
            }
        })


        return result
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
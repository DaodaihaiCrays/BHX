package com.bhx.bhx.Controller


import com.bhx.bhx.Model.Province.District
import com.bhx.bhx.Model.Province.Province
import com.bhx.bhx.Model.Province.Ward
import retrofit2.Call
import retrofit2.http.GET

interface LocationController {
    @GET("/provinces")
    fun getProvinceList(): Call<List<Province>>
    @GET("/districts")
    fun getDistrictList(): Call<List<District>>
    @GET("/wards")
    fun getWardList(): Call<List<Ward>>

}
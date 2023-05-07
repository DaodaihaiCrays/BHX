package com.bhx.bhx.Controller


import com.bhx.bhx.Model.Province.ExtendProvince
import com.bhx.bhx.Model.Province.Province
import retrofit2.Call
import retrofit2.http.Path
import retrofit2.http.GET

interface ProvinceController {
    @GET("/provinces")
    fun getProvinceList(): Call<List<Province>>
    @GET("/provinces/{id}")
    fun getProvinceData(@Path("id") id: Int): Call<ExtendProvince>
}
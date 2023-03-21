package com.bhx.bhx.Controller

import com.bhx.bhx.Constant.URL
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInstance {
    companion object {
        fun getInstance(): Retrofit {
            var gson = GsonBuilder()
                .setDateFormat("YYYY-MM-dd HH:mm:ss").create()

            return Retrofit.Builder()
                .baseUrl(URL.DOMAIN_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
        }
    }
}
package com.bhx.bhx.Controller

import android.util.Log
import com.bhx.bhx.Constant.URL
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInstance {
    companion object {
        fun getInstance(): Retrofit {
            var gson = GsonBuilder()
                .setDateFormat("YYYY-MM-dd HH:mm:ss").create()

            val client = OkHttpClient.Builder()
                .sslSocketFactory(TrustAllCerts.createSSLSocketFactory(), TrustAllCerts())
                .hostnameVerifier(TrustAllCerts.TrustAllHostnameVerifier())
                .build()

            return Retrofit.Builder()
                .baseUrl(URL.DOMAIN_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
        }
    }
}
package com.bhx.bhx.Controller

import com.bhx.bhx.Model.Account
import com.bhx.bhx.Model.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AccountController {

    @GET("accounts")
    fun getAccounts(): Call<List<Account>>


}
package com.bhx.bhx.Controller

import com.bhx.bhx.Model.Account
import retrofit2.Call
import retrofit2.http.GET

interface AccountController {

    @GET("/accounts")
    fun getAccounts(): Call<List<Account>>
}
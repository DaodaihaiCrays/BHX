package com.bhx.bhx.Model

data class Account (val id: Int, val username: String, val password: String, val balance: Double)

data class AccountLogin(val username: String, val password: String)
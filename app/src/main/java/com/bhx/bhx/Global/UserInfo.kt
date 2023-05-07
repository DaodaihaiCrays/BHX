package com.bhx.bhx.Global

import android.os.AsyncTask
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bhx.bhx.Controller.RetrofitInstance
import com.bhx.bhx.Controller.UserController
import com.bhx.bhx.Model.Order
import com.bhx.bhx.Model.User
import com.bhx.bhx.View.Adapter.OrderRvAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import retrofit2.Call
import retrofit2.Response
import retrofit2.Callback

class UserInfo{
    private var _data: User? = null

    private constructor() {
        updateFromApi()
    }

    fun updateFromApi() = runBlocking {
        launch(Dispatchers.Unconfined) {
            syncFromApi()
        }
    }

    private suspend fun syncFromApi() {
        val uid = "111"
        val apiInstance = RetrofitInstance.getInstance().create(UserController::class.java)
        apiInstance.getUserInfo(uid).enqueue(object : Callback<User> {
            override fun onResponse(
                call: Call<User>,
                response: Response<User>
            ){
                if (response.isSuccessful) {
                    _data = response.body()
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {

            }
        })
    }

    fun getUser() = _data

    companion object {
        @Volatile
        private var instance: UserInfo? = null;

        fun getInstance(): UserInfo {
            if (instance == null) {
                synchronized(this) {
                    if (instance == null) {
                        instance = UserInfo();
                    }
                }
            }
            return instance!!;
        }
    }
}
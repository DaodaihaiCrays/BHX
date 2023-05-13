package com.bhx.bhx.Global

import android.os.AsyncTask
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bhx.bhx.Controller.RetrofitInstance
import com.bhx.bhx.Controller.UserController
import com.bhx.bhx.Model.Order
import com.bhx.bhx.Model.User
import com.bhx.bhx.View.Adapter.OrderRvAdapter
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response
import retrofit2.Callback

class UserInfo{
    private var _data: User? = null

    private constructor() {
        updateFromApi()
    }

    fun updateFromApi() = runBlocking {
        launch {
            syncFromApi()
        }
    }

    fun updateToApi(user: User) : Boolean{
        var success: Boolean = true
        runBlocking {
            if (user.id != _data?.id) {
                success = false
                return@runBlocking
            }
            _data = user
            launch {
                success = syncToApi()
            }


        }

        return success
    }


    private suspend fun syncFromApi() {
        if(FirebaseAuth.getInstance().currentUser!=null) {
            val uid = FirebaseAuth.getInstance().uid!!;
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
    }

    private suspend fun syncToApi() : Boolean{
        var success: Boolean = true
        val nData = JSONObject().apply {
            put("email", _data!!.email)
            put("fullname", _data!!.fullname)
            put("phone_number", _data!!.phoneNumber)
            put("gender", _data!!.gender)
            put("province_id", _data!!.provinceId)
            put("district_id", _data!!.districtId)
            put("ward_id", _data!!.wardId)
            put("address", _data!!.address)
            put("authority", _data!!.authority)
        }

        val requestBody = nData.toString().toRequestBody("application/json".toMediaType())
        RetrofitInstance.getInstance().create(UserController::class.java).updateUserInfo(_data!!.id, requestBody).enqueue(object :
            Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (!response.isSuccessful){
                    success = false
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                success = false
            }
        })
        return success
    }


    fun getUser() = _data!!.copy()

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
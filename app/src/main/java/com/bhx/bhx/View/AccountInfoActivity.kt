package com.bhx.bhx.View

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.ActionBar
import com.bhx.bhx.R

class AccountInfoActivity : AppCompatActivity() {
    var backBtn: Button? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account_info)
        val actionBar: ActionBar? = supportActionBar
        actionBar?.hide()

        backBtn = findViewById(R.id.back_btn)
        backBtn?.setOnClickListener{
            finish()
        }
    }
}
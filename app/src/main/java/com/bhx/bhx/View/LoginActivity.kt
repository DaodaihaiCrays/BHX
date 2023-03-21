package com.bhx.bhx.View

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import com.bhx.bhx.Model.Account
import com.bhx.bhx.Model.AccountLogin
import com.bhx.bhx.R
import okhttp3.internal.Util
import java.io.DataInput

class LoginActivity : AppCompatActivity() {
    lateinit var edtUsername: EditText;
    lateinit var edtPassword: EditText;
    lateinit var btnLogin: Button;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val actionBar: ActionBar? = supportActionBar
        actionBar?.hide()
        refView()

        btnLogin.setOnClickListener(View.OnClickListener {
            val username: String = edtUsername.text.toString().trim()
            val password: String = edtUsername.text.toString().trim()
            val account: AccountLogin = AccountLogin(username, password)
            if (account!=null){
                val intent: Intent = Intent(this@LoginActivity, MainActivity::class.java)
                startActivity(intent)
            }else {
                Toast.makeText(this@LoginActivity, "Invalid account", Toast.LENGTH_SHORT).show()
            }
        })

    }

    private fun refView() {
        edtUsername = findViewById(R.id.edtUsername)
        edtPassword = findViewById(R.id.edtPassword)
        btnLogin = findViewById(R.id.btnLogin)
    }
}
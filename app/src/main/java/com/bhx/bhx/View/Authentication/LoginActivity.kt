package com.bhx.bhx.View.Authentication

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import com.bhx.bhx.Model.AccountLogin
import com.bhx.bhx.R
import com.bhx.bhx.View.MainActivity
import com.bhx.bhx.View.ShoppingCart.ShoppingCartFragment
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract


class LoginActivity : AppCompatActivity() {
    lateinit var edtUsername: EditText;
    lateinit var edtPassword: EditText;
    lateinit var btnLogin: Button;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        val actionBar: ActionBar? = supportActionBar;
        actionBar?.hide();

        supportFragmentManager.beginTransaction().replace(
            R.id.login_fragment_placeholder,
            LoginMainFragment()
        ).commit();
    }
}
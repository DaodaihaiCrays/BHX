package com.bhx.bhx.View.Authentication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import com.bhx.bhx.R

class SignupActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        val actionBar: ActionBar? = supportActionBar;
        actionBar?.hide();

        supportFragmentManager.beginTransaction().replace(
            R.id.signup_fragment_placeholder,
            SignupMainFragment()
        ).commit();
    }
}
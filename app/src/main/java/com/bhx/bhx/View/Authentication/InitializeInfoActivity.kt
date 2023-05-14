package com.bhx.bhx.View.Authentication

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.ActionBar
import androidx.core.widget.doOnTextChanged
import com.bhx.bhx.R
import com.bhx.bhx.databinding.ActivityInitializeInfoBinding
import com.google.android.material.snackbar.Snackbar

class InitializeInfoActivity : AppCompatActivity() {
    lateinit var nameTextInputLayout: com.google.android.material.textfield.TextInputLayout;
    lateinit var nameEdittext: com.google.android.material.textfield.TextInputEditText;
    lateinit var phoneTextInputLayout: com.google.android.material.textfield.TextInputLayout;
    lateinit var phoneEdittext: com.google.android.material.textfield.TextInputEditText;
    lateinit var emailTextInputLayout: com.google.android.material.textfield.TextInputLayout;
    lateinit var emailEdittext: com.google.android.material.textfield.TextInputEditText;
    lateinit var signupBtn: Button;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initialize_info);
        val actionBar: ActionBar? = supportActionBar;
        actionBar?.hide();

        nameTextInputLayout = findViewById(R.id.nameTextInputLayout);
        nameEdittext = findViewById(R.id.nameEdittext);
        phoneEdittext = findViewById(R.id.phoneEdittext);
        emailEdittext = findViewById(R.id.emailEdittext);
        signupBtn = findViewById(R.id.signupBtn);
    }

    override fun onStart() {
        super.onStart();

        nameEdittext.doOnTextChanged { text, start, before, count ->
            if (text!!.isEmpty()) {
                nameTextInputLayout.error = "*Yêu cầu";
            } else {
                nameTextInputLayout.error = null;
            }
        }

        val intent: Intent = intent;

        if (intent.getStringExtra("phone") != null) phoneEdittext.setText(intent.getStringExtra("phone"));
        if (intent.getStringExtra("email") != null) emailEdittext.setText(intent.getStringExtra("email"));

        signupBtn.setOnClickListener {
            if (nameEdittext.error.isNullOrEmpty()) {
                intent.putExtra("name", nameEdittext.text.toString());
                if (phoneEdittext.text!!.isNotEmpty()) intent.putExtra("phone", phoneEdittext.text.toString());
                if (emailEdittext.text!!.isNotEmpty()) intent.putExtra("email", emailEdittext.text.toString());

                setResult(Activity.RESULT_OK, intent);
                finish();
            } else {
                Snackbar.make(it, "Vui lòng nhập họ và tên để tiếp tục", 1000).show();
            }
        }

    }
}
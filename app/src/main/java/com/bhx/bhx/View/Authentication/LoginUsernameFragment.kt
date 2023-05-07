package com.bhx.bhx.View.Authentication

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.bhx.bhx.Constant.AuthConstanst
import com.bhx.bhx.R
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import kotlin.math.log

class LoginUsernameFragment: Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
    }

    lateinit var accountEdittext: com.google.android.material.textfield.TextInputEditText;
    lateinit var passwordEdittext: com.google.android.material.textfield.TextInputEditText;
    lateinit var loginBtn: Button;
    lateinit var auth: FirebaseAuth;

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {3
        var view: View = inflater.inflate(R.layout.fragment_login_input, container, false);
        accountEdittext = view.findViewById(R.id.accountEdittext);
        passwordEdittext = view.findViewById(R.id.passwordEdittext);
        loginBtn = view.findViewById(R.id.loginBtn);
        auth = FirebaseAuth.getInstance();
        loginBtn.setOnClickListener {
            auth.signInWithEmailAndPassword(accountEdittext.text.toString() + AuthConstanst.FAKE_USERNAME_DOMAIN, passwordEdittext.text.toString())
                .addOnCompleteListener(requireActivity()) { task ->
                    if (task.isSuccessful) {
                        val user = auth.currentUser
                        Log.d("Shin", user?.uid.toString())
                    } else {
                        Snackbar.make(it, "Tên đăng nhập hoặc mật khẩu không đúng", 1000).show();
                    }
                }
        }
        return view;
    }
}
package com.bhx.bhx.View.Authentication

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.bhx.bhx.R
import com.google.android.material.button.MaterialButton

class LoginMainFragment: Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
    }

    lateinit var signupBtn: Button;
    lateinit var usernameBtn: MaterialButton;
    lateinit var phoneBtn: MaterialButton;
    lateinit var emailBtn: MaterialButton;

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view: View = inflater.inflate(R.layout.fragment_login_main, container, false);
        signupBtn = view.findViewById(R.id.signupBtn);
        usernameBtn = view.findViewById(R.id.usernameLoginBtn);
        phoneBtn = view.findViewById(R.id.phoneLoginBtn);
        emailBtn = view.findViewById(R.id.emailLoginBtn);

        signupBtn.setOnClickListener {
            val intent: Intent = Intent(container?.context, SignupActivity::class.java);
            startActivity(intent);
        }

        usernameBtn.setOnClickListener {
            parentFragmentManager.beginTransaction().replace(
                R.id.login_fragment_placeholder,
                LoginUsernameFragment()
            ).commit();
        }
        phoneBtn.setOnClickListener {
            parentFragmentManager.beginTransaction().replace(
                R.id.login_fragment_placeholder,
                LoginPhoneFragment()
            ).commit();
        }
        emailBtn.setOnClickListener {
            parentFragmentManager.beginTransaction().replace(
                R.id.login_fragment_placeholder,
                LoginEmailFragment()
            ).commit();
        }
        return view;
    }
}
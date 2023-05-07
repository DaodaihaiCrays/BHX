package com.bhx.bhx.View.Authentication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.bhx.bhx.R
import com.google.android.material.button.MaterialButton

class SignupMainFragment: Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
    }

    lateinit var loginBtn: Button;
    lateinit var usernameBtn: MaterialButton;
    lateinit var phoneBtn: MaterialButton;
    lateinit var emailBtn: MaterialButton;

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view: View = inflater.inflate(R.layout.fragment_signup_main, container, false);
        loginBtn = view.findViewById(R.id.loginBtn);
        usernameBtn = view.findViewById(R.id.usernameBtn);
        phoneBtn = view.findViewById(R.id.phoneBtn);
        emailBtn = view.findViewById(R.id.emailBtn);

        loginBtn.setOnClickListener { activity?.finish(); }
        usernameBtn.setOnClickListener {
            parentFragmentManager.beginTransaction().replace(
                R.id.signup_fragment_placeholder,
                SignupUsernameFragment()
            ).commit();
        }
        phoneBtn.setOnClickListener {
            parentFragmentManager.beginTransaction().replace(
                R.id.signup_fragment_placeholder,
                SignupPhoneFragment()
            ).commit();
        }
        emailBtn.setOnClickListener {
            parentFragmentManager.beginTransaction().replace(
                R.id.signup_fragment_placeholder,
                SignupEmailFragment()
            ).commit();
        }
        return view;
    }
}
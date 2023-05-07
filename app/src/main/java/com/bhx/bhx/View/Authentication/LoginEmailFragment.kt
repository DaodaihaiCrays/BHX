package com.bhx.bhx.View.Authentication

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.content.ContextCompat
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import com.bhx.bhx.Constant.AuthConstanst
import com.bhx.bhx.Global.UserInfo
import com.bhx.bhx.R
import com.bhx.bhx.View.MainActivity
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import kotlin.math.log

class LoginEmailFragment: Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
    }

    lateinit var accountTextInputLayout: com.google.android.material.textfield.TextInputLayout;
    lateinit var passwordTextInputLayout: com.google.android.material.textfield.TextInputLayout;
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
        accountTextInputLayout = view.findViewById(R.id.accountTextInputLayout);
        accountEdittext = view.findViewById(R.id.accountEdittext);
        passwordTextInputLayout = view.findViewById(R.id.passwordTextInputLayout);
        passwordEdittext = view.findViewById(R.id.passwordEdittext);
        loginBtn = view.findViewById(R.id.loginBtn);
        auth = FirebaseAuth.getInstance();

        accountTextInputLayout.hint = "Email";
        accountTextInputLayout.startIconDrawable = ContextCompat.getDrawable(container?.context!!, R.drawable.ic_mail);

        accountEdittext.doOnTextChanged { text, start, before, count ->
            if (text!!.isEmpty()) {
                accountTextInputLayout.error = "Email không được bỏ trống";
            } else {
                accountTextInputLayout.error = null;
            }
        }

        passwordEdittext.doOnTextChanged { text, start, before, count ->
            if (text!!.isEmpty()) {
                passwordTextInputLayout.error = "Mật khẩu không được bỏ trống";
            } else {
                passwordTextInputLayout.error = null;
            }
        }

        loginBtn.setOnClickListener {
            if(accountTextInputLayout.error.isNullOrEmpty() && passwordTextInputLayout.error.isNullOrEmpty()) {
                auth.signInWithEmailAndPassword(
                    accountEdittext.text.toString(),
                    passwordEdittext.text.toString()
                )
                    .addOnCompleteListener(requireActivity()) { task ->
                        if (task.isSuccessful) {
                            UserInfo.getInstance().updateFromApi();
                            val intent: Intent =
                                Intent(requireActivity(), MainActivity::class.java);
                            startActivity(intent);
                        } else {
                            Snackbar.make(it, "Email hoặc mật khẩu không đúng", 1000).show();
                        }
                    }
            } else {
                Snackbar.make(it, "Vui lòng nhập thông tin đúng quy định", 1000).show();
            }
        }
        return view;
    }
}
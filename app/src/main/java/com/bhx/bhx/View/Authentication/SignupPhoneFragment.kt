package com.bhx.bhx.View.Authentication

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.content.ContextCompat
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import com.bhx.bhx.Constant.AuthConstanst
import com.bhx.bhx.Controller.AccountController
import com.bhx.bhx.Controller.RetrofitInstance
import com.bhx.bhx.Global.UserInfo
import com.bhx.bhx.Model.User
import com.bhx.bhx.R
import com.bhx.bhx.View.MainActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignupPhoneFragment: Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
    }

    lateinit var accountTextInputLayout: com.google.android.material.textfield.TextInputLayout;
    lateinit var accountEdittext: com.google.android.material.textfield.TextInputEditText;
    lateinit var passwordTextInputLayout: com.google.android.material.textfield.TextInputLayout;
    lateinit var passwordEdittext: com.google.android.material.textfield.TextInputEditText;
    lateinit var rePasswordTextInputLayout: com.google.android.material.textfield.TextInputLayout;
    lateinit var rePasswordEdittext: com.google.android.material.textfield.TextInputEditText;
    lateinit var signupBtn: Button;
    lateinit var backBtn: FloatingActionButton;
    lateinit var auth: FirebaseAuth;
    lateinit var apiUserInstance: AccountController;

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view: View = inflater.inflate(R.layout.fragment_signup_input, container, false);
        accountTextInputLayout = view.findViewById(R.id.accountTextInputLayout);
        accountEdittext = view.findViewById(R.id.accountEdittext);
        passwordTextInputLayout = view.findViewById(R.id.passwordTextInputLayout);
        passwordEdittext = view.findViewById(R.id.passwordEdittext);
        rePasswordTextInputLayout = view.findViewById(R.id.rePasswordTextInputLayout);
        rePasswordEdittext = view.findViewById(R.id.rePasswordEdittext);
        signupBtn = view.findViewById(R.id.signupBtn);
        backBtn = view.findViewById(R.id.backBtn);
        auth = FirebaseAuth.getInstance();
        apiUserInstance = RetrofitInstance.getInstance().create(AccountController::class.java);

        accountTextInputLayout.hint = "Số điện thoại";
        accountEdittext.inputType = InputType.TYPE_CLASS_NUMBER;
        accountTextInputLayout.startIconDrawable = ContextCompat.getDrawable(container?.context!!, R.drawable.ic_phone);

        passwordEdittext.doOnTextChanged { text, start, before, count ->
            if(text!!.length < AuthConstanst.MIN_PASSWORD_LENGTH) {
                passwordTextInputLayout.error = "Mật khẩu phải gồm ít nhất ${AuthConstanst.MIN_USERNAME_LENGTH} ký tự";
            }
            else {
                passwordTextInputLayout.error = null;
            }
        }

        rePasswordEdittext.doOnTextChanged { text, start, before, count ->
            if(text!!.toString() != passwordEdittext.text.toString()) {
                rePasswordTextInputLayout.error = "Mật khẩu không khớp";
            }
            else {
                rePasswordTextInputLayout.error = null;
            }
        }

        signupBtn.setOnClickListener {
            if(accountTextInputLayout.error.isNullOrEmpty() && passwordTextInputLayout.error.isNullOrEmpty() && rePasswordTextInputLayout.error.isNullOrEmpty()) {
                val dialog = ProgressDialog(context);
                dialog.create();
                dialog.setContentView(R.layout.custom_progress_dialog);
                dialog.setCancelable(false);
                dialog.show();
                auth.createUserWithEmailAndPassword(accountEdittext.text.toString() + AuthConstanst.FAKE_PHONE_DOMAIN, passwordEdittext.text.toString())
                    .addOnCompleteListener(requireActivity()) { task ->
                        if (task.isSuccessful) {
                            val user = auth.currentUser;
                            apiUserInstance.createUser(User(user!!.uid)).enqueue(object : Callback<User> {
                                override fun onResponse(
                                    call: Call<User>,
                                    response: Response<User>
                                ) {
                                    if (response.isSuccessful) {
                                        UserInfo.getInstance().updateFromApi();
                                        val intent: Intent = Intent(requireActivity(), MainActivity::class.java);
                                        startActivity(intent);
                                    }
                                }

                                override fun onFailure(call: Call<User>, t: Throwable) {
                                    dialog.dismiss();
                                    Snackbar.make(it, "Đã có lỗi xảy ra, vui lòng thử lại sau", 1000).show();
                                    activity?.finish();
                                }

                            })
                            dialog.dismiss();
                        } else {
                            dialog.dismiss();
                            Snackbar.make(it, "Đã có lỗi xảy ra, vui lòng thử lại sau", 1000).show();
                            activity?.finish();
                        }
                    }
            } else {
                Snackbar.make(it, "Vui lòng nhập thông tin đúng quy định", 1000).show();
            }
        }

        backBtn.setOnClickListener {
            parentFragmentManager.beginTransaction().replace(
                R.id.signup_fragment_placeholder,
                SignupMainFragment()
            ).commit();
        }

        return view;
    }
}
package com.bhx.bhx.View.Authentication

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.bhx.bhx.Constant.AuthConstanst
import com.bhx.bhx.Controller.AccountController
import com.bhx.bhx.Controller.RetrofitInstance
import com.bhx.bhx.Global.UserInfo
import com.bhx.bhx.Model.User
import com.bhx.bhx.R
import com.bhx.bhx.View.MainActivity
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.material.button.MaterialButton
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoginMainFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
    }

    lateinit var signupBtn: Button;
    lateinit var usernameBtn: MaterialButton;
    lateinit var phoneBtn: MaterialButton;
    lateinit var emailBtn: MaterialButton;
    lateinit var alterLoginBtn: Button;
    lateinit var apiUserInstance: AccountController;

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
        alterLoginBtn = view.findViewById(R.id.alterLoginBtn);
        apiUserInstance = RetrofitInstance.getInstance().create(AccountController::class.java);

        signupBtn.setOnClickListener {
            val intent: Intent = Intent(container?.context, SignupActivity::class.java);
            startActivity(intent);
        }

        val signInLauncher = registerForActivityResult(
            FirebaseAuthUIActivityResultContract()
        ) { result: FirebaseAuthUIAuthenticationResult? ->
            if (result?.resultCode == Activity.RESULT_OK) {
                val dialog = ProgressDialog(context);
                dialog.create();
                dialog.setContentView(R.layout.custom_progress_dialog);
                dialog.setCancelable(false);
                dialog.show();
                apiUserInstance.createUser(User(FirebaseAuth.getInstance().currentUser!!.uid)).enqueue(object :
                    Callback<User> {
                    override fun onResponse(
                        call: Call<User>,
                        response: Response<User>
                    ) {
                        UserInfo.getInstance().updateFromApi();
                        val intent: Intent = Intent(requireActivity(), MainActivity::class.java);
                        startActivity(intent);
                    }

                    override fun onFailure(call: Call<User>, t: Throwable) {
                        dialog.dismiss();
                        Snackbar.make(view, "Đã có lỗi xảy ra, vui lòng thử lại sau", 1000).show();
                        activity?.finish();
                    }

                })
                dialog.dismiss();
            } else {
                Snackbar.make(view, "Đã có lỗi xảy ra, vui lòng thử lại sau", 1000).show();
                activity?.finish();
            }
        }

    alterLoginBtn.setOnClickListener {
        val signInIntent = AuthUI.getInstance()
            .createSignInIntentBuilder()
            .setAvailableProviders(
                listOf(
                    AuthUI.IdpConfig.GoogleBuilder().setSignInOptions(GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestIdToken(getString(R.string.default_web_client_id))
                        .build()).build(),
                    AuthUI.IdpConfig.FacebookBuilder().build(),
                    AuthUI.IdpConfig.TwitterBuilder().build()
                )
            )
            .setIsSmartLockEnabled(false)
            .setAlwaysShowSignInMethodScreen(true)
            .build();

        signInLauncher.launch(signInIntent);
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
package com.bhx.bhx.View.AccountFragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.bhx.bhx.Global.UserInfo
import com.bhx.bhx.Model.User
import com.bhx.bhx.R
import com.bhx.bhx.View.AccountActivity.AccountInfoActivity
import com.bhx.bhx.View.AccountActivity.AccountOrderActivity
import com.bhx.bhx.View.AdminActivity
import com.bhx.bhx.View.Authentication.LoginActivity
import com.bhx.bhx.View.MainActivity
import com.google.firebase.auth.FirebaseAuth

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AccountFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AccountFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var accInfoBtn: Button? = null
    private var accOrdersBtn: Button? = null
    private var accAvatar: ImageView? = null

    private var userName: TextView? =null
    private var userAuthority: TextView? = null
    private var userEmail: TextView? = null

    private var btnLogout: Button? = null
    private var btnLogin: Button? = null
    private var btnAdmin: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        arguments?.let {
//            param1 = it.getString(ARG_PARAM1)
//            param2 = it.getString(ARG_PARAM2)
//        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnAdmin = getView()?.findViewById(R.id.Admin_btn)
        if(FirebaseAuth.getInstance().currentUser!=null && UserInfo.getInstance().getUser()?.authority=="ADMIN") {
            btnAdmin?.setOnClickListener {

                if(FirebaseAuth.getInstance().currentUser!=null) {
                    val intent = Intent(context, AdminActivity::class.java)
                    startActivity(intent)
                }
            }
        }
        else {
            btnAdmin?.visibility=View.GONE
        }


        btnLogout = getView()?.findViewById(R.id.Logout_btn)
        if(FirebaseAuth.getInstance().currentUser!=null)
            btnLogout?.setOnClickListener {
                FirebaseAuth.getInstance().signOut()

                if(FirebaseAuth.getInstance().currentUser==null) {
                    val intent = Intent(context, MainActivity::class.java)
                    startActivity(intent)
                }
            }
        else {
            btnLogout?.visibility=View.GONE
        }

        btnLogin = getView()?.findViewById(R.id.Login_btn)
        if(FirebaseAuth.getInstance().currentUser==null) {
            btnLogin?.setOnClickListener {
                val intent = Intent(context, LoginActivity::class.java)
                startActivity(intent)
            }
        }
        else {
            btnLogin?.visibility=View.GONE
        }

        accInfoBtn = getView()?.findViewById(R.id.acc_info_btn)
        accInfoBtn?.setOnClickListener{
            var intent = Intent(context, AccountInfoActivity::class.java)
            startActivity(intent)
        }

        accOrdersBtn = getView()?.findViewById(R.id.acc_orders_btn)
        accOrdersBtn?.setOnClickListener{
            var intent = Intent(context, AccountOrderActivity::class.java)
            startActivity(intent)
        }

        accAvatar = getView()?.findViewById(R.id.acc_avatar)
        accAvatar?.setImageResource(R.drawable.logo_login)

        var user: User? =null
        if(FirebaseAuth.getInstance().currentUser!=null)
            user = UserInfo.getInstance().getUser()

        userName = getView()?.findViewById(R.id.acc_full_name)
        userEmail = getView()?.findViewById(R.id.acc_email)
        userAuthority = getView()?.findViewById(R.id.acc_authority)

        if (user != null){
            userName?.text = user.fullname
            userEmail?.text = user.email
            userAuthority?.text = user.authority
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_account, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment AccountFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AccountFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
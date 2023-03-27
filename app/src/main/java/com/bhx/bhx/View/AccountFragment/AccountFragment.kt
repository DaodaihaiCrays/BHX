package com.bhx.bhx.View.AccountFragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import com.bhx.bhx.R
import com.bhx.bhx.View.AccountInfoActivity
import com.bhx.bhx.View.AccountOrderActivity

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        arguments?.let {
//            param1 = it.getString(ARG_PARAM1)
//            param2 = it.getString(ARG_PARAM2)
//        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
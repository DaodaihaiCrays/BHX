package com.bhx.bhx.View.Admin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.bhx.bhx.Controller.CategoryAdminController
import com.bhx.bhx.Controller.RetrofitInstance
import com.bhx.bhx.Controller.UserAdminController
import com.bhx.bhx.Model.AdminUser
import com.bhx.bhx.Model.Category
import com.bhx.bhx.R
import com.bhx.bhx.View.Admin.Adapter.AdminCategoryAdapter
import com.bhx.bhx.View.Admin.Adapter.AdminUserAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AdminAccountList.newInstance] factory method to
 * create an instance of this fragment.
 */
class AdminAccountList : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var lastVisiblePositionY: Int = 0
    private var edtSearch: EditText?? = null
    private var horizontal_scroll_view: HorizontalScrollView?? =null
    private var listUser : List<AdminUser> = listOf()//Để khi không cần load lại api thì vẫn còn Cate cũ
    private var listUserSearch : List<AdminUser> = listOf()
    private var btnSearch: Button??=null
    private var isApiCalled: Boolean = false//Kiểm tra nếu call rồi thì kh call nữa

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_admin_account_list, container, false)
        var tableLayout = view.findViewById<TableLayout>(R.id.table_layout)
        horizontal_scroll_view = view.findViewById(R.id.horizontal_scroll_view)
        edtSearch = view.findViewById(R.id.edtSearch)
        btnSearch = view.findViewById(R.id.btnSearch)


        var apiUserAdminInstance: UserAdminController = RetrofitInstance.getInstance().create(
            UserAdminController::class.java)

        apiUserAdminInstance.findAll().enqueue(object : Callback<List<AdminUser>> {
            override fun onResponse(
                call: Call<List<AdminUser>>,
                response: Response<List<AdminUser>>
            ) {

                if (response.isSuccessful) {
                    var listOfAdminUser: List<AdminUser>? = response.body()

                    if (listOfAdminUser == null) {
                        listOfAdminUser = listOf<AdminUser>()
                    }
                    listUser = listOfAdminUser
                    val myAdapter = AdminUserAdapter(requireContext(), listUser)

                    tableLayout.removeAllViews()

                    for (i in 0 until myAdapter.count) {
                        val rowView = myAdapter.getView(i, null, tableLayout)
                        tableLayout.addView(rowView)
                    }
                    isApiCalled = true
                }else{
                    Toast.makeText(container!!.context, "Thất bại", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<List<AdminUser>>, t: Throwable) {
                Toast.makeText(container!!.context,t.message, Toast.LENGTH_SHORT).show()
                println(t.message)
            }
        })

        btnSearch!!.setOnClickListener {
            val strSearch = edtSearch!!.text.toString()

            if(strSearch.length!=0) {
                RetrofitInstance.getInstance().create(UserAdminController::class.java).search(strSearch).enqueue(object :
                    Callback<List<AdminUser>> {
                    override fun onResponse(call: Call<List<AdminUser>>, response: Response<List<AdminUser>>) {
                        if (response.isSuccessful){
                            var listOfAdminUser: List<AdminUser>? = response.body()

                            if (listOfAdminUser == null) {
                                listOfAdminUser = listOf<AdminUser>()
                            }
                            listUserSearch = listOfAdminUser as List<AdminUser>
                            val myAdapter = AdminUserAdapter(requireContext(), listUserSearch)

                            tableLayout.removeAllViews()

                            for (i in 0 until myAdapter.count) {
                                val rowView = myAdapter.getView(i, null, tableLayout)
                                tableLayout.addView(rowView)
                            }
                        }else {
                            //Toast.makeText(context, "Fail",Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<List<AdminUser>>, t: Throwable) {
                        TODO("Not yet implemented")
                    }

                })
            }
            else {
                val fragmentManager = (context as AppCompatActivity).supportFragmentManager
                fragmentManager.beginTransaction().replace(
                    R.id.adminContainer,
                    AdminAccountList()
                ).commit()
            }
        }

        tableLayout.removeAllViews()


        // Khi không cần gọi api thì lấy thẳng cái cũ
        val myAdapter = AdminUserAdapter(requireContext(), listUser)
        for (i in 0 until myAdapter.count) {
            val rowView = myAdapter.getView(i, null, tableLayout)
            tableLayout.addView(rowView)
        }

        return view
    }

}
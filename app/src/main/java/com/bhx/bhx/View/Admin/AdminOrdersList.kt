package com.bhx.bhx.View.Admin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.bhx.bhx.Controller.OrderAdminController
import com.bhx.bhx.Controller.ProductAdminController
import com.bhx.bhx.Controller.RetrofitInstance
import com.bhx.bhx.Model.AdminOrder
import com.bhx.bhx.Model.AdminProduct
import com.bhx.bhx.R
import com.bhx.bhx.View.Admin.Adapter.AdminOrderAdapter
import com.bhx.bhx.View.Admin.Adapter.AdminProductAdapter
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
 * Use the [AdminOrdersList.newInstance] factory method to
 * create an instance of this fragment.
 */
class AdminOrdersList : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var edtSearch: EditText?? = null
    private var horizontal_scroll_view: HorizontalScrollView?? =null
    private var isApiCalled: Boolean = false
    private var listAdminOrder : List<AdminOrder> = listOf()
    private var listAdminOrderSearch : List<AdminOrder> = listOf()
    private var btnSearch: Button??=null

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
        val view: View = inflater.inflate(R.layout.fragment_admin_orders_list, container, false)
        var tableLayout = view.findViewById<TableLayout>(R.id.table_layout)
        horizontal_scroll_view = view.findViewById(R.id.horizontal_scroll_view)
        edtSearch = view.findViewById(R.id.edtSearch)
        btnSearch = view.findViewById(R.id.btnSearch)

        if(!isApiCalled) {
            var apiOrderAdminInstance: OrderAdminController = RetrofitInstance.getInstance().create(
                OrderAdminController::class.java)

            apiOrderAdminInstance.findAll().enqueue(object : Callback<List<AdminOrder>> {
                override fun onResponse(
                    call: Call<List<AdminOrder>>,
                    response: Response<List<AdminOrder>>
                ) {

                    if (response.isSuccessful) {
                        var listOfAdminOrder: List<AdminOrder>? = response.body()

                        if (listOfAdminOrder == null) {
                            listOfAdminOrder = listOf<AdminOrder>()
                        }
                        listAdminOrder = listOfAdminOrder
                        val myAdapter = AdminOrderAdapter(requireContext(), listAdminOrder)

                        tableLayout.removeAllViews()

                        for (i in 0 until myAdapter.count) {
                            val rowView = myAdapter.getView(i, null, tableLayout)
                            tableLayout.addView(rowView)
                        }
                        isApiCalled = true
                    }else{
                        Toast.makeText(container!!.context, "Fail", Toast.LENGTH_SHORT).show()
                    }
                }
                override fun onFailure(call: Call<List<AdminOrder>>, t: Throwable) {
                    Toast.makeText(container!!.context,t.message, Toast.LENGTH_SHORT).show()
                    println(t.message)
                }
            })
        }

        btnSearch!!.setOnClickListener {
            val strSearch = edtSearch!!.text.toString()
            if(strSearch.length!=0) {
                RetrofitInstance.getInstance().create(OrderAdminController::class.java).search(strSearch).enqueue(object :
                    Callback<List<AdminOrder>> {
                    override fun onResponse(call: Call<List<AdminOrder>>, response: Response<List<AdminOrder>>) {
                        if (response.isSuccessful){
                            var listOfAdminOrder: List<AdminOrder>? = response.body()

                            if (listOfAdminOrder == null) {
                                listOfAdminOrder = listOf<AdminOrder>()
                            }
                            listAdminOrder = listOfAdminOrder as List<AdminOrder>
                            val myAdapter = AdminOrderAdapter(requireContext(), listAdminOrder)

                            tableLayout.removeAllViews()

                            for (i in 0 until myAdapter.count) {
                                val rowView = myAdapter.getView(i, null, tableLayout)
                                tableLayout.addView(rowView)
                            }
                        }else {
                            Toast.makeText(context, "Tìm kiếm thất bại", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<List<AdminOrder>>, t: Throwable) {
                        Toast.makeText(context, "Tìm kiếm thất bại", Toast.LENGTH_SHORT).show()
                    }

                })
            }
            else {
                val fragmentManager = (context as AppCompatActivity).supportFragmentManager
                fragmentManager.beginTransaction().replace(
                    R.id.adminContainer,
                    AdminOrdersList()
                ).commit()
            }
        }

        tableLayout.removeAllViews()


        // Khi không cần gọi api thì lấy thẳng cái cũ
        val myAdapter = AdminOrderAdapter(requireContext(), listAdminOrder)
        for (i in 0 until myAdapter.count) {
            val rowView = myAdapter.getView(i, null, tableLayout)
            tableLayout.addView(rowView)
        }

        return view
    }

}
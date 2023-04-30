package com.bhx.bhx.View.Admin

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TableLayout
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bhx.bhx.Controller.CategoryAdminController
import com.bhx.bhx.Controller.RetrofitInstance
import com.bhx.bhx.Model.Category
import com.bhx.bhx.Model.ReviewCategory
import com.bhx.bhx.R
import com.bhx.bhx.View.Admin.Adapter.AdminCategoryAdapter
import com.bhx.bhx.View.HomeFragment.ProductAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AdminCategoryList.newInstance] factory method to
 * create an instance of this fragment.
 */
class AdminCategoryList : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var adapter: AdminCategoryAdapter

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

        val view: View = inflater.inflate(R.layout.fragment_admin_category_list, container, false)

        val tableLayout = view.findViewById<TableLayout>(R.id.table_layout)

        var apiCategoryAdminInstance: CategoryAdminController = RetrofitInstance.getInstance().create(
            CategoryAdminController::class.java)

        apiCategoryAdminInstance.findAll().enqueue(object : Callback<List<Category>> {
            override fun onResponse(
                call: Call<List<Category>>,
                response: Response<List<Category>>
            ) {
                if (response.isSuccessful) {
                    var listOfCategory: List<Category>? = response.body()

                    if (listOfCategory == null) {
                        listOfCategory = listOf<Category>()
                    }

                    Log.i("test", listOfCategory.toString())
                    val myAdapter = AdminCategoryAdapter(requireContext(), listOfCategory)

                    tableLayout.removeAllViews()
                    var rowHeader = listOf<String>("Tên", "Mô tả", "")


                    for (i in 0 until myAdapter.count) {
                        val rowView = myAdapter.getView(i, null, tableLayout)
                        tableLayout.addView(rowView)
                    }

                }else{
                    Toast.makeText(container!!.context, "Fail",Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<List<Category>>, t: Throwable) {
                Toast.makeText(container!!.context,t.message,Toast.LENGTH_SHORT).show()
                println(t.message)
            }
        })

        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment AdminCategoryList.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AdminCategoryList().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
package com.bhx.bhx.View.Admin

import android.os.Bundle
import android.os.Parcelable
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bhx.bhx.Controller.CategoryAdminController
import com.bhx.bhx.Controller.ProductController
import com.bhx.bhx.Controller.RetrofitInstance
import com.bhx.bhx.Model.Category
import com.bhx.bhx.Model.ReviewCategory
import com.bhx.bhx.R
import com.bhx.bhx.View.Admin.Adapter.AdminCategoryAdapter
import com.bhx.bhx.View.HomeFragment.ProductAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputLayout
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
    private var lastVisiblePositionY: Int = 0
    private var edtSearch: EditText?? = null
    private var horizontal_scroll_view:HorizontalScrollView?? =null
    private var isApiCalled: Boolean = false//Kiểm tra nếu call rồi thì kh call nữa
    private var listCategory : List<Category> = listOf()//Để khi không cần load lại api thì vẫn còn Cate cũ
    private var listCategorySearch : List<Category> = listOf()//Để lưu lại cate khi search
    private var btnSearch:Button??=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onPause() {
        super.onPause()
        //Lưu lại vị trí màn hình trước khi sang Fragment khác
        lastVisiblePositionY = horizontal_scroll_view!!.scrollY
    }

    override fun onResume() {
        super.onResume()
        //Lấy lại vị trí màn hình như trước khi sang Fragment khác
        horizontal_scroll_view!!.post { horizontal_scroll_view!!.scrollTo(0, lastVisiblePositionY) }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_admin_category_list, container, false)
        var tableLayout = view.findViewById<TableLayout>(R.id.table_layout)
        horizontal_scroll_view = view.findViewById(R.id.horizontal_scroll_view)
        edtSearch = view.findViewById(R.id.edtSearch)
        btnSearch = view.findViewById(R.id.btnSearch)
        val btnAdd = view.findViewById<FloatingActionButton>(R.id.btnAdd)


        btnAdd.setOnClickListener {
            val fragmentManager = (context as AppCompatActivity).supportFragmentManager
            fragmentManager.beginTransaction().replace(
                R.id.adminContainer,
                AdminCreateCategory()
            ).commit()
        }

        if(!isApiCalled) {
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
                        listCategory = listOfCategory
                        val myAdapter = AdminCategoryAdapter(requireContext(), listCategory)

                        tableLayout.removeAllViews()

                        for (i in 0 until myAdapter.count) {
                            val rowView = myAdapter.getView(i, null, tableLayout)
                            tableLayout.addView(rowView)
                        }
                        isApiCalled = true
                    }else{
                        Toast.makeText(container!!.context, "Fail",Toast.LENGTH_SHORT).show()
                    }
                }
                override fun onFailure(call: Call<List<Category>>, t: Throwable) {
                    Toast.makeText(container!!.context,t.message,Toast.LENGTH_SHORT).show()
                    println(t.message)
                }
            })
        }

        btnSearch!!.setOnClickListener {
            val strSearch = edtSearch!!.text.toString()

            if(strSearch.length!=0) {
                    RetrofitInstance.getInstance().create(CategoryAdminController::class.java).findCateOfSearch(strSearch).enqueue(object : Callback<List<Category>> {
                        override fun onResponse(call: Call<List<Category>>, response: Response<List<Category>>) {
                            if (response.isSuccessful){
                                var listOfCategory: List<Category>? = response.body()

                                if (listOfCategory == null) {
                                    listOfCategory = listOf<Category>()
                                }
                                listCategorySearch = listOfCategory as List<Category>
                                val myAdapter = AdminCategoryAdapter(requireContext(), listCategorySearch)

                                tableLayout.removeAllViews()

                                for (i in 0 until myAdapter.count) {
                                    val rowView = myAdapter.getView(i, null, tableLayout)
                                    tableLayout.addView(rowView)
                                }
                            }else {
                                Log.i("test","fail1")
                                //Toast.makeText(context, "Fail",Toast.LENGTH_SHORT).show()
                            }
                        }

                        override fun onFailure(call: Call<List<Category>>, t: Throwable) {
                            TODO("Not yet implemented")
                            Log.i("test","fail2")
                        }

                    })
                }
                else {
                    val fragmentManager = (context as AppCompatActivity).supportFragmentManager
                    fragmentManager.beginTransaction().replace(
                        R.id.adminContainer,
                        AdminCategoryList()
                    ).commit()
                }
        }

//        autoCompleteTextView!!.addTextChangedListener(object : TextWatcher {
//            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
//            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
//            override fun afterTextChanged(s: Editable) {
//                val strSearch = s.toString()
//
//                println(strSearch)
//
//                if(strSearch.length!=0) {
//                    RetrofitInstance.getInstance().create(CategoryAdminController::class.java).findCateOfSearch(strSearch).enqueue(object : Callback<List<Category>> {
//                        override fun onResponse(call: Call<List<Category>>, response: Response<List<Category>>) {
//                            if (response.isSuccessful){
//                                var listOfCategory: List<Category>? = response.body()
//
//                                if (listOfCategory == null) {
//                                    listOfCategory = listOf<Category>()
//                                }
//                                listCategorySearch = listOfCategory as List<Category>
//                                val myAdapter = AdminCategoryAdapter(requireContext(), listCategorySearch)
//
//                                tableLayout.removeAllViews()
//
//                                for (i in 0 until myAdapter.count) {
//                                    val rowView = myAdapter.getView(i, null, tableLayout)
//                                    tableLayout.addView(rowView)
//                                }
//                            }else {
//                                Log.i("test","fail1")
//                                //Toast.makeText(context, "Fail",Toast.LENGTH_SHORT).show()
//                            }
//                        }
//
//                        override fun onFailure(call: Call<List<Category>>, t: Throwable) {
//                            TODO("Not yet implemented")
//                            Log.i("test","fail2")
//                        }
//
//                    })
//                }
//                else {
//                    val fragmentManager = (context as AppCompatActivity).supportFragmentManager
//                    fragmentManager.beginTransaction().replace(
//                        R.id.adminContainer,
//                        AdminCategoryList()
//                    ).commit()
//                }
//
//            }
//        })

        tableLayout.removeAllViews()
//                    var rowHeader = listOf<String>("Tên", "Mô tả", "")
//                    tableLayout.addView(rowHeader)

        // Khi không cần gọi api thì lấy thẳng cái cũ
        val myAdapter = AdminCategoryAdapter(requireContext(), listCategory)
        for (i in 0 until myAdapter.count) {
            val rowView = myAdapter.getView(i, null, tableLayout)
            tableLayout.addView(rowView)
        }

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
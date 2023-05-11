package com.bhx.bhx.View.Admin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.bhx.bhx.Controller.PromotionsAdminController
import com.bhx.bhx.Controller.RetrofitInstance
import com.bhx.bhx.Model.Category
import com.bhx.bhx.Model.Promotion
import com.bhx.bhx.R
import com.bhx.bhx.View.Admin.Adapter.AdminCategoryAdapter
import com.bhx.bhx.View.Admin.Adapter.AdminPromotionAdapter
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
 * Use the [AdminPromotionsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AdminPromotionsFragment() : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var lastVisiblePositionY: Int = 0
    private var edtSearch: EditText?? = null
    private var horizontal_scroll_view: HorizontalScrollView?? =null
    private var isApiCalled: Boolean = false//Kiểm tra nếu call rồi thì kh call nữa
    private var listPromotions : List<Promotion> = listOf()//Để khi không cần load lại api thì vẫn còn Cate cũ
    private var listPromotionsSearch : List<Promotion> = listOf()//Để lưu lại cate khi search
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
        val view: View = inflater.inflate(R.layout.fragment_admin_promotions, container, false)
        var tableLayout = view.findViewById<TableLayout>(R.id.table_layout)
        horizontal_scroll_view = view.findViewById(R.id.horizontal_scroll_view)
        edtSearch = view.findViewById(R.id.edtSearch)
        btnSearch = view.findViewById(R.id.btnSearch)
        val btnAdd = view.findViewById<FloatingActionButton>(R.id.btnAdd)


        btnAdd.setOnClickListener {
            val fragmentManager = (context as AppCompatActivity).supportFragmentManager
            fragmentManager.beginTransaction().replace(
                R.id.adminContainer,
                AdminCreatePromotions()
            ).commit()
        }

        if(!isApiCalled) {
            var apiPromotionAdminInstance: PromotionsAdminController = RetrofitInstance.getInstance().create(
                PromotionsAdminController::class.java)

            apiPromotionAdminInstance.findAll().enqueue(object : Callback<List<Promotion>> {
                override fun onResponse(
                    call: Call<List<Promotion>>,
                    response: Response<List<Promotion>>
                ) {

                    if (response.isSuccessful) {
                        var listOfPromotion: List<Promotion>? = response.body()

                        if (listOfPromotion == null) {
                            listOfPromotion = listOf<Promotion>()
                        }
                        listPromotions = listOfPromotion
                        val myAdapter = AdminPromotionAdapter(requireContext(), listPromotions)

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
                override fun onFailure(call: Call<List<Promotion>>, t: Throwable) {
                    Toast.makeText(container!!.context,t.message, Toast.LENGTH_SHORT).show()
                    println(t.message)
                }
            })
        }

        btnSearch!!.setOnClickListener {
            val strSearch = edtSearch!!.text.toString()

            if(strSearch.length!=0) {
                RetrofitInstance.getInstance().create(PromotionsAdminController::class.java).search(strSearch).enqueue(object :
                    Callback<List<Promotion>> {
                    override fun onResponse(call: Call<List<Promotion>>, response: Response<List<Promotion>>) {
                        if (response.isSuccessful){
                            var listOfPromotion: List<Promotion>? = response.body()

                            if (listOfPromotion == null) {
                                listOfPromotion = listOf<Promotion>()
                            }
                            listPromotionsSearch = listOfPromotion as List<Promotion>
                            val myAdapter = AdminPromotionAdapter(requireContext(), listPromotionsSearch)

                            tableLayout.removeAllViews()

                            for (i in 0 until myAdapter.count) {
                                val rowView = myAdapter.getView(i, null, tableLayout)
                                tableLayout.addView(rowView)
                            }
                        }else {
                            //Toast.makeText(context, "Fail",Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<List<Promotion>>, t: Throwable) {
                        TODO("Not yet implemented")
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

        tableLayout.removeAllViews()


        // Khi không cần gọi api thì lấy thẳng cái cũ
        val myAdapter = AdminPromotionAdapter(requireContext(), listPromotions)
        for (i in 0 until myAdapter.count) {
            val rowView = myAdapter.getView(i, null, tableLayout)
            tableLayout.addView(rowView)
        }

        return view
    }

}
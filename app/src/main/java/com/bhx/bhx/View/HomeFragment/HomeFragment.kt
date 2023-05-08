package com.bhx.bhx.View.HomeFragment

import android.app.ProgressDialog
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import android.widget.AutoCompleteTextView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bhx.bhx.Controller.CategoryController
import com.bhx.bhx.Controller.RetrofitInstance
import com.bhx.bhx.Global.Search
import com.bhx.bhx.Model.ReviewCategory
import com.bhx.bhx.Model.Product
import com.bhx.bhx.R
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.ZonedDateTime
import java.util.Locale.Category

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    private lateinit var revProducts: RecyclerView
    private lateinit var categories: MutableList<ReviewCategory>
    private lateinit var adapter: ProductAdapter
    private var isApiCalled: Boolean = false
    private var recyclerViewState: Parcelable? = null
    private var listReviewCategory : MutableList<ReviewCategory> = mutableListOf()

    lateinit var apiCategoryInstance: CategoryController

    override fun onPause() {
        super.onPause()
        recyclerViewState = revProducts.layoutManager?.onSaveInstanceState()

    }

    override fun onResume() {
        super.onResume()
        if (recyclerViewState != null) {
            revProducts.layoutManager?.onRestoreInstanceState(recyclerViewState)
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view: View = inflater.inflate(R.layout.fragment_home, container, false)
        //initDateCategories()
        revProducts = view.findViewById(R.id.revProduct)

        Search.edtSearch.setText("")

        if (!isApiCalled) {
            val dialog = ProgressDialog(context)
            dialog.create()
            dialog.setContentView(R.layout.custom_progress_dialog)
            dialog.setCancelable(false) //outside touch doesn't dismiss you
            dialog.show()
            val displayMetrics = context?.resources?.displayMetrics
            val screenWidth = displayMetrics?.widthPixels
            val width = (screenWidth?.times(0.5))?.toInt()
            if (width != null) {
                dialog.window?.setLayout(width, WindowManager.LayoutParams.WRAP_CONTENT)
            }

            apiCategoryInstance = RetrofitInstance.getInstance().create(CategoryController::class.java)
            apiCategoryInstance.getCategoryProduct().enqueue(object : Callback<List<ReviewCategory>> {
                override fun onResponse(
                    call: Call<List<ReviewCategory>>,
                    response: Response<List<ReviewCategory>>
                ) {
                    if (response.isSuccessful) {
                        val data = response.body()

                        dialog.dismiss()

                        listReviewCategory = data as MutableList<ReviewCategory>

                        adapter = ProductAdapter(listReviewCategory, container!!.context)
                        revProducts.layoutManager = LinearLayoutManager(container!!.context, RecyclerView.VERTICAL, false)
                        revProducts.adapter = adapter
                        isApiCalled = true
                    } else {
                        Toast.makeText(container!!.context, "Fail", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<List<ReviewCategory>>, t: Throwable) {
                    Toast.makeText(container!!.context, t.message, Toast.LENGTH_SHORT).show()
                    println(t.message)
                }
            })
        }
        adapter = ProductAdapter(listReviewCategory, container!!.context)
        revProducts.layoutManager = LinearLayoutManager(container!!.context, RecyclerView.VERTICAL, false)
        revProducts.adapter = adapter

        return view
    }

}
package com.bhx.bhx.View.ProductOfCateFragment

import android.app.ProgressDialog
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bhx.bhx.Controller.CategoryController
import com.bhx.bhx.Controller.RetrofitInstance
import com.bhx.bhx.Model.Product
import com.bhx.bhx.Model.ReviewCategory
import com.bhx.bhx.R
import com.bhx.bhx.View.HomeFragment.HomeFragment
import com.bhx.bhx.View.HomeFragment.ListProductAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ProductOfCateFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProductOfCateFragment(private val category: ReviewCategory) : Fragment() {
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

    var btnBack: Button?=null
    var tvCate: TextView?=null
    var revProductOfCate: RecyclerView?=null
    private lateinit var adapter:ListProductAdapter
    lateinit var apiCategoryInstance: CategoryController
    private var isApiCalled: Boolean = false
    private var recyclerViewState: Parcelable? = null
    private var listProduct : List<Product> = listOf()

    override fun onPause() {
        super.onPause()
        recyclerViewState = revProductOfCate!!.layoutManager?.onSaveInstanceState()

    }

    override fun onResume() {
        super.onResume()
        if (recyclerViewState != null) {
            revProductOfCate!!.layoutManager?.onRestoreInstanceState(recyclerViewState)
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view: View = inflater.inflate(R.layout.fragment_product_of_cate, container, false)

        btnBack = view.findViewById(R.id.btnBack)
        tvCate = view.findViewById(R.id.tvCate)
        revProductOfCate = view.findViewById(R.id.revProductOfCate)

        tvCate!!.setText(category.name)

        btnBack!!.setOnClickListener {
//            val fragmentManager = (context as AppCompatActivity).supportFragmentManager
//            fragmentManager.beginTransaction().replace(
//                R.id.container,
//                HomeFragment()
//            ).commit()
            Log.i("test","nhan quay lai")
            parentFragmentManager.popBackStack()
        }

        if(!isApiCalled) {
            apiCategoryInstance = RetrofitInstance.getInstance().create(CategoryController::class.java)

            val categoryId: Int = category.id
            val api = apiCategoryInstance.getAllProductsOfCate(categoryId)

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

            api.enqueue(object : Callback<List<Product>> {
                override fun onResponse(
                    call: Call<List<Product>>,
                    response: Response<List<Product>>
                ) {

                    if (response.isSuccessful) {
                        val data = response.body()
                        dialog.dismiss()
                        listProduct = data as List<Product>
                        adapter = ListProductAdapter(listProduct, container!!.context);
                        revProductOfCate!!.layoutManager = GridLayoutManager(context, 3, RecyclerView.VERTICAL, false)
                        revProductOfCate!!.adapter= adapter
                        isApiCalled = true
                    }else{
                        Toast.makeText(container!!.context, "Fail",Toast.LENGTH_SHORT).show()
                    }
                }
                override fun onFailure(call: Call<List<Product>>, t: Throwable) {
                    Toast.makeText(container!!.context,t.message,Toast.LENGTH_SHORT).show()
                    println(t.message)
                }
            })
        }

        adapter = ListProductAdapter(listProduct, container!!.context);
        revProductOfCate!!.layoutManager = GridLayoutManager(context, 3, RecyclerView.VERTICAL, false)
        revProductOfCate!!.adapter= adapter

        return view
    }
}
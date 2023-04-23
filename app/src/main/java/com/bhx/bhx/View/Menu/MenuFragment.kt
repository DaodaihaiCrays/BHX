package com.bhx.bhx.View.Menu

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
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bhx.bhx.Controller.CategoryController
import com.bhx.bhx.Controller.RetrofitInstance
import com.bhx.bhx.Global.Search
import com.bhx.bhx.Model.ReviewCategory
import com.bhx.bhx.R
import com.bhx.bhx.View.HomeFragment.HomeFragment
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
 * Use the [MenuFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MenuFragment : Fragment() {
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

    private lateinit var revListCategory: RecyclerView
    private lateinit var categories: MutableList<ReviewCategory>
    private lateinit var adapter: NameCategoryAdapter
    private lateinit var btnBack: Button

    private var isApiCalled: Boolean = false
    private var recyclerViewState: Parcelable? = null
    private var listReviewCategory : List<ReviewCategory> = listOf()

    override fun onPause() {
        super.onPause()
        recyclerViewState = revListCategory.layoutManager?.onSaveInstanceState()

    }

    override fun onResume() {
        super.onResume()
        if (recyclerViewState != null) {
            revListCategory.layoutManager?.onRestoreInstanceState(recyclerViewState)
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view: View = inflater.inflate(R.layout.fragment_menu, container, false)

        revListCategory = view.findViewById(R.id.revListCategory)
        btnBack = view.findViewById(R.id.btnBack)



        btnBack!!.setOnClickListener {
            Search.edtSearch.setText("")

//            val fragmentManager = (context as AppCompatActivity).supportFragmentManager
//            fragmentManager.beginTransaction().replace(
//                R.id.container,
//                HomeFragment()
//            ).addToBackStack(null).commit()

            parentFragmentManager.popBackStack()
        }

        if(!isApiCalled) {

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

            RetrofitInstance.getInstance().create(CategoryController::class.java).getCategoryProduct().enqueue(object :
                Callback<List<ReviewCategory>> {
                override fun onResponse(
                    call: Call<List<ReviewCategory>>,
                    response: Response<List<ReviewCategory>>
                ) {

                    if (response.isSuccessful) {
                        val data = response.body()

                        dialog.dismiss()
                        listReviewCategory = data as List<ReviewCategory>
                        adapter = NameCategoryAdapter(listReviewCategory, container!!.context);
                        revListCategory.layoutManager = LinearLayoutManager(container!!.context, RecyclerView.VERTICAL, false)
                        revListCategory.adapter= adapter
                        isApiCalled = true
                    }else{
                        Toast.makeText(container!!.context, "Fail", Toast.LENGTH_SHORT).show()
                    }
                }
                override fun onFailure(call: Call<List<ReviewCategory>>, t: Throwable) {
                    Toast.makeText(container!!.context,t.message, Toast.LENGTH_SHORT).show()
                    println(t.message)
                }
            })
        }

        adapter = NameCategoryAdapter(listReviewCategory, container!!.context);
        revListCategory.layoutManager = LinearLayoutManager(container!!.context, RecyclerView.VERTICAL, false)
        revListCategory.adapter= adapter


        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment MenuFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MenuFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
package com.bhx.bhx.View.HomeFragment

import android.app.ProgressDialog
import android.os.Bundle
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

    lateinit var apiCategoryInstance: CategoryController



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view: View = inflater.inflate(R.layout.fragment_home, container, false)
        //initDateCategories()
        revProducts = view.findViewById(R.id.revProduct)

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

                    Log.i("test","fdbgftb")

                    adapter = ProductAdapter(data as List<ReviewCategory>, container!!.context);
                    revProducts.layoutManager = LinearLayoutManager(container!!.context, RecyclerView.VERTICAL, false)
                    revProducts.adapter= adapter
                }else{
                    Toast.makeText(container!!.context, "Fail",Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<List<ReviewCategory>>, t: Throwable) {
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
         * @return A new instance of fragment HomeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
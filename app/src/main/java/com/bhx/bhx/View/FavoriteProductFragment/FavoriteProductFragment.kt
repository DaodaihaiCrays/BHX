package com.bhx.bhx.View.FavoriteProductFragment

import android.app.ProgressDialog
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bhx.bhx.Controller.CategoryController
import com.bhx.bhx.Controller.ProductController
import com.bhx.bhx.Controller.RetrofitInstance
import com.bhx.bhx.Global.CheckChangeFavorite
import com.bhx.bhx.Global.UserInfo
import com.bhx.bhx.Model.Product
import com.bhx.bhx.Model.ReviewCategory
import com.bhx.bhx.R
import com.bhx.bhx.View.HomeFragment.ListProductAdapter
import com.bhx.bhx.View.HomeFragment.ProductAdapter
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FavoriteProductFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FavoriteProductFragment : Fragment() {
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
    private lateinit var adapter: ListProductAdapter
    private var isApiCalled: Boolean = false
    private var recyclerViewState: Parcelable? = null
    private var listFavoriteProduct : MutableList<Product> = mutableListOf()


    override fun onPause() {
        super.onPause()
        if(FirebaseAuth.getInstance().currentUser!=null) {
            recyclerViewState = revProducts.layoutManager?.onSaveInstanceState()
        }
    }

    override fun onResume() {
        super.onResume()

        if(FirebaseAuth.getInstance().currentUser!=null) {
            revProducts.layoutManager?.onRestoreInstanceState(recyclerViewState)
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view: View = inflater.inflate(R.layout.fragment_favorite_product, container, false)



        if(FirebaseAuth.getInstance().currentUser!=null) {
            revProducts = view.findViewById(R.id.revListFavoriteProduct)

            if (!isApiCalled) {
                callApi()
                //Log.i("test","id= " + UserInfo.getInstance().getUser()!!.id)
            }

            adapter = ListProductAdapter(listFavoriteProduct,container!!.context)
            CheckChangeFavorite.adapterFavorite=adapter
            revProducts.layoutManager =  GridLayoutManager(context, 3, RecyclerView.VERTICAL, false)
            revProducts.adapter = adapter
        }
        else Toast.makeText(context,"Đăng nhập để xem",Toast.LENGTH_SHORT).show()
        return view
    }

    fun callApi() {
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

        RetrofitInstance.getInstance().create(ProductController::class.java).getFavoriteProduct(
            UserInfo.getInstance().getUser()!!.id).enqueue(object : Callback<List<Product>> {
            override fun onResponse(
                call: Call<List<Product>>,
                response: Response<List<Product>>
            ) {
                if (response.isSuccessful) {
                    val data = response.body()

                    dialog.dismiss()

                    listFavoriteProduct = data as MutableList<Product>

                    adapter = ListProductAdapter(listFavoriteProduct,context!!)
                    CheckChangeFavorite.adapterFavorite=adapter
                    revProducts.layoutManager =  GridLayoutManager(context, 3, RecyclerView.VERTICAL, false)
                    revProducts.adapter = adapter
                    isApiCalled = true
                } else {
                    Toast.makeText(context, "Fail", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<Product>>, t: Throwable) {
                Toast.makeText(context, t.message, Toast.LENGTH_SHORT).show()
                println(t.message)
            }
        })
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment NotificationFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FavoriteProductFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
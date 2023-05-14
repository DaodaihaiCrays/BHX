package com.bhx.bhx.View.Promotions

import android.app.ProgressDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bhx.bhx.Controller.ProductController
import com.bhx.bhx.Controller.RetrofitInstance
import com.bhx.bhx.Model.PromotionsWithProducts
import com.bhx.bhx.R
import com.google.android.material.snackbar.Snackbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SaleFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SaleFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    lateinit var salePageItemRecyclerView: RecyclerView;

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
        val view: View =  inflater.inflate(R.layout.fragment_sale, container, false);
        salePageItemRecyclerView = view.findViewById(R.id.salePageItemRecyclerView);

        val dialog = ProgressDialog(context);
        dialog.create();
        dialog.setContentView(R.layout.custom_progress_dialog);
        dialog.setCancelable(false);
        dialog.show();

        RetrofitInstance.getInstance().create(ProductController::class.java).getAllProductInActivePromotion().enqueue(object :
            Callback<List<PromotionsWithProducts>> {
            override fun onResponse(
                call: Call<List<PromotionsWithProducts>>,
                response: Response<List<PromotionsWithProducts>>
            ) {
                val adapter = SalePageItemAdapter(response.body()!!, view.context);
                salePageItemRecyclerView.adapter = adapter;
                salePageItemRecyclerView.layoutManager = LinearLayoutManager(view.context, RecyclerView.VERTICAL, false);

                dialog.dismiss();
            }

            override fun onFailure(call: Call<List<PromotionsWithProducts>>, t: Throwable) {
                Snackbar.make(
                    view,
                    "Đã có lỗi xảy ra, vui lòng thử lại sau",
                    1000
                ).show();
                dialog.dismiss();
            }
        })

        return view;
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SaleFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SaleFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
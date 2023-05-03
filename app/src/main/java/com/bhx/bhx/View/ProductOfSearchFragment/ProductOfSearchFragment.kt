package com.bhx.bhx.View.ProductOfSearchFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bhx.bhx.Global.Search
import com.bhx.bhx.Model.Product
import com.bhx.bhx.R
import com.bhx.bhx.View.DetailProduct.PropertiesAdapter
import com.bhx.bhx.View.HomeFragment.HomeFragment
import com.bhx.bhx.View.HomeFragment.ListProductAdapter
import com.bhx.bhx.View.MainActivity

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ProductOfSearchFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProductOfSearchFragment(private val listProduct: List<Product>) : Fragment() {
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

    lateinit var revListProduct: RecyclerView;
    lateinit var adapter: ListProductAdapter
    lateinit var btnBack: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        var view: View = inflater.inflate(R.layout.fragment_product_of_search, container, false)

        revListProduct = view.findViewById(R.id.revListProduct)
        btnBack = view.findViewById(R.id.btnBack)

        btnBack!!.setOnClickListener {
            Search.edtSearch.setText("")
            parentFragmentManager.popBackStack()
        }

        adapter = ListProductAdapter(listProduct,container!!.context)

        revListProduct.adapter=adapter
        revListProduct.layoutManager = GridLayoutManager(context, 3, RecyclerView.VERTICAL, false)

        return view
    }

}
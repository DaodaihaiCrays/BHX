package com.bhx.bhx.View.DetailProduct

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.DataBindingUtil.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bhx.bhx.Model.Product
import com.bhx.bhx.R
import com.bhx.bhx.View.HomeFragment.HomeFragment
import com.bhx.bhx.databinding.FragmentDetailProductBinding

class DetailProductFragment(private val product: Product) : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    lateinit var revProperties: RecyclerView;
    lateinit var adapter: PropertiesAdapter
    lateinit var btnBack: Button

    val lable = listOf<String>("A", "B", "C", "D")
    val value = listOf<String>(
        "var view: View = inflater.inflate(R.layout.fragment_detail_product, container, false)",
        "var view: View = inflater.inflate(R.layout.fragment_detail_product, container, false)",
        "3",
        "var view: View = inflater.inflate(R.layout.fragment_detail_product, container, false)"
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view: View = inflater.inflate(R.layout.fragment_detail_product, container, false)
        revProperties = view.findViewById(R.id.revProperties)
        btnBack = view.findViewById(R.id.btnBack)

        //adapter = PropertiesAdapter(product?.attribute_label, product?.attribute_value)
        adapter = PropertiesAdapter(lable, value)
        revProperties.adapter = adapter
        revProperties.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)

        btnBack!!.setOnClickListener {
            val fragmentManager = (context as AppCompatActivity).supportFragmentManager
            fragmentManager.beginTransaction().replace(
                R.id.container,
                HomeFragment()
            ).commit()
        }

        return view
    }


}
package com.bhx.bhx.View.Admin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TableLayout
import com.bhx.bhx.Model.AdminOrder
import com.bhx.bhx.Model.AdminOrderItem
import com.bhx.bhx.R
import com.bhx.bhx.View.Admin.Adapter.AdminOrderAdapter
import com.bhx.bhx.View.Admin.Adapter.AdminOrderItemAdapter

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AdminOrderItemList.newInstance] factory method to
 * create an instance of this fragment.
 */
class AdminOrderItemList(orderItemList: List<AdminOrderItem>) : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var mainList: List<AdminOrderItem> = orderItemList;


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
        val view = inflater.inflate(R.layout.fragment_admin_order_item_list, container, false)

        var tableLayout = view.findViewById<TableLayout>(R.id.table_layout)

        val myAdapter = AdminOrderItemAdapter(requireContext(), mainList)

        tableLayout.removeAllViews()

        val btnBack = view.findViewById<Button>(R.id.btnBack)


        for (i in 0 until myAdapter.count) {
            val rowView = myAdapter.getView(i, null, tableLayout)
            tableLayout.addView(rowView)
        }

        btnBack.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        return view
    }
}
package com.bhx.bhx.View.Admin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.bhx.bhx.Controller.CategoryAdminController
import com.bhx.bhx.Controller.OrderAdminController
import com.bhx.bhx.Controller.RetrofitInstance
import com.bhx.bhx.Model.AdminOrder
import com.bhx.bhx.Model.AdminOrderItem
import com.bhx.bhx.Model.Category
import com.bhx.bhx.R
import com.bhx.bhx.View.Admin.Adapter.OrderStatus
import com.bhx.bhx.View.Admin.Adapter.OrderStatusSpinnerAdapter
import com.bhx.bhx.View.Admin.Adapter.orderStatuses
import com.google.android.material.textfield.TextInputLayout
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AdminEditOrder.newInstance] factory method to
 * create an instance of this fragment.
 */

data class UpdateStatusOrder (
    @SerializedName("status")
    @Expose()
    val status: String,

    @SerializedName("is_paid")
    @Expose()
    val is_paid: Boolean,
)

class AdminEditOrder(order: AdminOrder) : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var pOrder = order

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
        val view = inflater.inflate(R.layout.fragment_admin_edit_order, container, false)

        var pOrderName = ""

        var statusSpinner = view.findViewById<Spinner>(R.id.spinnerStatus)
        statusSpinner.adapter = OrderStatusSpinnerAdapter(requireContext())

        val defaultPosition = orderStatuses.indexOfFirst { it.status == pOrder.status}
        statusSpinner.setSelection(defaultPosition)

        statusSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                val selectedItem = parent.getItemAtPosition(position) as OrderStatus
                pOrderName = selectedItem.status
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
            }
        }

        var isPaidSwitch = view.findViewById<Switch>(R.id.switchIsPaid)
        isPaidSwitch.isChecked = pOrder.is_paid

        val btnBack = view.findViewById<Button>(R.id.btnBack)
        btnBack.setOnClickListener {
            //Không phải gọi lại toàn AdminCategoryList() là vì không cần load lại api
            parentFragmentManager.popBackStack()
        }

        val saveButton = view.findViewById<Button>(R.id.saveBtn)

        saveButton.setOnClickListener{
            val newOrderStatus = UpdateStatusOrder(pOrderName, isPaidSwitch.isChecked)

            val apiOrderAdminInstance: OrderAdminController = RetrofitInstance.getInstance().create(
                OrderAdminController::class.java)
            apiOrderAdminInstance.updateStatus(pOrder.id!!, newOrderStatus).enqueue(object :
                Callback<ResponseBody> {
                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                    if (response.isSuccessful) {
                        val fragmentManager = (context as AppCompatActivity).supportFragmentManager
                        fragmentManager.beginTransaction().replace(
                            R.id.adminContainer,
                            AdminOrdersList()
                        ).commit()
                    } else {
                        Toast.makeText(context, "Cập nhật trạng thái thất bại", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Toast.makeText(context, "Cập nhật trạng thái thất bại", Toast.LENGTH_SHORT).show()
                }
            })
        }

        return view
    }

}
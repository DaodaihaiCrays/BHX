package com.bhx.bhx.View.Admin

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bhx.bhx.Controller.PromotionsAdminController
import com.bhx.bhx.Controller.RetrofitInstance
import com.bhx.bhx.Model.Category
import com.bhx.bhx.Model.Promotion
import com.bhx.bhx.R
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.create
import java.text.SimpleDateFormat
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AdminCreatePromotions.newInstance] factory method to
 * create an instance of this fragment.
 */
class AdminCreatePromotions : Fragment() {
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_admin_create_promotions, container, false)

        val saveButton = view.findViewById<Button>(R.id.saveBtn)
        val btnBack = view.findViewById<Button>(R.id.btnBack)

        val promotion = arguments?.getSerializable("promotionEdit") as? Promotion

        btnBack.setOnClickListener {
            val fragmentManager = (context as AppCompatActivity).supportFragmentManager
            fragmentManager.beginTransaction().replace(
                R.id.adminContainer,
                AdminPromotionsFragment()
            ).commit()
        }

        var apiPromotionAdminInstance: PromotionsAdminController = RetrofitInstance.getInstance().create(
            PromotionsAdminController::class.java)

        if (promotion != null) { // edit

        } else { // create

            val startDate = view.findViewById<TextInputEditText>(R.id.createStartInput)
            startDate.setOnFocusChangeListener { _, hasFocus ->
                if (hasFocus) {
                    val datePickerStartDate = MaterialDatePicker.Builder.datePicker()
                        .setTitleText("Chọn ngày bắt đầu")
                        .build()
                    datePickerStartDate.addOnPositiveButtonClickListener { date ->
                        val selectedDate = Date(date)
                        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                        startDate.setText(sdf.format(selectedDate))
                    }
                    datePickerStartDate.show(requireActivity().supportFragmentManager, "tag")
                }
            }

            val endDate = view.findViewById<TextInputEditText>(R.id.createEndInput)
            endDate.setOnFocusChangeListener { _, hasFocus ->
                if (hasFocus) {
                    val datePickerEndDate = MaterialDatePicker.Builder.datePicker()
                        .setTitleText("Chọn ngày bắt đầu")
                        .build()
                    datePickerEndDate.addOnPositiveButtonClickListener { date ->
                        val selectedDate = Date(date)
                        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                        endDate.setText(sdf.format(selectedDate))
                    }
                    datePickerEndDate.show(requireActivity().supportFragmentManager, "tag")
                }
            }

            saveButton.setOnClickListener{
                val name = view.findViewById<TextInputLayout>(R.id.createName)
                val percent = view.findViewById<TextInputLayout>(R.id.createPercent)
                val inputDateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()) // gửi date nhớ format dạng này

                val newPromotion = Promotion(null, name.editText?.text.toString(), percent.editText?.text.toString().toIntOrNull() ?: 0, sdf.format(inputDateFormat.parse(startDate?.text.toString())), sdf.format(inputDateFormat.parse(endDate?.text.toString())))

                apiPromotionAdminInstance.create(newPromotion).enqueue(object : Callback<Promotion> {
                    override fun onResponse(call: Call<Promotion>, response: Response<Promotion>) {
                        if (response.isSuccessful) {
                            // Promotion mới được tạo sẽ được trả về ở đây
                            val promotion = response.body()

                            Toast.makeText(container!!.context, "Tạo đợt giảm giá thành công", Toast.LENGTH_SHORT).show()

                            // Điều hướng trở lại danh sách Promotions
                            val fragmentManager = (context as AppCompatActivity).supportFragmentManager
                            fragmentManager.beginTransaction().replace(
                                R.id.adminContainer,
                                AdminPromotionsFragment()
                            ).commit()
                        } else {
                            Toast.makeText(container!!.context, "Tạo đợt giảm giá thất bại", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<Promotion>, t: Throwable) {
                        Toast.makeText(container!!.context, "Tạo đợt giảm giá thất bại", Toast.LENGTH_SHORT).show()
                    }
                })
            }

        }

        // Inflate the layout for this fragment
        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment AdminCreatePromotions.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AdminCreatePromotions().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
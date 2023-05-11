package com.bhx.bhx.View.Admin

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.bhx.bhx.Controller.PromotionsAdminController
import com.bhx.bhx.Controller.RetrofitInstance
import com.bhx.bhx.Model.Promotion
import com.bhx.bhx.R
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AdminEditPromotions.newInstance] factory method to
 * create an instance of this fragment.
 */
class AdminEditPromotions(var pPromotion: Promotion) : Fragment() {
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

        //val promotion = arguments?.getSerializable("promotionEdit") as? Promotion

        btnBack.setOnClickListener {
            val fragmentManager = (context as AppCompatActivity).supportFragmentManager
            fragmentManager.beginTransaction().replace(
                R.id.adminContainer,
                AdminPromotionsFragment()
            ).commit()
        }

        var apiPromotionAdminInstance: PromotionsAdminController =
            RetrofitInstance.getInstance().create(
                PromotionsAdminController::class.java
            )

        if (pPromotion != null) { // edit
            val name = view.findViewById<TextInputLayout>(R.id.createName)
            if (pPromotion.name !== null) name.editText?.setText(pPromotion.name)
            val inputDateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

            val percent = view.findViewById<TextInputLayout>(R.id.createPercent)
            if (pPromotion.sale_percent !== null) percent.editText?.setText(pPromotion.sale_percent!!.toString())

            val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
            dateFormat.timeZone = TimeZone.getTimeZone("UTC")

            val startDate = view.findViewById<TextInputEditText>(R.id.createStartInput)
            val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.US)
            var startDateDateType: Date? = null

            if (pPromotion.start_time !== null)
            {
                startDateDateType = dateFormat.parse(pPromotion.start_time)
                startDate.setText(sdf.format(startDateDateType))
            }

            val calendarStart = Calendar.getInstance()
            calendarStart.time = dateFormat.parse(pPromotion.start_time)
            calendarStart.add(Calendar.HOUR_OF_DAY, 7)

            val datePickerStartDate = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Chọn ngày bắt đầu")
                .setSelection(calendarStart.timeInMillis)
                .build()
            datePickerStartDate.addOnPositiveButtonClickListener { date ->
                val selectedDate = Date(date)
                val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                startDate.setText(sdf.format(selectedDate))
            }

            startDate.setOnFocusChangeListener { _, hasFocus ->
                if (hasFocus) {
                    datePickerStartDate.show(requireActivity().supportFragmentManager, "tag")
                }
            }

            val endDate = view.findViewById<TextInputEditText>(R.id.createEndInput)
            var endDateDateType: Date? = null
            if (pPromotion.end_time !== null)
            {
                endDateDateType = dateFormat.parse(pPromotion.end_time)
                endDate.setText(sdf.format(endDateDateType))
            }

            val calendarEnd = Calendar.getInstance()
            calendarEnd.time = dateFormat.parse(pPromotion.end_time)
            calendarEnd.add(Calendar.HOUR_OF_DAY, 7)

            val datePickerEndDate = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Chọn ngày kết thúc")
                .setSelection(calendarEnd.timeInMillis)
                .build()
            datePickerEndDate.addOnPositiveButtonClickListener { date ->
                val selectedDate = Date(date)
                val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                endDate.setText(sdf.format(selectedDate))
            }

            endDate.setOnFocusChangeListener { _, hasFocus ->
                if (hasFocus) {
                    datePickerEndDate.show(requireActivity().supportFragmentManager, "tag")
                }
            }

            saveButton.setOnClickListener {
                val uploadSDF = SimpleDateFormat(
                    "yyyy-MM-dd",
                    Locale.getDefault()
                ) // gửi date nhớ format dạng này

                val newPromotion = Promotion(
                    null,
                    name.editText?.text.toString(),
                    percent.editText?.text.toString().toIntOrNull() ?: 0,
                    uploadSDF.format(inputDateFormat.parse(startDate?.text.toString())),
                    uploadSDF.format(inputDateFormat.parse(endDate?.text.toString()))
                )

                apiPromotionAdminInstance.update(pPromotion.id!!, newPromotion)
                    .enqueue(object : Callback<Promotion> {
                        override fun onResponse(
                            call: Call<Promotion>,
                            response: Response<Promotion>
                        ) {
                            if (response.isSuccessful) {
                                // Promotion mới được tạo sẽ được trả về ở đây
                                val promotion = response.body()

                                Toast.makeText(
                                    container!!.context,
                                    "Tạo đợt giảm giá thành công",
                                    Toast.LENGTH_SHORT
                                ).show()

                                // Điều hướng trở lại danh sách Promotions
                                val fragmentManager =
                                    (context as AppCompatActivity).supportFragmentManager
                                fragmentManager.beginTransaction().replace(
                                    R.id.adminContainer,
                                    AdminPromotionsFragment()
                                ).commit()
                            } else {
                                Toast.makeText(
                                    container!!.context,
                                    "Tạo đợt giảm giá thất bại",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }

                        override fun onFailure(call: Call<Promotion>, t: Throwable) {
                            Toast.makeText(
                                container!!.context,
                                "Tạo đợt giảm giá thất bại",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    })
            }

        }

        // Inflate the layout for this fragment
        return view
    }
}
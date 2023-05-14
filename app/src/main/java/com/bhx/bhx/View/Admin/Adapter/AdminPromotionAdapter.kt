package com.bhx.bhx.View.Admin.Adapter

import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.text.TextUtils
import android.util.Log
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TableRow
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.bhx.bhx.Controller.PromotionsAdminController
import com.bhx.bhx.Controller.RetrofitInstance
import com.bhx.bhx.Model.Promotion
import com.bhx.bhx.R
import com.bhx.bhx.View.Admin.AdminEditPromotions
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.bhx.bhx.View.Admin.AdminPromotionsFragment
import java.text.SimpleDateFormat
import java.util.*

class AdminPromotionAdapter(private val context: Context, private var data: List<Promotion>) : BaseAdapter() {
    override fun getCount(): Int {
        // Trả về tổng số hàng của bảng
        return data.size
    }

    override fun getItem(position: Int): Any? {
        // Trả về dữ liệu tại vị trí của hàng
        return data[position]
    }

    override fun getItemId(position: Int): Long {
        // Trả về id của hàng
        return position.toLong()
    }

    fun setList(list: List<Promotion>) {
        this.data = list
        notifyDataSetChanged()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val tableRow = TableRow(context)
        val params = TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, 200) // Chiều cao cố định là 200 pixels
        tableRow.layoutParams = params

        val textView1 = TextView(context)
        textView1.text = data[position].name
        textView1.width = 100
        textView1.height = 200
        textView1.maxLines = 2
        textView1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15f)
        textView1.setPadding(10, 10, 10, 10)
        if (position % 2 == 0) {
            textView1.background = ContextCompat.getDrawable(context, R.drawable.admin_table_colors)
        }
        else {
            textView1.background = ContextCompat.getDrawable(context, R.drawable.admin_table_color_even)
        }
        tableRow.addView(textView1)

        val textView11 = TextView(context)
        textView11.text = data[position].sale_percent.toString()
        textView11.width = 50
        textView11.height = 200
        textView11.maxLines = 2
        textView11.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15f)
        textView11.setPadding(10, 10, 10, 10)
        if (position % 2 == 0) {
            textView11.background = ContextCompat.getDrawable(context, R.drawable.admin_table_colors)
        }
        else {
            textView11.background = ContextCompat.getDrawable(context, R.drawable.admin_table_color_even)
        }
        tableRow.addView(textView11)

        val textView2 = TextView(context)
        val inputFormat2 = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US)
        inputFormat2.timeZone = TimeZone.getTimeZone("UTC")
        val date2 = inputFormat2.parse(data[position].start_time)
        textView2.text = SimpleDateFormat("dd/MM/yyyy", Locale.US).format(date2)
        textView2.height = 200
        textView2.width = 100
        textView2.maxLines = 3
        textView2.ellipsize = TextUtils.TruncateAt.END
        textView2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15f)
        textView2.setPadding(10, 10, 10, 10)
        if (position % 2 == 0) {
            textView2.background = ContextCompat.getDrawable(context, R.drawable.admin_table_colors)
        }
        else {
            textView2.background = ContextCompat.getDrawable(context, R.drawable.admin_table_color_even)
        }

        tableRow.addView(textView2)

        val textView21 = TextView(context)
        val inputFormat21 = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US)
        inputFormat21.timeZone = TimeZone.getTimeZone("UTC")
        val date21 = inputFormat21.parse(data[position].end_time)
        textView21.text = SimpleDateFormat("dd/MM/yyyy", Locale.US).format(date21)
        textView21.height = 200
        textView21.width = 100
        textView21.maxLines = 3
        textView21.ellipsize = TextUtils.TruncateAt.END
        textView21.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15f)
        textView21.setPadding(10, 10, 10, 10)
        if (position % 2 == 0) {
            textView21.background = ContextCompat.getDrawable(context, R.drawable.admin_table_colors)
        }
        else {
            textView21.background = ContextCompat.getDrawable(context, R.drawable.admin_table_color_even)
        }

        tableRow.addView(textView21)

        val textView3 = TextView(context)
        val tvXoa: TextView?? = null
        textView3.setText("Chọn")
        textView3.setTextColor(Color.BLUE)
        textView3.setTypeface(null, Typeface.ITALIC)
        textView3.gravity = Gravity.CENTER_HORIZONTAL
        textView3.height = 200
        textView3.width = 100
        textView3.ellipsize = TextUtils.TruncateAt.END
        textView3.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15f)
        textView3.setPadding(0, 10, 10, 0)
        if (position % 2 == 0) {
            textView3.background = ContextCompat.getDrawable(context, R.drawable.admin_table_colors)
        }
        else {
            textView3.background = ContextCompat.getDrawable(context, R.drawable.admin_table_color_even)
        }

        tableRow.addView(textView3)

        textView3.setOnClickListener {
            // Lấy thông tin của hàng được bấm
            val rowData = data[position].id
            Log.d("TAG", "Row data: $rowData")

            val builder = AlertDialog.Builder(context)

            builder.setTitle("Thông báo")
            builder.setMessage("Hãy chọn một trong hai lựa chọn!")

            builder.setPositiveButton("Xóa") { dialog, _ ->
                var apiPromotionsAdminInstance: PromotionsAdminController = RetrofitInstance.getInstance().create(
                    PromotionsAdminController::class.java)

                data[position].id?.let { it1 ->
                    apiPromotionsAdminInstance.delete(it1).enqueue(object : Callback<Void> {
                        override fun onResponse(call: Call<Void>, response: Response<Void>) {
                            if (response.isSuccessful) {
                                val fragmentManager = (context as AppCompatActivity).supportFragmentManager
                                fragmentManager.beginTransaction().replace(
                                    R.id.adminContainer,
                                    AdminPromotionsFragment()
                                ).commit()
                            } else {
                                Toast.makeText(context, "Delete category failed", Toast.LENGTH_SHORT).show()
                            }
                        }

                        override fun onFailure(call: Call<Void>, t: Throwable) {
                            Toast.makeText(context, "Delete category failed", Toast.LENGTH_SHORT).show()
                        }
                    })
                }
                dialog.dismiss()
            }
            builder.setNegativeButton("Điều chỉnh") { dialog, _ ->
                val fragmentManager = (context as AppCompatActivity).supportFragmentManager
                fragmentManager.beginTransaction().replace(
                    R.id.adminContainer,
                    AdminEditPromotions(data[position])
                ).addToBackStack(null).commit()
                dialog.dismiss()
            }

            builder.setNeutralButton("Quay lại") { dialog, _ ->
                dialog.dismiss()
            }

            val alertDialog = builder.create()
            alertDialog.show()
        }

        return tableRow
    }

    fun setData(data: MutableList<Promotion>) {
        this.data = data
        notifyDataSetChanged()
    }
}
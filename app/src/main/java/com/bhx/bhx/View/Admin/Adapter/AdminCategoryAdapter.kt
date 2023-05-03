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
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.bhx.bhx.Controller.CategoryAdminController
import com.bhx.bhx.Controller.RetrofitInstance
import com.bhx.bhx.Model.Category
import com.bhx.bhx.R
import com.bhx.bhx.View.Admin.AdminCategoryList
import com.bhx.bhx.View.Admin.AdminEditCategory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AdminCategoryAdapter(private val context: Context, private var data: List<Category>) : BaseAdapter() {
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

    fun setList(list: List<Category>) {
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

        val textView2 = TextView(context)
        textView2.text = data[position].description
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

        val textView3 = TextView(context)
        val tvXoa:TextView?? = null
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
                var apiCategoryAdminInstance: CategoryAdminController = RetrofitInstance.getInstance().create(CategoryAdminController::class.java)

                data[position].id?.let { it1 ->
                    apiCategoryAdminInstance.delete(it1).enqueue(object : Callback<Void> {
                        override fun onResponse(call: Call<Void>, response: Response<Void>) {
                            if (response.isSuccessful) {
                                val fragmentManager = (context as AppCompatActivity).supportFragmentManager
                                fragmentManager.beginTransaction().replace(
                                    R.id.adminContainer,
                                    AdminCategoryList()
                                ).commit()
                            } else {
                                Toast.makeText(context, "Delete category failed", Toast.LENGTH_SHORT).show()
                            }
                        }

                        override fun onFailure(call: Call<Void>, t: Throwable) {
                            Toast.makeText(context, "Delete category failed", Toast.LENGTH_SHORT).show()
                            Log.e("AdminEditCategory", "Delete category failed", t)
                        }
                    })
                }
                dialog.dismiss()
            }
            builder.setNegativeButton("Điều chỉnh") { dialog, _ ->
                val fragmentManager = (context as AppCompatActivity).supportFragmentManager
                fragmentManager.beginTransaction().replace(
                    R.id.adminContainer,
                    AdminEditCategory(data[position])
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

    fun setData(data: MutableList<Category>) {
        this.data = data
        notifyDataSetChanged()
    }
}
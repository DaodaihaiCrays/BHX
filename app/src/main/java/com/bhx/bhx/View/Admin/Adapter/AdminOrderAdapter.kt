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
import com.bhx.bhx.Model.AdminOrder
import com.bhx.bhx.Model.AdminOrderItem
import com.bhx.bhx.Model.AdminUser
import com.bhx.bhx.Model.Promotion
import com.bhx.bhx.R
import com.bhx.bhx.View.Admin.AdminEditOrder
import com.bhx.bhx.View.Admin.AdminEditPromotions
import com.bhx.bhx.View.Admin.AdminOrderItemList
import com.bhx.bhx.View.Admin.AdminPromotionsFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class AdminOrderAdapter(private val context: Context, private var data: List<AdminOrder>) : BaseAdapter() {
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

    fun setList(list: List<AdminOrder>) {
        this.data = list
        notifyDataSetChanged()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val tableRow = TableRow(context)
        val params = TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, 200) // Chiều cao cố định là 200 pixels
        tableRow.layoutParams = params

        val textView1 = TextView(context)
        textView1.text = data[position].user_id.toString()
        textView1.width = 300
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
        textView2.text = data[position].fullname
        textView2.width = 300
        textView2.height = 200
        textView2.maxLines = 2
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
        textView3.text = data[position].phone
        textView3.width = 300
        textView3.height = 200
        textView3.maxLines = 2
        textView3.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15f)
        textView3.setPadding(10, 10, 10, 10)
        if (position % 2 == 0) {
            textView3.background = ContextCompat.getDrawable(context, R.drawable.admin_table_colors)
        }
        else {
            textView3.background = ContextCompat.getDrawable(context, R.drawable.admin_table_color_even)
        }
        tableRow.addView(textView3)

        val textView4 = TextView(context)
        textView4.text = data[position].email
        textView4.width = 300
        textView4.height = 200
        textView4.maxLines = 2
        textView3.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15f)
        textView4.setPadding(10, 10, 10, 10)
        if (position % 2 == 0) {
            textView4.background = ContextCompat.getDrawable(context, R.drawable.admin_table_colors)
        }
        else {
            textView4.background = ContextCompat.getDrawable(context, R.drawable.admin_table_color_even)
        }
        tableRow.addView(textView4)

        val textView5 = TextView(context)
        textView5.text = data[position].gender
        textView5.width = 300
        textView5.height = 200
        textView5.maxLines = 2
        textView5.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15f)
        textView5.setPadding(10, 10, 10, 10)
        if (position % 2 == 0) {
            textView5.background = ContextCompat.getDrawable(context, R.drawable.admin_table_colors)
        }
        else {
            textView5.background = ContextCompat.getDrawable(context, R.drawable.admin_table_color_even)
        }
        tableRow.addView(textView5)

        val textView6 = TextView(context)
        textView6.text = data[position].address
        textView6.width = 300
        textView6.height = 200
        textView6.maxLines = 2
        textView6.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15f)
        textView6.setPadding(10, 10, 10, 10)
        if (position % 2 == 0) {
            textView6.background = ContextCompat.getDrawable(context, R.drawable.admin_table_colors)
        }
        else {
            textView6.background = ContextCompat.getDrawable(context, R.drawable.admin_table_color_even)
        }
        tableRow.addView(textView6)

        val textView7 = TextView(context)
        val inputFormat2 = SimpleDateFormat("yyyy-MM-dd", Locale.US)
        inputFormat2.timeZone = TimeZone.getTimeZone("UTC")
        val date2 = inputFormat2.parse(data[position].delivery_date)
        textView7.text = SimpleDateFormat("dd/MM/yyyy", Locale.US).format(date2)
        textView7.height = 200
        textView7.width = 300
        textView7.maxLines = 3
        textView7.ellipsize = TextUtils.TruncateAt.END
        textView7.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15f)
        textView7.setPadding(10, 10, 10, 10)
        if (position % 2 == 0) {
            textView7.background = ContextCompat.getDrawable(context, R.drawable.admin_table_colors)
        }
        else {
            textView7.background = ContextCompat.getDrawable(context, R.drawable.admin_table_color_even)
        }
        tableRow.addView(textView7)

        val textView8 = TextView(context)
        textView8.text = data[position].payment_method_name
        textView8.width = 300
        textView8.height = 200
        textView8.maxLines = 2
        textView8.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15f)
        textView8.setPadding(10, 10, 10, 10)
        if (position % 2 == 0) {
            textView8.background = ContextCompat.getDrawable(context, R.drawable.admin_table_colors)
        }
        else {
            textView8.background = ContextCompat.getDrawable(context, R.drawable.admin_table_color_even)
        }
        tableRow.addView(textView8)

        val textView9 = TextView(context)
        textView9.text = if(data[position].is_paid) "YES" else ""
        textView9.width = 300
        textView9.height = 200
        textView9.maxLines = 2
        textView9.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15f)
        textView9.setPadding(10, 10, 10, 10)
        if (position % 2 == 0) {
            textView9.background = ContextCompat.getDrawable(context, R.drawable.admin_table_colors)
        }
        else {
            textView9.background = ContextCompat.getDrawable(context, R.drawable.admin_table_color_even)
        }
        tableRow.addView(textView9)

        val textView10 = TextView(context)
        textView10.text = data[position].status
        textView10.width = 300
        textView10.height = 200
        textView10.maxLines = 2
        textView10.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15f)
        textView10.setPadding(10, 10, 10, 10)
        if (position % 2 == 0) {
            textView10.background = ContextCompat.getDrawable(context, R.drawable.admin_table_colors)
        }
        else {
            textView10.background = ContextCompat.getDrawable(context, R.drawable.admin_table_color_even)
        }
        tableRow.addView(textView10)

        val textView11 = TextView(context)
        textView11.text = "Chọn"
        textView11.setTextColor(Color.BLUE)
        textView11.setTypeface(null, Typeface.ITALIC)
        textView11.width = 300
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

        textView11.setOnClickListener {
            // Lấy thông tin của hàng được bấm

            val fragmentManager = (context as AppCompatActivity).supportFragmentManager
            fragmentManager.beginTransaction().replace(
                R.id.adminContainer,
                AdminOrderItemList(data[position].order_items!!)
            ).addToBackStack(null).commit()
        }

        val textView12 = TextView(context)
        textView12.text = "Sửa"
        textView12.setTextColor(Color.BLUE)
        textView12.setTypeface(null, Typeface.ITALIC)
        textView12.width = 300
        textView12.height = 200
        textView12.maxLines = 2
        textView12.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15f)
        textView12.setPadding(10, 10, 10, 10)
        if (position % 2 == 0) {
            textView12.background = ContextCompat.getDrawable(context, R.drawable.admin_table_colors)
        }
        else {
            textView12.background = ContextCompat.getDrawable(context, R.drawable.admin_table_color_even)
        }
        tableRow.addView(textView12)

        textView12.setOnClickListener {
            // Lấy thông tin của hàng được bấm

            val fragmentManager = (context as AppCompatActivity).supportFragmentManager
            fragmentManager.beginTransaction().replace(
                R.id.adminContainer,
                AdminEditOrder(data[position])
            ).addToBackStack(null).commit()
        }

        return tableRow
    }

    fun setData(data: MutableList<AdminOrder>) {
        this.data = data
        notifyDataSetChanged()
    }
}
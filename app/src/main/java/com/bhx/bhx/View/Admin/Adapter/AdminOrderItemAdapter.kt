package com.bhx.bhx.View.Admin.Adapter

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.text.TextUtils
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TableRow
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.bhx.bhx.Model.AdminOrder
import com.bhx.bhx.Model.AdminOrderItem
import com.bhx.bhx.R
import com.bhx.bhx.View.Admin.AdminOrderItemList
import java.text.SimpleDateFormat
import java.util.*

class AdminOrderItemAdapter(private val context: Context, private var data: List<AdminOrderItem>) : BaseAdapter() {
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

    fun setList(list: List<AdminOrderItem>) {
        this.data = list
        notifyDataSetChanged()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val tableRow = TableRow(context)
        val params = TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, 200) // Chiều cao cố định là 200 pixels
        tableRow.layoutParams = params

        val textView1 = TextView(context)
        textView1.text = data[position].name
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
        textView2.text = data[position].quantity.toString()
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
        textView3.text = data[position].sale_percent.toString()
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
        textView4.text = data[position].unit_price.toString()
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
        textView5.text = data[position].general_description
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

        return tableRow
    }

    fun setData(data: MutableList<AdminOrderItem>) {
        this.data = data
        notifyDataSetChanged()
    }
}
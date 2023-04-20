package com.bhx.bhx.View.Admin.Adapter

import android.content.Context
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import com.bhx.bhx.Model.Category
import com.bhx.bhx.R

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

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val tableRow = TableRow(context)

        val textView1 = TextView(context)
        textView1.text = data[position].name
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
        textView2.setPadding(10, 10, 10, 10)
        if (position % 2 == 0) {
            textView2.background = ContextCompat.getDrawable(context, R.drawable.admin_table_colors)
        }
        else {
            textView2.background = ContextCompat.getDrawable(context, R.drawable.admin_table_color_even)
        }

        tableRow.addView(textView2)

        val textView3 = TextView(context)
        textView3.text = ""
        textView3.setPadding(10, 10, 10, 10)
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
        }

        return tableRow
    }

    fun setData(data: MutableList<Category>) {
        this.data = data
        notifyDataSetChanged()
    }
}
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
import com.bhx.bhx.Model.AdminUser
import com.bhx.bhx.Model.Category
import com.bhx.bhx.R
import com.bhx.bhx.View.Admin.AdminCategoryList
import com.bhx.bhx.View.Admin.AdminEditCategory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AdminUserAdapter(private val context: Context, private var data: List<AdminUser>) : BaseAdapter() {
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

    fun setList(list: List<AdminUser>) {
        this.data = list
        notifyDataSetChanged()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val tableRow = TableRow(context)
        val params = TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, 200) // Chiều cao cố định là 200 pixels
        tableRow.layoutParams = params

        val textView1 = TextView(context)
        textView1.text = data[position].firebase_uid
        textView1.width = 400
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
        textView2.text = data[position].email
        textView2.height = 200
        textView2.width = 400
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
        textView3.text = data[position].fullname
        textView3.height = 200
        textView3.width = 400
        textView3.maxLines = 3
        textView3.ellipsize = TextUtils.TruncateAt.END
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
        textView4.text = data[position].phone_number
        textView4.height = 200
        textView4.width = 400
        textView4.maxLines = 3
        textView4.ellipsize = TextUtils.TruncateAt.END
        textView4.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15f)
        textView4.setPadding(10, 10, 10, 10)
        if (position % 2 == 0) {
            textView4.background = ContextCompat.getDrawable(context, R.drawable.admin_table_colors)
        }
        else {
            textView4.background = ContextCompat.getDrawable(context, R.drawable.admin_table_color_even)
        }

        tableRow.addView(textView4)

        val textView5 = TextView(context)
        textView5.text = data[position].address
        textView5.height = 200
        textView5.width = 600
        textView5.maxLines = 3
        textView5.ellipsize = TextUtils.TruncateAt.END
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
        textView6.text = data[position].gender
        textView6.height = 200
        textView6.width = 400
        textView6.maxLines = 3
        textView6.ellipsize = TextUtils.TruncateAt.END
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
        textView7.text = data[position].authority
        textView7.height = 200
        textView7.width = 400
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


        return tableRow
    }

    fun setData(data: MutableList<AdminUser>) {
        this.data = data
        notifyDataSetChanged()
    }
}
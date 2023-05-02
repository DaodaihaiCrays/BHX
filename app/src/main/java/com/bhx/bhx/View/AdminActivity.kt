package com.bhx.bhx.View

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.bhx.bhx.R
import com.bhx.bhx.View.Admin.AdminCategoryList
import com.bhx.bhx.View.Admin.AdminCreateCategory

class AdminActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)


    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.category -> {
                // Code xử lý khi người dùng chọn mục Category
                val categoryFragment = AdminCategoryList()
                supportFragmentManager.beginTransaction()
                    .replace(R.id.adminContainer, categoryFragment)
                    .commit()

                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.admin_side_nav, menu)
        return true
    }
}
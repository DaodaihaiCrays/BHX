package com.bhx.bhx.View

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.bhx.bhx.R
import com.bhx.bhx.View.Admin.*
import com.google.firebase.auth.FirebaseAuth

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
            R.id.sale -> {
                // Code xử lý khi người dùng chọn mục Sale
                val promotionFragment = AdminPromotionsFragment()
                supportFragmentManager.beginTransaction()
                    .replace(R.id.adminContainer, promotionFragment)
                    .commit()

                return true
            }
            R.id.product -> {
                // Code xử lý khi người dùng chọn mục Product
                val productFragment = AdminProductList()
                supportFragmentManager.beginTransaction()
                    .replace(R.id.adminContainer, productFragment)
                    .commit()

                return true
            }
            R.id.user -> {
                // Code xử lý khi người dùng chọn mục User
                val productFragment = AdminAccountList()
                supportFragmentManager.beginTransaction()
                    .replace(R.id.adminContainer, productFragment)
                    .commit()

                return true
            }
            R.id.signOut -> {
                FirebaseAuth.getInstance().signOut()
                if(FirebaseAuth.getInstance().currentUser==null) {
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                }
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
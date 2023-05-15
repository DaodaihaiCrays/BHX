package com.bhx.bhx.View

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.Toast
import com.bhx.bhx.R
import com.bhx.bhx.View.Admin.*
import com.google.firebase.auth.FirebaseAuth

class AdminActivity : AppCompatActivity() {

    lateinit var imgAdmin: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)

        supportActionBar!!.setBackgroundDrawable(ColorDrawable(Color.parseColor("#024F8C")))

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
            R.id.order -> {
                // Code xử lý khi người dùng chọn mục Order
                val fragment = AdminOrdersList()
                supportFragmentManager.beginTransaction()
                    .replace(R.id.adminContainer, fragment)
                    .commit()

                return true
            }
            R.id.backCus -> {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                return true
            }
            R.id.signOut -> {
                FirebaseAuth.getInstance().signOut()
                Toast.makeText(this,"Đăng xuất",Toast.LENGTH_SHORT).show()
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
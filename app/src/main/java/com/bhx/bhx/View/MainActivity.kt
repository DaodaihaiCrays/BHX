package com.bhx.bhx.View

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.bhx.bhx.Model.Product
import com.bhx.bhx.R
import com.bhx.bhx.View.AccountFragment.AccountFragment
import com.bhx.bhx.View.HomeFragment.HomeFragment
import com.bhx.bhx.View.NotificationFragment.NotificationFragment
import com.bhx.bhx.View.SaleFragment.SaleFragment
import com.bhx.bhx.View.ShoppingCart.ShoppingCartActivity
import com.ismaeldivita.chipnavigation.ChipNavigationBar
import com.ismaeldivita.chipnavigation.ChipNavigationBar.OnItemSelectedListener
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json


class MainActivity : AppCompatActivity() {
    lateinit var chipNavigationBar: ChipNavigationBar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val actionBar: ActionBar? = supportActionBar
        actionBar?.hide()

        chipNavigationBar = findViewById(R.id.bottomNav);
        chipNavigationBar.setItemSelected(R.id.home, true);

        supportFragmentManager.beginTransaction().replace(R.id.container, HomeFragment()).commit()
        bottomMenu()

        val btn_ThanhToan: Button = findViewById(R.id.btn_ThanhToan);
        btn_ThanhToan.setOnClickListener {
            val intent: Intent = Intent(this, ShoppingCartActivity::class.java);
            startActivity(intent);
        }
    }

    private fun bottomMenu() {
        chipNavigationBar.setOnItemSelectedListener { id ->
            val selectedFragment: Fragment = when (id) {
                R.id.home -> HomeFragment()
                R.id.sale -> SaleFragment()
                R.id.notification -> NotificationFragment()
                R.id.user -> AccountFragment()
                else -> HomeFragment()
            }
            supportFragmentManager.beginTransaction().replace(
                R.id.container,
                selectedFragment
            ).commit()
        }
    }

}
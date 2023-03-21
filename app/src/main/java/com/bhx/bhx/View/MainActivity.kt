package com.bhx.bhx.View

import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.bhx.bhx.R
import com.bhx.bhx.View.AccountFragment.AccountFragment
import com.bhx.bhx.View.HomeFragment.HomeFragment
import com.bhx.bhx.View.NotificationFragment.NotificationFragment
import com.bhx.bhx.View.SaleFragment.SaleFragment
import com.ismaeldivita.chipnavigation.ChipNavigationBar
import com.ismaeldivita.chipnavigation.ChipNavigationBar.OnItemSelectedListener


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
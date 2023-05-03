package com.bhx.bhx.View

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bhx.bhx.Controller.CategoryController
import com.bhx.bhx.Controller.ProductController
import com.bhx.bhx.Controller.RetrofitInstance
import com.bhx.bhx.Global.Search
import com.bhx.bhx.Model.Product
import com.bhx.bhx.Model.ReviewCategory
import com.bhx.bhx.R
import com.bhx.bhx.View.AccountFragment.AccountFragment
import com.bhx.bhx.View.DetailProduct.DetailProductFragment
import com.bhx.bhx.View.DetailProduct.PropertiesAdapter
import com.bhx.bhx.View.HomeFragment.HomeFragment
import com.bhx.bhx.View.HomeFragment.ListProductAdapter
import com.bhx.bhx.View.HomeFragment.ProductAdapter
import com.bhx.bhx.View.Menu.MenuFragment
import com.bhx.bhx.View.NotificationFragment.NotificationFragment
import com.bhx.bhx.View.ProductOfSearchFragment.ProductOfSearchFragment
import com.bhx.bhx.View.SaleFragment.SaleFragment
import com.bhx.bhx.View.ShoppingCart.ShoppingCartActivity
import com.ismaeldivita.chipnavigation.ChipNavigationBar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response



class MainActivity : AppCompatActivity() {

    lateinit var chipNavigationBar: ChipNavigationBar
    //public lateinit var edtSearch: AutoCompleteTextView
    lateinit var btnMenu: Button
    lateinit var btnSearch: Button
    private lateinit var adapter: ProductAdapter
    private lateinit var revProducts: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val actionBar: ActionBar? = supportActionBar
        actionBar?.hide()

        Search.edtSearch = findViewById(R.id.edtSearch)
        btnMenu = findViewById(R.id.btnMenu)
        btnSearch = findViewById(R.id.btnSearch)

        chipNavigationBar = findViewById(R.id.bottomNav);
        chipNavigationBar.setItemSelected(R.id.home, true);

        supportFragmentManager.beginTransaction().replace(R.id.container, HomeFragment()).commit()
        bottomMenu()

        val btn_ThanhToan: Button = findViewById(R.id.btn_ThanhToan);
        btn_ThanhToan.setOnClickListener {
            val intent: Intent = Intent(this, ShoppingCartActivity::class.java);
            startActivity(intent);
        }

        if(Search.edtSearch.text.toString().length!=0) {
            Search.edtSearch.setText("")
        }

        btnMenu.setOnClickListener {
            supportFragmentManager.beginTransaction().replace(
                R.id.container,
                MenuFragment()
            ).addToBackStack(null).commit()
        }

        btnSearch.setOnClickListener {
            val strSearch = Search.edtSearch.text.toString()

            if(strSearch.length!=0) {
                RetrofitInstance.getInstance().create(ProductController::class.java).getAllProductsOfSearch(strSearch).enqueue(object : Callback<List<Product>> {
                    override fun onResponse(call: Call<List<Product>>, response: Response<List<Product>>) {
                        if (response.isSuccessful){
                            val product: List<Product>? = response.body()

                            supportFragmentManager.beginTransaction().replace(
                                R.id.container,
                                ProductOfSearchFragment(product!!)
                            ).addToBackStack(null).commit()

                        }else {
                        }
                    }
                    override fun onFailure(call: Call<List<Product>>, t: Throwable) {
                        Log.i("test","fail2")
                    }

                })
            }
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


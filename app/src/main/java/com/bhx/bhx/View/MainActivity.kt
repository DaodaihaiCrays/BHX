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
import com.bhx.bhx.Model.Product
import com.bhx.bhx.Model.ReviewCategory
import com.bhx.bhx.R
import com.bhx.bhx.View.AccountFragment.AccountFragment
import com.bhx.bhx.View.DetailProduct.DetailProductFragment
import com.bhx.bhx.View.DetailProduct.PropertiesAdapter
import com.bhx.bhx.View.HomeFragment.HomeFragment
import com.bhx.bhx.View.HomeFragment.ListProductAdapter
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
    private lateinit var edtSearch: AutoCompleteTextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val actionBar: ActionBar? = supportActionBar
        actionBar?.hide()

        edtSearch = findViewById(R.id.edtSearch)

        chipNavigationBar = findViewById(R.id.bottomNav);
        chipNavigationBar.setItemSelected(R.id.home, true);

        supportFragmentManager.beginTransaction().replace(R.id.container, HomeFragment()).commit()
        bottomMenu()

        val btn_ThanhToan: Button = findViewById(R.id.btn_ThanhToan);
        btn_ThanhToan.setOnClickListener {
            val intent: Intent = Intent(this, ShoppingCartActivity::class.java);
            startActivity(intent);
        }

        edtSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                val strSearch = s.toString()

                println(strSearch)

                if(strSearch.length!=0) {
                    RetrofitInstance.getInstance().create(ProductController::class.java).getAllProductsOfSearch(strSearch).enqueue(object : Callback<List<Product>> {
                        override fun onResponse(call: Call<List<Product>>, response: Response<List<Product>>) {
                            if (response.isSuccessful){
                                val product: List<Product>? = response.body()

                                supportFragmentManager.beginTransaction().replace(
                                    R.id.container,
                                    ProductOfSearchFragment(product!!)
                                ).commit()

                            }else {
                                Log.i("test","fail1")
                                //Toast.makeText(context, "Fail",Toast.LENGTH_SHORT).show()
                            }
                        }

                        override fun onFailure(call: Call<List<Product>>, t: Throwable) {
                            TODO("Not yet implemented")
                            Log.i("test","fail2")
                        }

                    })
                }
                else {

                    supportFragmentManager.beginTransaction().replace(
                        R.id.container,
                        HomeFragment()
                    ).commit()
                }

            }
        })
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


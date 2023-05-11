package com.bhx.bhx.View.Admin

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bhx.bhx.Controller.CategoryAdminController
import com.bhx.bhx.Controller.RetrofitInstance
import com.bhx.bhx.Model.Category
import com.bhx.bhx.R
import com.google.android.material.textfield.TextInputLayout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AdminCreateProduct.newInstance] factory method to
 * create an instance of this fragment.
 */
class AdminCreateProduct : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_admin_create_category, container, false)

        val saveButton = view.findViewById<Button>(R.id.saveBtn)
        val btnBack = view.findViewById<Button>(R.id.btnBack)

        val category = arguments?.getSerializable("categoryEdit") as? Category

        btnBack.setOnClickListener {
            val fragmentManager = (context as AppCompatActivity).supportFragmentManager
            fragmentManager.beginTransaction().replace(
                R.id.adminContainer,
                AdminCategoryList()
            ).commit()
        }

        var apiCategoryAdminInstance: CategoryAdminController = RetrofitInstance.getInstance().create(
            CategoryAdminController::class.java)

        if (category != null) { // edit

        } else { // create

            saveButton.setOnClickListener{
                val name = view.findViewById<TextInputLayout>(R.id.categoryName)
                val description = view.findViewById<TextInputLayout>(R.id.categoryDescription)
                val newCategory = Category(null, name.editText?.text.toString(), description.editText?.text.toString())

                apiCategoryAdminInstance.create(newCategory).enqueue(object : Callback<Category> {
                    override fun onResponse(call: Call<Category>, response: Response<Category>) {
                        if (response.isSuccessful) {
                            // Category mới được tạo sẽ được trả về ở đây
                            val category = response.body()

                            Toast.makeText(container!!.context, "Create new category successfully", Toast.LENGTH_SHORT).show()

                            // Điều hướng trở lại danh sách Category
                            val fragmentManager = (context as AppCompatActivity).supportFragmentManager
                            fragmentManager.beginTransaction().replace(
                                R.id.adminContainer,
                                AdminCategoryList()
                            ).commit()
                        } else {
                            Toast.makeText(container!!.context, "Create new category failed", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<Category>, t: Throwable) {
                        Toast.makeText(container!!.context, "Create new category failed", Toast.LENGTH_SHORT).show()
                        Log.e("AdminCreateCategory", "Create new category failed", t)
                    }
                })
            }

        }

        // Inflate the layout for this fragment
        return view
    }


}
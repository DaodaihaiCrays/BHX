package com.bhx.bhx.View.Admin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import com.bhx.bhx.Controller.CategoryAdminController
import com.bhx.bhx.Controller.RetrofitInstance
import com.bhx.bhx.Model.Category
import com.bhx.bhx.R
import retrofit2.Callback
import com.google.android.material.textfield.TextInputLayout
import retrofit2.Call
import retrofit2.Response

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AdminCreateCategory.newInstance] factory method to
 * create an instance of this fragment.
 */
class AdminCreateCategory : Fragment() {
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

        val category = arguments?.getSerializable("categoryEdit") as? Category

        var apiCategoryAdminInstance: CategoryAdminController = RetrofitInstance.getInstance().create(CategoryAdminController::class.java)

        if (category != null) { // edit

        } else { // create

            saveButton.setOnClickListener{
                val name = view.findViewById<TextInputLayout>(R.id.categoryName)
                val description = view.findViewById<TextInputLayout>(R.id.categoryDescription)
                val newCategory = Category(null, name.editText?.text.toString(), description.editText?.text.toString())

                apiCategoryAdminInstance.create(newCategory).enqueue(object : Callback<Category> {
                    override fun onResponse(
                        call: Call<Category>,
                        response: Response<Category>
                    ) {

                        if (response.isSuccessful) {
                            val data = response.body()


                        }
                        else {
                            Toast.makeText(container!!.context, "Fail",Toast.LENGTH_SHORT).show()
                        }
                    }
                    override fun onFailure(call: Call<Category>, t: Throwable) {
                        Toast.makeText(container!!.context,t.message,Toast.LENGTH_SHORT).show()
                        println(t.message)
                    }
                })
            }

        }

        // Inflate the layout for this fragment
        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment AdminCreateCategory.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AdminCreateCategory().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
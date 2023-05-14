package com.bhx.bhx.View.Admin

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.bhx.bhx.Controller.CategoryAdminController
import com.bhx.bhx.Controller.ProductAdminController
import com.bhx.bhx.Controller.PromotionsAdminController
import com.bhx.bhx.Controller.RetrofitInstance
import com.bhx.bhx.Model.AdminProduct
import com.bhx.bhx.Model.Category
import com.bhx.bhx.Model.Promotion
import com.bhx.bhx.R
import com.bhx.bhx.View.Admin.Adapter.CategorySpinnerAdapter
import com.bhx.bhx.View.Admin.Adapter.PromotionSpinnerAdaper
import com.bumptech.glide.Glide
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AdminEditProduct.newInstance] factory method to
 * create an instance of this fragment.
 */
class AdminEditProduct(var pProduct: AdminProduct) : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var selectedImageUri: Uri? = null
    private var pName : String? = null
    private var pPrice : Double? = null
    private var pStock : Int? = null
    private var pCategoryId : Int? = null
    private var pPromotionId : Int? = null
    private var pNote : String? = null
    private var pDescription : String? = null
    private var pImageUrl : String? = null
    private var changeImage : Boolean = false

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
        val view = inflater.inflate(R.layout.fragment_admin_create_product, container, false)
        val saveButton = view.findViewById<Button>(R.id.saveBtn)
        val btnBack = view.findViewById<Button>(R.id.btnBack)

        btnBack.setOnClickListener {
            val fragmentManager = (context as AppCompatActivity).supportFragmentManager
            fragmentManager.beginTransaction().replace(
                R.id.adminContainer,
                AdminProductList()
            ).commit()
        }

        var nameInput = view.findViewById<TextInputLayout>(R.id.createName)
        if (pProduct.name !== null) nameInput.editText?.setText(pProduct.name)

        var priceInput = view.findViewById<TextInputLayout>(R.id.createUnitPrice)
        if (pProduct.unit_price !== null) priceInput.editText?.setText(pProduct.unit_price.toString())

        var stockInput = view.findViewById<TextInputLayout>(R.id.createStock)
        if (pProduct.stock !== null) stockInput.editText?.setText(pProduct.stock.toString())

        var noteInput = view.findViewById<TextInputLayout>(R.id.createNote)
        if (pProduct.note !== null) noteInput.editText?.setText(pProduct.note)

        var descriptionInput = view.findViewById<TextInputLayout>(R.id.createDescription)
        if (pProduct.general_description !== null) descriptionInput.editText?.setText(pProduct.general_description)

        Log.i("testL", pProduct.banner!!)
        setDefaultImage(view, pProduct.banner)

        var apiCategoryAdminInstance: CategoryAdminController = RetrofitInstance.getInstance().create(
            CategoryAdminController::class.java)

        var categoryList: List<Category>

        apiCategoryAdminInstance.findAll().enqueue(object : Callback<List<Category>> {
            override fun onResponse(
                call: Call<List<Category>>,
                response: Response<List<Category>>
            ) {

                if (response.isSuccessful) {
                    categoryList = response.body()!!

                    var categorySpinner = view.findViewById<Spinner>(R.id.spinnerCategory)
                    categorySpinner.adapter = CategorySpinnerAdapter(requireContext(), categoryList)

                    val defaultPosition = categoryList.indexOfFirst { it.id == pProduct.category_id }
                    categorySpinner.setSelection(defaultPosition)

                    categorySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                        override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                            val selectedItem = parent.getItemAtPosition(position) as Category
                            pCategoryId = selectedItem.id
                        }

                        override fun onNothingSelected(parent: AdapterView<*>) {
                        }
                    }

                }else{
                    Toast.makeText(container!!.context, "Fail", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<List<Category>>, t: Throwable) {
                Toast.makeText(container!!.context,t.message, Toast.LENGTH_SHORT).show()
                println(t.message)
            }
        })

        var apiPromotionAdminInstance: PromotionsAdminController = RetrofitInstance.getInstance().create(
            PromotionsAdminController::class.java)

        var promotionList : List<Promotion>

        apiPromotionAdminInstance.findAll().enqueue(object : Callback<List<Promotion>> {
            override fun onResponse(
                call: Call<List<Promotion>>,
                response: Response<List<Promotion>>
            ) {

                if (response.isSuccessful) {
                    promotionList = response.body()!!

                    var promotionSpinner = view.findViewById<Spinner>(R.id.spinnerPromotion)
                    promotionSpinner.adapter = PromotionSpinnerAdaper(requireContext(), listOf(null) + promotionList)

                    if (pProduct.promotion_id !== null) {
                        val defaultPosition = promotionList.indexOfFirst { it.id == pProduct.promotion_id }
                        promotionSpinner.setSelection(defaultPosition)
                    }

                    promotionSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                        override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                            val selectedItem = parent.getItemAtPosition(position) as Promotion?
                            pPromotionId = selectedItem?.id ?: null
                        }

                        override fun onNothingSelected(parent: AdapterView<*>) {
                        }
                    }

                }else{
                    Toast.makeText(container!!.context, "Fail", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<Promotion>>, t: Throwable) {
                Toast.makeText(container!!.context,t.message, Toast.LENGTH_SHORT).show()
                println(t.message)
            }
        })

        var button_select_image = view.findViewById<Button>(R.id.btnSelectImage)
        button_select_image.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            intent.type = "image/*"
            imagePickerLauncher.launch(intent)
        }

        saveButton.setOnClickListener{
            submit(view)
        }

        // Inflate the layout for this fragment
        return view
    }

    private val imagePickerLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        var image_preview = view?.findViewById<ImageView>(R.id.image_preview)

        if (result.resultCode == Activity.RESULT_OK) {
            selectedImageUri = result.data?.data
            selectedImageUri?.let { uri: Uri ->
                Glide.with(this)
                    .load(uri)
                    .into(image_preview!!)
            }
            changeImage = true
        }
    }

    private fun setDefaultImage(view: View, imageUrl: String?)  {
        if (imageUrl !== null) {
            var image_preview = view?.findViewById<ImageView>(R.id.image_preview)
            Glide.with(this)
                .load(imageUrl)
                .into(image_preview!!)
        }
    }



    private fun submit(view: View) {
        if (changeImage) {
            selectedImageUri?.let { uri ->
                val storageRef = Firebase.storage.reference.child("uploads/${System.currentTimeMillis()}.jpg")
                storageRef.putFile(uri)
                    .addOnSuccessListener {
                        storageRef.downloadUrl.addOnSuccessListener { downloadUri ->

                            var apiProductAdminInstance: ProductAdminController = RetrofitInstance.getInstance().create(
                                ProductAdminController::class.java)

                            var nameInput = view.findViewById<TextInputLayout>(R.id.createName)
                            pName = nameInput.editText?.text.toString()

                            var priceInput = view.findViewById<TextInputLayout>(R.id.createUnitPrice)
                            pPrice = priceInput.editText?.text?.toString()?.toDoubleOrNull() ?: 0.0

                            var stockInput = view.findViewById<TextInputLayout>(R.id.createStock)
                            pStock = stockInput.editText?.text?.toString()?.toIntOrNull() ?: 0

                            var noteInput = view.findViewById<TextInputLayout>(R.id.createNote)
                            pNote = noteInput.editText?.text.toString()

                            var descriptionInput = view.findViewById<TextInputLayout>(R.id.createDescription)
                            pDescription = descriptionInput.editText?.text.toString()

                            pImageUrl = downloadUri.toString()

                            val newProduct = AdminProduct(null, pName!!, pPrice!!, pStock!!, pCategoryId!!, pDescription!!, pPromotionId, pNote, pImageUrl!!, pProduct.status, null)

                            apiProductAdminInstance.update(pProduct.id!!, newProduct).enqueue(object : Callback<AdminProduct> {
                                override fun onResponse(call: Call<AdminProduct>, response: Response<AdminProduct>) {
                                    if (response.isSuccessful) {

                                        Toast.makeText(requireContext(), "Cập nhật sản phẩm thành công", Toast.LENGTH_SHORT).show()

                                        val fragmentManager = (context as AppCompatActivity).supportFragmentManager
                                        fragmentManager.beginTransaction().replace(
                                            R.id.adminContainer,
                                            AdminProductList()
                                        ).commit()
                                    } else {
                                        Toast.makeText(requireContext(), "Cập nhật sản phẩm thất bại", Toast.LENGTH_SHORT).show()
                                    }
                                }

                                override fun onFailure(call: Call<AdminProduct>, t: Throwable) {
                                    Toast.makeText(requireContext(), "Cập nhật sản phẩm thất bại", Toast.LENGTH_SHORT).show()
                                }
                            })
                        }
                    }
                    .addOnFailureListener { exception ->
                        Toast.makeText(context, "Tải lên ảnh thất bại: ${exception.message}", Toast.LENGTH_SHORT).show()
                    }
            } ?: Toast.makeText(context, "Chưa chọn ảnh", Toast.LENGTH_SHORT).show()
        }
        else {
            var apiProductAdminInstance: ProductAdminController = RetrofitInstance.getInstance().create(
                ProductAdminController::class.java)

            var nameInput = view.findViewById<TextInputLayout>(R.id.createName)
            pName = nameInput.editText?.text.toString()

            var priceInput = view.findViewById<TextInputLayout>(R.id.createUnitPrice)
            pPrice = priceInput.editText?.text?.toString()?.toDoubleOrNull() ?: 0.0

            var stockInput = view.findViewById<TextInputLayout>(R.id.createStock)
            pStock = stockInput.editText?.text?.toString()?.toIntOrNull() ?: 0

            var noteInput = view.findViewById<TextInputLayout>(R.id.createNote)
            pNote = noteInput.editText?.text.toString()

            var descriptionInput = view.findViewById<TextInputLayout>(R.id.createDescription)
            pDescription = descriptionInput.editText?.text.toString()

            pImageUrl = pProduct.banner

            val newProduct = AdminProduct(null, pName!!, pPrice!!, pStock!!, pCategoryId!!, pDescription!!, pPromotionId, pNote, pImageUrl!!, pProduct.status, null)

            apiProductAdminInstance.update(pProduct.id!!, newProduct).enqueue(object : Callback<AdminProduct> {
                override fun onResponse(call: Call<AdminProduct>, response: Response<AdminProduct>) {
                    if (response.isSuccessful) {

                        Toast.makeText(requireContext(), "Cập nhật sản phẩm thành công", Toast.LENGTH_SHORT).show()

                        val fragmentManager = (context as AppCompatActivity).supportFragmentManager
                        fragmentManager.beginTransaction().replace(
                            R.id.adminContainer,
                            AdminProductList()
                        ).commit()
                    } else {
                        Toast.makeText(requireContext(), "Cập nhật sản phẩm thất bại", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<AdminProduct>, t: Throwable) {
                    Toast.makeText(requireContext(), "Cập nhật sản phẩm thất bại", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }

}
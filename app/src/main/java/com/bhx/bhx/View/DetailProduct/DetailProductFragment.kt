package com.bhx.bhx.View.DetailProduct

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bhx.bhx.Controller.CommentsController
import com.bhx.bhx.Controller.ProductController
import com.bhx.bhx.Controller.RetrofitInstance
import com.bhx.bhx.Model.Comments
import com.bhx.bhx.Global.ShoppingCart
import com.bhx.bhx.Global.UserInfo
import com.bhx.bhx.Model.Product
import com.bhx.bhx.R
import com.bhx.bhx.View.Comments.CommentsAdapter
import com.bhx.bhx.View.HomeFragment.ListProductAdapter
import com.bumptech.glide.Glide
import com.google.android.material.textfield.TextInputLayout
import okhttp3.MediaType
import okhttp3.RequestBody
import okhttp3.ResponseBody
import org.json.JSONObject
import com.google.android.material.snackbar.Snackbar
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.NumberFormat
import java.util.*
import kotlin.properties.Delegates

class DetailProductFragment(private val product: Product) : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    lateinit var revProperties: RecyclerView;
    lateinit var revComments: RecyclerView;
    lateinit var revRelatedProduct: RecyclerView;
    lateinit var adapter: PropertiesAdapter
    lateinit var adapterComments: CommentsAdapter
    lateinit var relatedProductAdapter: ListProductAdapter
    lateinit var btnBack: Button
    lateinit var tvNameProduct: TextView
    lateinit var tvPriceProduct: TextView
    lateinit var tvInfor: TextView
    lateinit var imgProduct:ImageView
    lateinit var btnBuy: Button;
    lateinit var btnSubmitCmt: Button
    lateinit var tiCmt: TextInputLayout
    lateinit var imgLike:ImageView
    var checkChange by Delegates.notNull<Boolean>()

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view: View = inflater.inflate(R.layout.fragment_detail_product, container, false)
        revProperties = view.findViewById(R.id.revProperties)
        revRelatedProduct = view.findViewById(R.id.revRelatedProduct)
        btnBack = view.findViewById(R.id.btnBack)
        tvNameProduct = view.findViewById(R.id.tvNameProduct)
        tvPriceProduct = view.findViewById(R.id.tvPrice)
        tvInfor = view.findViewById(R.id.tvInfor)
        imgProduct = view.findViewById(R.id.imgProduct)
        revComments = view.findViewById(R.id.revCmt)
        btnSubmitCmt = view.findViewById(R.id.btnSubmitCmt)
        tiCmt = view.findViewById(R.id.tiCmt)
        imgLike = view.findViewById(R.id.imgLike)

        checkChange = true

        imgLike.setOnClickListener {
            checkChange=!checkChange
            if(!checkChange)
                imgLike.setImageResource(R.drawable.heart_like)
            else
                imgLike.setImageResource(R.drawable.heart)
        }

        btnSubmitCmt.setOnClickListener {
            var str: String = tiCmt.editText?.text.toString()

            val user_id= UserInfo.getInstance().getUser()?.id

            if(user_id != null) {

                if(str!=null && !str.isEmpty()) {

                    val commentData = JSONObject().apply {
                        put("user_id", UserInfo.getInstance().getUser()!!.id)
                        put("comment_content", str)
                    }
                    val requestBody = RequestBody.create("application/json".toMediaType(), commentData.toString())
                    RetrofitInstance.getInstance().create(ProductController::class.java).postComment(product.id,requestBody).enqueue(object : Callback<ResponseBody>{
                        override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                            if (response.isSuccessful){
                                tiCmt.editText?.setText("")
                                callApiCmt(container)
                                Toast.makeText(context,"Bình luận thành công",Toast.LENGTH_LONG).show()
                            }else {
                            }
                        }

                        override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                        }

                    })
                }
            }


        }
        btnBuy = view.findViewById(R.id.btnBuy);

        Glide.with(container!!.context).load(product.banner).error(R.drawable.xoai).into(imgProduct)

        //call api
        RetrofitInstance.getInstance().create(ProductController::class.java).getDetailProduct(product.id).enqueue(object : Callback<Product>{
            override fun onResponse(call: Call<Product>, response: Response<Product>) {
                if (response.isSuccessful){
                    val product: Product? = response.body()
                    tvNameProduct.setText(product!!.name)
                    val formatter = NumberFormat.getCurrencyInstance(Locale("vi", "VN"));
                    formatter.currency = Currency.getInstance("VND");
                    tvPriceProduct.setText(formatter.format((product!!.unit_price)))
                    tvInfor.setText(product!!.general_description)
                    //attribute
                    adapter = PropertiesAdapter(product?.attribute_label, product?.attribute_value)
                    revProperties.adapter = adapter
                    revProperties.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
                    //related product
                    relatedProductAdapter = ListProductAdapter(product!!.related, container!!.context)
                    revRelatedProduct.adapter = relatedProductAdapter
                    revRelatedProduct.layoutManager = LinearLayoutManager(container.context, RecyclerView.HORIZONTAL, false)
                }else {
                }
            }

            override fun onFailure(call: Call<Product>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })

        callApiCmt(container)

        btnBack!!.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        btnBuy!!.setOnClickListener {
            ShoppingCart.getInstance().addItem(product);

            Snackbar.make(it, "Đã thêm ${product.name} vào giỏ hàng", 1000).show();
        }

        return view
    }

    fun callApiCmt(container:ViewGroup?) {
        RetrofitInstance.getInstance().create(CommentsController::class.java).getProductComments(product.id).enqueue(object : Callback<List<Comments>>{
            override fun onResponse(call: Call<List<Comments>>, response: Response<List<Comments>>) {
                if (response.isSuccessful){
                    val data = response.body()
                    val comments: List<Comments> = data as List<Comments>

                    adapterComments = CommentsAdapter(comments, product.id,container!!.context)
                    revComments.adapter = adapterComments
                    revComments.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)

                }else {
                }
            }

            override fun onFailure(call: Call<List<Comments>>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }


}

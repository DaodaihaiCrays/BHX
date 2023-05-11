package com.bhx.bhx.View.Comments

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bhx.bhx.Controller.ProductController
import com.bhx.bhx.Controller.RetrofitInstance
import com.bhx.bhx.Model.Comments
import com.bhx.bhx.Model.ReviewCategory
import com.bhx.bhx.R
import com.bhx.bhx.View.ProductOfCateFragment.ProductOfCateFragment
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class CommentsAdapter(private var comments: List<Comments>, private val id: Int,private val context: Context):
    RecyclerView.Adapter<CommentsAdapter.CommentsHolder>() {



    class CommentsHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvNameCmt: TextView = itemView.findViewById(R.id.tvNameCmt)
        val tvContent: TextView = itemView.findViewById(R.id.tvContent)
        val revSubCmt: RecyclerView = itemView.findViewById(R.id.revSubCmt)
        val tvRepCmt: TextView = itemView.findViewById(R.id.tvRepCmt)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CommentsHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.comments_item, parent, false)
        return CommentsHolder(view)
    }

    override fun onBindViewHolder(holder: CommentsHolder, @SuppressLint("RecyclerView") position: Int) {
        holder.tvNameCmt.text = comments[position].fullname
        holder.tvContent.text = comments[position].comment_content

        var adapter: SubCommentsAdapter

        adapter = SubCommentsAdapter(comments[position].replies, context)
        holder.revSubCmt.adapter = adapter
        holder.revSubCmt.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)

        holder.tvRepCmt.setOnClickListener {
            //rep cmt click
            val dialog = Dialog(context)
            dialog.setContentView(R.layout.dialog_replycmt_layout)
            dialog.setCancelable(false)
            val displayMetrics = context?.resources?.displayMetrics
            val screenWidth = displayMetrics?.widthPixels
            val width = (screenWidth?.times(1))?.toInt()
            if (width != null) {
                dialog.window?.setLayout(width, WindowManager.LayoutParams.WRAP_CONTENT)
            }

            val inputText = dialog.findViewById<TextInputLayout>(R.id.tiRepCmt)
            val closeButton = dialog.findViewById<Button>(R.id.btnBack)
            val submitButton = dialog.findViewById<Button>(R.id.btnSubmit)

            submitButton.setOnClickListener {
                var str: String = inputText.editText?.text.toString()

                if(str!=null && !str.isEmpty()) {
                    Log.i("test","str= " + str)
                    val commentData = JSONObject().apply {
                        put("user_id", 9)
                        put("comment_content", str)
                    }
                    val requestBody = RequestBody.create("application/json".toMediaType(), commentData.toString())
                    RetrofitInstance.getInstance().create(ProductController::class.java).postSubComment(id, comments[position].id,requestBody).enqueue(object :
                        Callback<ResponseBody> {
                        override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                            if (response.isSuccessful){
//                                callApiCmt(container)

                                comments[position].replies.add(Comments(0, "Hai", str, "", mutableListOf<Comments>()))
                                adapter.updateSubCommet(comments[position].replies)
                                Toast.makeText(context,"Bình luận thành công",Toast.LENGTH_LONG).show()
                                dialog.dismiss()
                            }else {
                            }
                        }

                        override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                        }

                    })
                }
            }

            closeButton.setOnClickListener {
                dialog.dismiss()
            }

            dialog.show()

        }
    }

    override fun getItemCount(): Int {
        //Log.i("test","cmt size: " + comments.size)
        return comments.size
    }

}
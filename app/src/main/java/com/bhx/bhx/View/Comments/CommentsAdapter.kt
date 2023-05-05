package com.bhx.bhx.View.Comments

import android.app.Dialog
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bhx.bhx.Model.Comments
import com.bhx.bhx.Model.ReviewCategory
import com.bhx.bhx.R
import com.bhx.bhx.View.ProductOfCateFragment.ProductOfCateFragment
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout


class CommentsAdapter(private val comments: List<Comments>, private val context: Context):
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

    override fun onBindViewHolder(holder: CommentsHolder, position: Int) {
        Log.i("test","cmt name: " + comments[position].comment_content + " - " + position.toString())
        holder.tvNameCmt.text = comments[position].fullname
        holder.tvContent.text = comments[position].comment_content

        var adapter: SubCommentsAdapter

        adapter = SubCommentsAdapter(comments[position].replies, context)
        holder.revSubCmt.adapter = adapter
        holder.revSubCmt.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)

        holder.tvRepCmt.setOnClickListener {
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
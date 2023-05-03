package com.bhx.bhx.View.Comments

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bhx.bhx.Model.Comments
import com.bhx.bhx.Model.ReviewCategory
import com.bhx.bhx.R
import com.bhx.bhx.View.ProductOfCateFragment.ProductOfCateFragment


class CommentsAdapter(private val comments: List<Comments>, private val context: Context):
    RecyclerView.Adapter<CommentsAdapter.CommentsHolder>() {

    class CommentsHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvNameCmt: TextView = itemView.findViewById(R.id.tvNameCmt)
        val tvContent: TextView = itemView.findViewById(R.id.tvContent)
        val revSubCmt: RecyclerView = itemView.findViewById(R.id.revSubCmt)
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
    }

    override fun getItemCount(): Int {
        //Log.i("test","cmt size: " + comments.size)
        return comments.size
    }

}
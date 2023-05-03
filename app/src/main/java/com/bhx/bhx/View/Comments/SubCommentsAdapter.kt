package com.bhx.bhx.View.Comments

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bhx.bhx.Model.Comments
import com.bhx.bhx.R

class SubCommentsAdapter(private val comments: List<Comments>, private val context: Context):
    RecyclerView.Adapter<SubCommentsAdapter.SubCommentsAdapterHolder>() {

    class SubCommentsAdapterHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvNameSubCmt: TextView = itemView.findViewById(R.id.tvNameSubCmt)
        val tvSubContent: TextView = itemView.findViewById(R.id.tvSubContent)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SubCommentsAdapterHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.sub_comments_item, parent, false)
        return SubCommentsAdapterHolder(view)
    }

    override fun onBindViewHolder(holder: SubCommentsAdapterHolder, position: Int) {
        Log.i(
            "test",
            "cmt name: " + comments[position].comment_content + " - " + position.toString()
        )
        holder.tvNameSubCmt.text = comments[position].fullname
        holder.tvSubContent.text = comments[position].comment_content
    }

    override fun getItemCount(): Int {

        return comments.size
    }
}

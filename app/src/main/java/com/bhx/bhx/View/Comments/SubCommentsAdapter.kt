package com.bhx.bhx.View.Comments

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bhx.bhx.Global.UserInfo
import com.bhx.bhx.Model.Comments
import com.bhx.bhx.R
import com.google.firebase.auth.FirebaseAuth

class SubCommentsAdapter(private var comments: List<Comments>, private val context: Context):
    RecyclerView.Adapter<SubCommentsAdapter.SubCommentsAdapterHolder>() {

    fun updateSubCommet(subComments: List<Comments>){
        comments = subComments
        notifyDataSetChanged()
    }
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

        if(FirebaseAuth.getInstance().currentUser!=null) {
            holder.tvNameSubCmt.text = UserInfo.getInstance().getUser()!!.fullname
            holder.tvSubContent.text = comments[position].comment_content
        }

    }

    override fun getItemCount(): Int {

        return comments.size
    }
}

package com.example.myapplication.callingapi.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.callingapi.R
import com.example.myapplication.callingapi.data.TestDataItem
import com.example.myapplication.callingapi.data.TestResponse

class PostAdapter(
    private val posts: TestResponse,
    private val onItemClick: (TestDataItem) -> Unit // Pass a lambda for item clicks
) : RecyclerView.Adapter<PostAdapter.PostViewHolder>() {

    class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.tvTitle)
        val body: TextView = itemView.findViewById(R.id.tvBody)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_post, parent, false)
        return PostViewHolder(view)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = posts[position]
        holder.title.text = post.title
        holder.body.text = post.body

        // Set click listener
        holder.itemView.setOnClickListener {
            onItemClick(post) // Pass the clicked item to the listener
        }
    }

    override fun getItemCount(): Int {
        return posts.size
    }
}

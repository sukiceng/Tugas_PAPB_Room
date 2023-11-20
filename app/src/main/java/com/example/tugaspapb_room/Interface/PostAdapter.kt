package com.example.tugaspapb_room.Interface

import android.annotation.SuppressLint
import androidx.recyclerview.widget.RecyclerView
import com.example.tugaspapb_room.Database.Posts
import com.example.tugaspapb_room.databinding.ItemPostBinding
import android.view.LayoutInflater
import android.view.ViewGroup


class PostAdapter(private var PostList: List<Posts>) :
    RecyclerView.Adapter<PostAdapter.ItemPostViewHolder>() {


    inner class ItemPostViewHolder(private val binding: ItemPostBinding) :
        RecyclerView.ViewHolder(binding.root) {

            init {
                binding.deleteButton.setOnClickListener {
                    val position = adapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        onDeleteClickListener?.onDeleteClick(PostList[position])
                    }
                }
            }

            fun bind(posts: Posts){
                with(binding){
                    postTitle.text = posts.title
                    postDescription.text = posts.description
                    datePosted.text= posts.date
                }
            }
        }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(newMovies: List<Posts>) {
        PostList = newMovies
        notifyDataSetChanged()
    }

    interface OnDeleteClickListener {
        fun onDeleteClick(posts: Posts)
    }

    private var onDeleteClickListener: OnDeleteClickListener? = null

    fun setOnDeleteClickListener(listener: OnDeleteClickListener) {
        onDeleteClickListener = listener
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemPostViewHolder {
        val binding = ItemPostBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
        return ItemPostViewHolder(binding)
    }

    override fun getItemCount(): Int = PostList.size

    override fun onBindViewHolder(holder: ItemPostViewHolder, position: Int) {
        holder.bind(PostList[position])
    }

}

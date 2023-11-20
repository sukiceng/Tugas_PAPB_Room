package com.example.tugaspapb_room.Interface

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tugaspapb_room.Database.Posts
import com.example.tugaspapb_room.Database.PostsRoomDatabase
import com.example.tugaspapb_room.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var listPostAdapter: PostAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView() // Mengatur RecyclerView untuk menampilkan daftar Posts.

        // Mengambil data dari database
        val db = PostsRoomDatabase.getDatabase(this)
        val postDao = db?.postDao()

        // Mengamati LiveData dan mengupdate RecyclerView ketika ada perubahan.
        val allPosts: LiveData<List<Posts>>? = postDao?.allNotes
        allPosts?.observe(this) { posts ->
            posts?.let { listPostAdapter.setData(it) }
        }

        // listener onDeleteClick di adapter untuk menghapus data.
        listPostAdapter.setOnDeleteClickListener(object : PostAdapter.OnDeleteClickListener {
            override fun onDeleteClick(posts: Posts) {
                // hapus data di latar belakang
                deletePostsInBackground(posts)
            }
        })

//        OnClickListener pada tombol addButton untuk menuju AddItemActivity.
        binding.addButton.setOnClickListener {
            val intent = Intent(this, AddItemActivity::class.java)
            startActivity(intent)
        }
    }

//    Menghapus data
    private fun deletePostsInBackground(posts: Posts) {
        val postDao = PostsRoomDatabase.getDatabase(this)?.postDao()
        postDao?.let {
            lifecycleScope.launch {
                withContext(Dispatchers.IO) {
                    // Mengakses database di latar belakang dan menghapus data.
                    it.delete(posts)
                }
            }
        }
    }

    private fun setupRecyclerView() {
        listPostAdapter = PostAdapter(emptyList())
        binding.rvPost.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = listPostAdapter
        }
    }
}
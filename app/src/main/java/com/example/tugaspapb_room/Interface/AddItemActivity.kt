package com.example.tugaspapb_room.Interface

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.SyncStateContract.Helpers.insert
import android.widget.Toast
import com.example.tugaspapb_room.Database.PostDao
import com.example.tugaspapb_room.Database.Posts
import com.example.tugaspapb_room.Database.PostsRoomDatabase
import com.example.tugaspapb_room.R
import com.example.tugaspapb_room.databinding.AddItemBinding
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


class AddItemActivity : AppCompatActivity() {
    private lateinit var binding: AddItemBinding
    private lateinit var postDao: PostDao
    private lateinit var executorService: ExecutorService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = AddItemBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Membuat executorService dengan satu thread untuk menjalankan operasi database
        executorService = Executors.newSingleThreadExecutor()

        // Mendapatkan instance dari basis data menggunakan metode singleton.
        val db = PostsRoomDatabase.getDatabase(this)
        if (db != null){
            postDao = db.postDao()!!
        }
//        Menambahkan onClickListener pada tombol saveButton.
        binding.saveButton.setOnClickListener {
            val title = binding.editPostTitle.text.toString()
            val description = binding.editPostDescription.text.toString()
            val date = binding.editPostDate.text.toString()
// Check input apakah kosong
            if (title.isNotEmpty() && description.isNotEmpty() && date.isNotEmpty()){
                val note = Posts(
                    title = title,
                    description = description,
                    date = date
                )
                insert(note) //masukkan data kedalam database
            } else {
// notifikasi jika input kosong
                Toast.makeText(applicationContext, "Invalid input", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun insert(posts: Posts) {
        // Menjalankan operasi penyisipan data di latar belakang menggunakan executorService.
        executorService.execute {
            // Memanggil fungsi insert dari Dao untuk menyisipkan data ke dalam tabel.
            postDao.insert(posts)
            setResult(RESULT_OK) // Mengatur hasil operasi sebagai RESULT_OK dan menutup activity saat ini.
            finish() // close activity
        }
    }
}
package com.example.tugaspapb_room.Database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update


@Dao
interface PostDao {
    // Menandai fungsi untuk menyisipkan (insert) data ke dalam tabel.
    // Jika ada konflik, data baru akan diabaikan.
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(note: Posts)

    // Menandai fungsi untuk memperbarui (update) data dalam tabel.
    @Update
    fun update(note: Posts)

    // Menandai fungsi untuk menghapus (delete) data dari tabel.
    @Delete
    fun delete(note: Posts)

    // Menandai properti yang mengembalikan LiveData<List<Posts>>.
    // LiveData akan memberi tahu pengamat (observer) jika ada perubahan data.
    // Query digunakan untuk mengambil semua catatan dari tabel Posts_table dan
    // mengurutkannya berdasarkan kolom 'id' secara berurutan (ASC).
    @get:Query("SELECT * from Posts_table ORDER BY id ASC")
    val allNotes: LiveData<List<Posts>>
}

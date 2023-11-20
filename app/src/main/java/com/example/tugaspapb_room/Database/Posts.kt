package com.example.tugaspapb_room.Database

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "Posts_table")
data class Posts(
    // Menandai primary key dari entitas dengan opsi untuk menghasilkan nilai secara otomatis.
    @PrimaryKey(autoGenerate = true)
    // Memberikan anotasi @NonNull untuk menandai bahwa id tidak boleh null.
    @NonNull
    val id: Int = 0,

    // Menandai kolom dengan nama "title" dalam tabel.
    @ColumnInfo(name = "title")
    val title: String,

    // Menandai kolom dengan nama "description" dalam tabel.
    @ColumnInfo(name = "description")
    val description: String,

    // Menandai kolom dengan nama "date_posted" dalam tabel.
    @ColumnInfo(name = "date_posted")
    val date: String
)


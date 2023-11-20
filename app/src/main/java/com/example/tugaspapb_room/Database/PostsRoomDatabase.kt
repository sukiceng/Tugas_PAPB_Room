package com.example.tugaspapb_room.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase


@Database(entities = [Posts::class], version = 2, exportSchema = false)
abstract class PostsRoomDatabase : RoomDatabase() {

    // Abstrak fungsi yang memberikan akses ke Dao terkait dengan entitas Posts.
    abstract fun postDao(): PostDao?

    companion object {
        @Volatile
        private var INSTANCE: PostsRoomDatabase? = null

        // Fungsi untuk mendapatkan instance tunggal dari basis data menggunakan metode singleton.
        fun getDatabase(context: Context): PostsRoomDatabase? {
            if (INSTANCE == null) {
                synchronized(PostsRoomDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        PostsRoomDatabase::class.java, "posts_database"
                    )
                        .addMigrations(MIGRATION_2_1)
                        .build()
                }
            }
            return INSTANCE
        }

        // Migrasi dari versi 2 ke versi 1.
        private val MIGRATION_2_1: Migration = object : Migration(2, 1) {
            override fun migrate(database: SupportSQLiteDatabase) {
                // Implementasikan logika migrasi jika diperlukan.
                // Migrasi ini akan dijalankan saat ada pembaruan versi basis data dari 2 ke 1.
                // Contoh: database.execSQL("ALTER TABLE nama_tabel ADD COLUMN nama_kolom INTEGER");
            }
        }
    }
}

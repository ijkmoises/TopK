package br.com.emesistemas.topk.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import br.com.emesistemas.topk.model.Item

@Database(
    entities = [Item::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getItemDao(): ItemDao
}


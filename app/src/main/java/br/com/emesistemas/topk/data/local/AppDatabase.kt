package br.com.emesistemas.topk.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import br.com.emesistemas.topk.model.Item
import br.com.emesistemas.topk.model.Owner
import br.com.emesistemas.topk.model.Repo

@Database(
    entities = [Item::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getItemDao(): ItemDao

    companion object {

        private lateinit var dbInstance: AppDatabase

        fun getDb(context: Context): AppDatabase {

            if (::dbInstance.isInitialized) return dbInstance

            synchronized(AppDatabase::class) {
                dbInstance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "topk.db"
                ).build()
            }

            return dbInstance
        }
    }
}


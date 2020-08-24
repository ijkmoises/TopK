package br.com.emesistemas.topk.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import br.com.emesistemas.topk.model.Item

@Dao
interface ItemDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(repoItems: List<Item>)

    @Query("SELECT * FROM Item WHERE page=:page")
    suspend fun getByPage(page: Int): List<Item>
}
package br.com.emesistemas.topk

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import br.com.emesistemas.topk.data.local.AppDatabase
import br.com.emesistemas.topk.data.local.ItemDao
import br.com.emesistemas.topk.model.Item
import br.com.emesistemas.topk.model.Owner
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

import java.io.IOException

@RunWith(AndroidJUnit4::class)
class ItemDaoTest {

    private lateinit var itemDao: ItemDao
    private lateinit var db: AppDatabase
    private val items: List<Item> = listOf(
        Item(id = 100, page = 1, owner = Owner(login = "John")),
        Item(id = 200, page = 1, owner = Owner(login = "Mark")),
        Item(id = 300, page = 1, owner = Owner(login = "Vany")),
        Item(id = 400, page = 2, owner = Owner(login = "Ness")),
        Item(id = 500, page = 2, owner = Owner(login = "Ghose")),
        Item(id = 600, page = 2, owner = Owner(login = "Bill"))
    )

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, AppDatabase::class.java
        ).build()
        itemDao = db.getItemDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }


    @Test
    @Throws(Exception::class)
    fun writeRepoItemsAndReadinList() {
        runBlocking {

            itemDao.insertAll(items)

            val itemsPage1 = itemDao.getByPage(1)

            assertThat(itemsPage1[0].id, equalTo(100))
            assertThat(itemsPage1[0].owner.login, equalTo("John"))

            assertThat(itemsPage1[1].id, equalTo(200))
            assertThat(itemsPage1[1].owner.login, equalTo("Mark"))


            val itemsPage2 = itemDao.getByPage(2)

            assertThat(itemsPage2[0].id, equalTo(400))
            assertThat(itemsPage2[0].owner.login, equalTo("Ness"))

            assertThat(itemsPage2[1].id, equalTo(500))
            assertThat(itemsPage2[1].owner.login, equalTo("Ghose"))
        }
    }

    @Test
    @Throws(Exception::class)
    fun testOnConflitReplaceWhenInsertDuplicatedRepoItems() {
        runBlocking {

            itemDao.insertAll(items)

            itemDao.insertAll(items)

            itemDao.insertAll(items)

            val itemsPage1 = itemDao.getByPage(1)
            assertThat(itemsPage1.size, equalTo(3))

            val itemsPage2 = itemDao.getByPage(2)
            assertThat(itemsPage2.size, equalTo(3))
        }
    }
}
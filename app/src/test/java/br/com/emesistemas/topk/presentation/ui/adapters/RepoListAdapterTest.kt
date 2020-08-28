package br.com.emesistemas.topk.presentation.ui.adapters

import android.content.Context
import br.com.emesistemas.topk.model.Item
import br.com.emesistemas.topk.model.Owner
import com.nhaarman.mockitokotlin2.*
import org.hamcrest.core.Is.`is`
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Test

class RepoListAdapterTest {

    private lateinit var mockContext: Context
    private lateinit var adapter: RepoListAdapter
    private val items: List<Item> = listOf(
        Item(id = 100, page = 1, owner = Owner(login = "John")),
        Item(id = 200, page = 1, owner = Owner(login = "Mark")),
        Item(id = 300, page = 1, owner = Owner(login = "Vany")),
        Item(id = 400, page = 2, owner = Owner(login = "Ness")),
        Item(id = 500, page = 2, owner = Owner(login = "Ghose")),
        Item(id = 600, page = 2, owner = Owner(login = "Bill"))
    )

    @Before
    fun setUp() {
        mockContext = mock()
        adapter = spy(RepoListAdapter(mockContext))
    }

    @Test
    fun `when adapter receive items should update dataSet`() {

        doNothing().`when`(adapter).notifyDataSetChanged()

        whenever(adapter.submitList(items)).then { adapter.notifyDataSetChanged()}

        adapter.submitList(items)

        verify(adapter).notifyDataSetChanged()

        val dataSetSize = adapter.itemCount

        assertThat(dataSetSize, `is`(6))
    }
}
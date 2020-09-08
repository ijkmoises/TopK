package br.com.emesistemas.topk.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import br.com.emesistemas.topk.R
import br.com.emesistemas.topk.data.local.RepoRepository
import br.com.emesistemas.topk.data.remote.Resource
import br.com.emesistemas.topk.data.remote.Status
import br.com.emesistemas.topk.model.Item
import br.com.emesistemas.topk.model.Repo
import com.nhaarman.mockitokotlin2.*
import kotlinx.coroutines.*
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import java.lang.Exception

@RunWith(JUnit4::class)
class RepoListViewModelTest {

    private lateinit var viewModel: RepoListViewModel
    private lateinit var repoRepository: RepoRepository
    private lateinit var repoObserver: Observer<Resource<Repo>>

    private val successResource =  Resource.success(Repo(listOf(Item(id = 1),Item(id = 2),Item(id = 3))))
    private val errorResource = Resource.error(R.string.unexpected_error,null)

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @ObsoleteCoroutinesApi
    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @ObsoleteCoroutinesApi
    @ExperimentalCoroutinesApi
    @Before
    fun setUp() {
        Dispatchers.setMain(mainThreadSurrogate)
        repoRepository =  mock()
        viewModel = RepoListViewModel(repoRepository)
        repoObserver = mock()
    }

    @ExperimentalCoroutinesApi
    @ObsoleteCoroutinesApi
    @After
    fun tearDown() {
        Dispatchers.resetMain()
        mainThreadSurrogate.close()
    }

    @Test
    fun `when fetchCached is called to load first page, then observer is updated with success`() = runBlocking {

        whenever(repoRepository.fetchCached(1)).thenReturn(successResource)

        viewModel.fetchListResult().observeForever(repoObserver)

        delay(10)
        verify(repoRepository).fetchCached(1)
        verify(repoObserver, timeout(50)).onChanged(successResource)

        assert(successResource.status == Status.SUCCESS)
    }

    @Test
    fun `when fetchRemote is called to load first page, then observer is updated with success`() = runBlocking {

        whenever(repoRepository.fetchRemote(1)).thenReturn(successResource)

        viewModel.fetchListResult().observeForever(repoObserver)

        delay(10)
        verify(repoRepository).fetchRemote(1)
        verify(repoObserver, timeout(50)).onChanged(successResource)

        assert(successResource.status == Status.SUCCESS)
    }

    @Test
    fun `when fetchRemote is called and some network error occurs then observer is updated with error`() = runBlocking {

        whenever(repoRepository.fetchRemote(1)).thenReturn(errorResource)

        viewModel.fetchListResult().observeForever(repoObserver)

        delay(10)
        verify(repoRepository).fetchRemote(1)
        verify(repoObserver, timeout(50)).onChanged(errorResource)

        assert(errorResource.status == Status.ERROR)
    }

}
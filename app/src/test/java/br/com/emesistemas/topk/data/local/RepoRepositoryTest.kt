package br.com.emesistemas.topk.data.local

import br.com.emesistemas.topk.R
import br.com.emesistemas.topk.data.remote.*
import br.com.emesistemas.topk.model.Item
import br.com.emesistemas.topk.model.Repo
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import retrofit2.HttpException

class RepoRepositoryTest {

    private lateinit var dao: ItemDao
    private lateinit var githubApi: GithubApi
    private val responseHandler = ResponseHandler()
    private lateinit var repository: RepoRepository
    private val items: List<Item> = listOf(Item(id = 1), Item(id = 2), Item(id = 3))
    private val repo = Repo(items)
    private val successResource = Resource.success(repo)
    private lateinit var errorResource : Resource<Repo>
    private lateinit var mockException : HttpException

    @Before
    fun setUp() {
        githubApi = mock()
        dao = mock()
        mockException = mock()
        repository = RepoRepository(dao, githubApi, responseHandler)
    }

    @Test
    fun `test fetchCached, then Repo items is returned`() {
        runBlocking {
            whenever(dao.getByPage(1)).thenReturn(items)
            assertEquals(successResource, repository.fetchCached(1))
        }
    }

    @Test
    fun `test fetchRemote when some page is requested, then Repo items is returned`() {
        runBlocking {
            whenever(githubApi.fetchKotlinRepositories(1)).thenReturn(repo)
            assertEquals(successResource, repository.fetchRemote(1))
        }
    }

    @Test
    fun `test fetchRemote when request limit exceded, then error 403 is returned`() {
        runBlocking {
            errorResource = Resource.error(R.string.rate_limit_exceeded, null)
            whenever(mockException.code()).thenReturn(FORBIDDEN)
            whenever(githubApi.fetchKotlinRepositories(1)).thenThrow(mockException)
            assertEquals(errorResource, repository.fetchRemote(1))
        }
    }

    @Test
    fun `test fetchRemote when internet is offline, then error -1 is returned`() {
        runBlocking {
            errorResource = Resource.error(R.string.internet_offline, null)
            whenever(mockException.code()).thenReturn(INTERNET_OFF)
            whenever(githubApi.fetchKotlinRepositories(1)).thenThrow(mockException)
            assertEquals(errorResource, repository.fetchRemote(1))
        }
    }

    @Test
    fun `test fetchRemote when slow connection, then timeout error is returned`() {
        runBlocking {
            errorResource = Resource.error(R.string.timeout, null)
            whenever(mockException.code()).thenReturn(TIMEOUT)
            whenever(githubApi.fetchKotlinRepositories(1)).thenThrow(mockException)
            assertEquals(errorResource, repository.fetchRemote(1))
        }
    }

    @Test
    fun `test fetchRemote request, then error 500 is returned`() {
        runBlocking {
            errorResource = Resource.error(R.string.internal_server_error, null)
            whenever(mockException.code()).thenReturn(INTERNAL_SERVER_ERROR)
            whenever(githubApi.fetchKotlinRepositories(1)).thenThrow(mockException)
            assertEquals(errorResource, repository.fetchRemote(1))
        }
    }

    @Test
    fun `test fetchRemote request, then error 503 is returned`() {
        runBlocking {
            errorResource = Resource.error(R.string.service_unavailable, null)
            whenever(mockException.code()).thenReturn(SERVICE_UNAVAILABLE)
            whenever(githubApi.fetchKotlinRepositories(1)).thenThrow(mockException)
            assertEquals(errorResource, repository.fetchRemote(1))
        }
    }
}
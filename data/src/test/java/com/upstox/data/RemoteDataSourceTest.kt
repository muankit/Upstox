package com.upstox.data

import com.upstox.data.datasource.RemoteDataSourceImpl
import com.upstox.data.models.Data
import com.upstox.data.models.HoldingsResponse
import com.upstox.data.models.UserHolding
import com.upstox.data.network.HoldingsApiService
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import retrofit2.Response

@OptIn(ExperimentalCoroutinesApi::class)
class RemoteDataSourceTest {
    private lateinit var remoteDataSource: RemoteDataSourceImpl
    private val apiService: HoldingsApiService = mock()
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        remoteDataSource = RemoteDataSourceImpl(apiService)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `getHoldings should return list of UserHolding when API call is successful`() = runTest {
        val mockHoldings = listOf(UserHolding("AAPL", 10))
        val mockResponse = Response.success(HoldingsResponse(Data(mockHoldings)))
        whenever(apiService.getHoldings()).thenReturn(mockResponse)

        val result = remoteDataSource.getHoldings()

        assertEquals(mockHoldings, result)
    }

    @Test
    fun `getHoldings should return empty list when API response body is null`() = runTest {
        val mockResponse = Response.success<HoldingsResponse>(null)
        whenever(apiService.getHoldings()).thenReturn(mockResponse)

        val result = remoteDataSource.getHoldings()

        assertTrue(result.isEmpty())
    }
}
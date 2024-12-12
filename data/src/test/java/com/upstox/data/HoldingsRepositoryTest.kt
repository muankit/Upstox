package com.upstox.data

import com.upstox.data.datasource.LocalDataSource
import com.upstox.data.datasource.RemoteDataSource
import com.upstox.data.models.UserHolding
import com.upstox.data.repository.HoldingsRepoImpl
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertThrows
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class HoldingsRepoImplTest {

    @Mock
    private lateinit var remoteDataSource: RemoteDataSource

    @Mock
    private lateinit var localDataSource: LocalDataSource

    private lateinit var holdingsRepo: HoldingsRepoImpl

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        holdingsRepo = HoldingsRepoImpl(remoteDataSource, localDataSource)
    }

    @Test
    fun `getHoldings should fetch from remote when fetchFromRemote is true`() = runBlocking {
        val remoteHoldings = listOf(
            UserHolding(symbol = "SBI", quantity = 100, ltp = 10.0, avgPrice = 200.0, close = 100.0),
            UserHolding(symbol = "HDFC", quantity = 10, ltp = 10.0, avgPrice = 20.0, close = 100.0),
        )
        `when`(remoteDataSource.getHoldings()).thenReturn(remoteHoldings)

        val result = holdingsRepo.getHoldings(fetchFromRemote = true)

        assertEquals(remoteHoldings, result)
    }

    @Test
    fun `getHoldings should fetch from local when fetchFromRemote is false`() = runBlocking {
        val localHoldings = listOf(
            UserHolding(symbol = "SBI", quantity = 100, ltp = 10.0, avgPrice = 200.0, close = 100.0),
            UserHolding(symbol = "HDFC", quantity = 10, ltp = 10.0, avgPrice = 20.0, close = 100.0),
        )
        `when`(localDataSource.getHoldings()).thenReturn(localHoldings)

        val result = holdingsRepo.getHoldings(fetchFromRemote = false)

        assertEquals(localHoldings, result)
    }

    @Test
    fun `getHoldings should fetch from remote when fetchFromRemote is true with exception`() = runBlocking {
        val exception = RuntimeException("Remote data source error")
        `when`(remoteDataSource.getHoldings()).thenThrow(exception)

        val thrown = assertThrows(RuntimeException::class.java) {
            runBlocking {
                holdingsRepo.getHoldings(fetchFromRemote = true)
            }
        }
        assertEquals("Remote data source error", thrown.message)
    }
}
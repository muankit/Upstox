package com.upstox.data

import com.upstox.data.datasource.LocalDataSourceImpl
import com.upstox.data.mapper.toUserHoldingList
import com.upstox.data.room.HoldingsDao
import com.upstox.data.room.HoldingsEntity
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

@OptIn(ExperimentalCoroutinesApi::class)
class LocalDataSourceTest {
    private lateinit var localDataSource: LocalDataSourceImpl
    private val holdingsDao: HoldingsDao = mock()
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        localDataSource = LocalDataSourceImpl(holdingsDao)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `getHoldings should return list of UserHolding when DB call is successful`() = runTest {
        val mockHoldingsEntityList = listOf(HoldingsEntity(
            id = 1,
            symbol = "SBI",
            quantity = 10,
            ltp = 100.0,
            avgPrice = 10.0,
            close = 200.0
        ))
        whenever(holdingsDao.getAllHoldings()).thenReturn(mockHoldingsEntityList)

        val result = localDataSource.getHoldings()

        assertEquals(mockHoldingsEntityList.toUserHoldingList(), result)
    }

    @Test
    fun `getHoldings should return empty list when DB returns no data`() = runTest {
        whenever(holdingsDao.getAllHoldings()).thenReturn(emptyList())

        val result = localDataSource.getHoldings()

        assertTrue(result.isEmpty())
    }
}
package com.upstox.data

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.upstox.data.room.HoldingsDao
import com.upstox.data.room.HoldingsDatabase
import com.upstox.data.room.HoldingsEntity
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@SmallTest
class HoldingDaoTest {

    private lateinit var database: HoldingsDatabase
    private lateinit var dao: HoldingsDao

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            HoldingsDatabase::class.java
        ).allowMainThreadQueries().build()

        dao = database.holdingsDao()
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun insertHoldings_returnsTrue() = runBlocking {
        val holdings = listOf(HoldingsEntity(
            id = 3696,
            symbol = "SBI",
            quantity = 100,
            ltp = 20.0,
            avgPrice = 100.0,
            close = 200.0
        ))
        dao.insertHoldings(holdings)

        val allHoldings = dao.getAllHoldings()
        assert(allHoldings.containsAll(holdings))

    }

    @Test
    fun getHoldings_returnsTrue() = runBlocking {
        val holdings = listOf(
            HoldingsEntity(
                id = 3696,
                symbol = "SBI",
                quantity = 100,
                ltp = 20.0,
                avgPrice = 100.0,
                close = 200.0
            ),
            HoldingsEntity(
                id = 3697,
                symbol = "HDFC",
                quantity = 50,
                ltp = 50.0,
                avgPrice = 120.0,
                close = 220.0
            )
        )
        dao.insertHoldings(holdings)

        val retrievedHoldings = dao.getAllHoldings()

        assert(retrievedHoldings.size == holdings.size)
        assert(retrievedHoldings.containsAll(holdings))
    }
}
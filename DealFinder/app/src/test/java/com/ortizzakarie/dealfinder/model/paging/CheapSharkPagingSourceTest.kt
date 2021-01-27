package com.ortizzakarie.dealfinder.model.paging

import androidx.paging.PagingSource
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.ortizzakarie.dealfinder.model.dataModels.GameListLookup
import com.ortizzakarie.dealfinder.model.repository.remote.api.CheapSharkApi
import org.junit.Before
import org.junit.Test
import java.util.function.Consumer

/**
 * Created by Zakarie Ortiz on 1/27/21.
 */

class CheapSharkPagingSourceTest {

    private val gameList = listOf(
        GameListLookup(gameID = "001", steamAppID = "001", cheapest = "0.99", cheapestDealID = "001", external = "CHEAPGAME", thumb = "link1"),
        GameListLookup(gameID = "002", steamAppID = "002", cheapest = "12.99", cheapestDealID = "002", external = "COOLGAME", thumb = "link2"),
        GameListLookup(gameID = "003", steamAppID = "003", cheapest = "15.99", cheapestDealID = "003", external = "BESTGAME", thumb = "link3")
    )

    private lateinit var source: CheapSharkPagingSource

    private val api: CheapSharkApi = mock()

    @Before
    fun `set up`() {
        source = CheapSharkPagingSource(api, "")
    }

    @Test
    fun `getGames`() {

    }


}
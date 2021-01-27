package com.ortizzakarie.dealfinder.model.paging

import com.nhaarman.mockitokotlin2.mock
import com.ortizzakarie.dealfinder.model.dataModels.GameListLookupResponse
import com.ortizzakarie.dealfinder.model.repository.remote.api.CheapSharkApi
import org.junit.Before
import org.junit.Test

/**
 * Created by Zakarie Ortiz on 1/27/21.
 */

class CheapSharkPagingSourceTest {

    private val gameList = listOf(
        GameListLookupResponse(gameID = "001", steamAppID = "001", cheapest = "0.99", cheapestDealID = "001", external = "CHEAPGAME", thumb = "link1"),
        GameListLookupResponse(gameID = "002", steamAppID = "002", cheapest = "12.99", cheapestDealID = "002", external = "COOLGAME", thumb = "link2"),
        GameListLookupResponse(gameID = "003", steamAppID = "003", cheapest = "15.99", cheapestDealID = "003", external = "BESTGAME", thumb = "link3")
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
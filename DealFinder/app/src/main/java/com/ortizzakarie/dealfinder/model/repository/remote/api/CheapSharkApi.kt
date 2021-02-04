package com.ortizzakarie.dealfinder.model.repository.remote.api

import com.ortizzakarie.dealfinder.model.dataModels.GameListLookupResponse
import com.ortizzakarie.dealfinder.model.dataModels.GameLookupResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Zakarie Ortiz on 1/11/21.
 */

interface CheapSharkApi {

    companion object {
        const val BASE_URL = "https://www.cheapshark.com/api/1.0/"
    }

    @GET("games")
    suspend fun searchGamesByTitle(
        @Query("title") title: String
    ): List<GameListLookupResponse>

    @GET("games")
    suspend fun searchGameDealsByGameId(
        @Query("id") id: Int
    ): Response<GameLookupResponse>

}
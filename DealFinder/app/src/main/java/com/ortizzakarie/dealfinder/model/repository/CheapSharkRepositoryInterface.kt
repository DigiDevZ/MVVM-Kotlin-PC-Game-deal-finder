package com.ortizzakarie.dealfinder.model.repository

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.ortizzakarie.dealfinder.model.dataModels.GameListLookupResponse
import com.ortizzakarie.dealfinder.model.dataModels.GameLookupResponse
import com.ortizzakarie.dealfinder.utils.Resource

/**
 * Created by Zakarie Ortiz on 1/15/21.
 */

/**
 * [CheapSharkRepositoryInterface] acts as an interface for both the true repository and "fake" testing repository.
 *
 * The purpose of this is so that whenever I add new functionality to the repository class,
 * I can add the functions here and create a test scenario to be able to test the repository functionality separately,
 * without needing to replicate the Api, network requests, and such, for testing.
 */

interface CheapSharkRepositoryInterface {

    fun searchGameByTitle(titleQuery: String): LiveData<PagingData<GameListLookupResponse>>

    suspend fun searchGameDetailsByGameId(idQuery: Int): Resource<GameLookupResponse>
}


package com.ortizzakarie.dealfinder.model.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagingData
import com.ortizzakarie.dealfinder.model.dataModels.GameListLookupResponse
import com.ortizzakarie.dealfinder.model.dataModels.GameLookupResponse
import com.ortizzakarie.dealfinder.utils.Resource

/**
 * Created by Zakarie Ortiz on 1/15/21.
 */

//TODO: Will be revisiting this class and testing setup when the SearchResultViewModel has more test cases,
// and I find out how to better test a repository by looking at others examples.


/**
 * [FakeCheapSharkRepository] is used for passing in a [CheapSharkRepository] to any viewModel that needs it.
 *
 */
class FakeCheapSharkRepository :
    CheapSharkRepositoryInterface {

    private val games = mutableListOf<GameListLookupResponse>()

    private val observableGames = MutableLiveData<List<GameListLookupResponse>>(games)
    private val pagedGames = MutableLiveData<PagingData<GameListLookupResponse>>()


    private var shouldReturnNetworkError = false

    fun setShouldReturnNetworkError(value: Boolean) {
        shouldReturnNetworkError = value
    }

    override fun searchGameByTitle(titleQuery: String): LiveData<PagingData<GameListLookupResponse>> {
        return pagedGames
    }

    override suspend fun searchGameDetailsByGameId(idQuery: Int): Resource<GameLookupResponse> {
        TODO("Not yet implemented")
    }


}
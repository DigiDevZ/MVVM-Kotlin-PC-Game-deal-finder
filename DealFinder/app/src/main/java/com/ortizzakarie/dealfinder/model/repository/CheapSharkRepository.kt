package com.ortizzakarie.dealfinder.model.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.ortizzakarie.dealfinder.model.dataModels.GameLookupResponse
import com.ortizzakarie.dealfinder.model.paging.CheapSharkPagingSource
import com.ortizzakarie.dealfinder.model.repository.remote.api.CheapSharkApi
import com.ortizzakarie.dealfinder.utils.Resource
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Zakarie Ortiz on 1/11/21.
 */
@Singleton
class CheapSharkRepository @Inject constructor(private val cheapSharkApi: CheapSharkApi) : CheapSharkRepositoryInterface {

    override fun searchGameByTitle(titleQuery: String) =
        Pager(
            config = PagingConfig(
                pageSize = 20,
                maxSize = 60,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { CheapSharkPagingSource(cheapSharkApi, titleQuery)}
        ).liveData


    override suspend fun searchGameDetailsByGameId(idQuery: Int): Resource<GameLookupResponse> {

        return try {
            val response = cheapSharkApi.searchGameDealsByGameId(idQuery)

            if (response.isSuccessful) {
                response.body()?.let {
                    return@let Resource.success(it)
                } ?: Resource.error("An unknown error occurred", null)
            } else {
                Resource.error("An unknown error occurred", null)
            }
        } catch (e: Exception) {
            Resource.error(e.localizedMessage ?: "Something went wrong", null)
        }
    }


}
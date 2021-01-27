package com.ortizzakarie.dealfinder.model.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.ortizzakarie.dealfinder.model.paging.CheapSharkPagingSource
import com.ortizzakarie.dealfinder.model.repository.remote.api.CheapSharkApi
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Zakarie Ortiz on 1/11/21.
 */
@Singleton
class CheapSharkRepository @Inject constructor(private val cheapSharkApi: CheapSharkApi) : CheapSharkRepositoryInterface {

    override fun searchGameByTitle(query: String) =
        Pager(
            config = PagingConfig(
                pageSize = 20,
                maxSize = 60,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { CheapSharkPagingSource(cheapSharkApi, query)}
        ).liveData


}
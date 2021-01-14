package com.ortizzakarie.dealfinder.model.paging

import androidx.paging.PagingSource
import com.ortizzakarie.dealfinder.model.dataModels.GameListLookup
import com.ortizzakarie.dealfinder.model.repository.remote.api.CheapSharkApi
import retrofit2.HttpException
import java.io.IOException

/**
 * Created by Zakarie Ortiz on 1/11/21.
 */
private const val CHEAPSHARK_STARTING_PAGE_INDEX = 1

class CheapSharkPagingSource(
    private val cheapSharkApi: CheapSharkApi,
    private val query: String
) : PagingSource<Int, GameListLookup>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GameListLookup> {

        val position = params.key ?: CHEAPSHARK_STARTING_PAGE_INDEX

        return try {
            val games = cheapSharkApi.searchGamesByTitle(query)

            LoadResult.Page(
                data = games,
                prevKey = if (position == CHEAPSHARK_STARTING_PAGE_INDEX) null else position - 1,
                nextKey = if (games.isEmpty()) null else position + 1
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }

    }

}
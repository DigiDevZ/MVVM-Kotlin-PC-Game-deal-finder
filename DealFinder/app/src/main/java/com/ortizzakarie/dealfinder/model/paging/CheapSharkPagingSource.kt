package com.ortizzakarie.dealfinder.model.paging

import android.util.Log
import androidx.paging.PagingSource
import com.ortizzakarie.dealfinder.model.dataModels.GameListLookupResponse
import com.ortizzakarie.dealfinder.model.repository.remote.api.CheapSharkApi
import retrofit2.HttpException
import java.io.IOException

/**
 * Created by Zakarie Ortiz on 1/11/21.
 */
private const val CHEAPSHARK_STARTING_PAGE_INDEX = 1


//TODO: Create a Unit Test for this class based on the stack overflow post i found.
class CheapSharkPagingSource(
    private val cheapSharkApi: CheapSharkApi,
    private val query: String
) : PagingSource<Int, GameListLookupResponse>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GameListLookupResponse> {

        val position = params.key ?: CHEAPSHARK_STARTING_PAGE_INDEX

        return try {
            val games = cheapSharkApi.searchGamesByTitle(query)

            LoadResult.Page(
                data = games,
                prevKey = if (position == CHEAPSHARK_STARTING_PAGE_INDEX) null else position - 1,
                nextKey = if (games.isEmpty()) null else position + 1
            )
        } catch (IOEx: IOException) {
            Log.d("CheapSharkPagingSource", "Failed to load pages, IO Exception: ${IOEx.message}")
            LoadResult.Error(IOEx)
        } catch (httpEx: HttpException) {
            Log.d(
                "CheapSharkPagingSource",
                "Failed to load pages, http Exception code: ${httpEx.code()}"
            )
            LoadResult.Error(httpEx)
        }

    }

}
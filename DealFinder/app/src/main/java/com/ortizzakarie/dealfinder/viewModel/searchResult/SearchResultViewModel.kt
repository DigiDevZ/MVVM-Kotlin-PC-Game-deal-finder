package com.ortizzakarie.dealfinder.viewModel.searchResult

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import androidx.paging.cachedIn
import com.ortizzakarie.dealfinder.model.repository.CheapSharkRepository

/**
 * Created by Zakarie Ortiz on 1/11/21.
 */
class SearchResultViewModel @ViewModelInject constructor(
    private val repository: CheapSharkRepository,
    @Assisted state: SavedStateHandle
) : ViewModel() {

    private val currentQuery = state.getLiveData(CURRENT_QUERY, DEFAULT_QUERY)

    val games = currentQuery.switchMap { queryString ->
        repository.getSearchResultsListGameLookup(queryString).cachedIn(viewModelScope)
    }

    fun searchGames(query: String) {
        currentQuery.value = query
    }

    companion object {
        private const val CURRENT_QUERY = "current_query"
        private const val DEFAULT_QUERY = "digimon"
    }
}
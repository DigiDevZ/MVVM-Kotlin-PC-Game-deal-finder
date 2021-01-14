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

    //currentQuery gets it's query string data from SavedStateHandle getLiveData,
    // this allows the search results & query to survive process death if the user leaves the app.
    private val currentQuery = state.getLiveData(CURRENT_QUERY, DEFAULT_QUERY)

    //games is a reactive val that is "activated" whenever currentQuery has it's values changed.
    // the following function will call the repository to view the search results of the currentQuery string.
    val games = currentQuery.switchMap { queryString ->


        repository.getSearchResultsListGameLookup(queryString).cachedIn(viewModelScope)
    }

    //searchGames is called whenever the user submits a search query.
    fun searchGames(query: String) {
        currentQuery.value = query
    }

    companion object {
        private const val CURRENT_QUERY = "current_query"

        //If default is left blank, it will provide an error.
        private const val DEFAULT_QUERY = ""
    }
}
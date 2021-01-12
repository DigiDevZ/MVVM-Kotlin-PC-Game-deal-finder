package com.ortizzakarie.dealfinder.viewModel.searchResult

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.ortizzakarie.dealfinder.model.repository.CheapSharkRepository

/**
 * Created by Zakarie Ortiz on 1/11/21.
 */
class SearchResultViewModel @ViewModelInject constructor(
    private val repository: CheapSharkRepository
) : ViewModel() {

    private val currentQuery = MutableLiveData(DEFAULT_QUERY)

    val games = currentQuery.switchMap { queryString ->
        repository.getSearchResultsListGameLookup(queryString).cachedIn(viewModelScope)
    }

    fun searchGames(query: String) {
        currentQuery.value = query
    }

    companion object {
        private const val DEFAULT_QUERY = "digimon"
    }
}
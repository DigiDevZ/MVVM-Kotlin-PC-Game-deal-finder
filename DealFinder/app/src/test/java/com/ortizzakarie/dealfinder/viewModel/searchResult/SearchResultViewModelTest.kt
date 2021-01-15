package com.ortizzakarie.dealfinder.viewModel.searchResult

import androidx.lifecycle.SavedStateHandle
import com.ortizzakarie.dealfinder.MainCoroutineRule
import com.ortizzakarie.dealfinder.model.repository.FakeCheapSharkRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

/**
 * Created by Zakarie Ortiz on 1/15/21.
 */
@ExperimentalCoroutinesApi
class SearchResultViewModelTest {

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var viewModel: SearchResultViewModel

    @Mock
    private lateinit var state: SavedStateHandle

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
//        viewModel = SearchResultViewModel(FakeCheapSharkRepository(), state)
    }

    @Test
    fun `search game by title returns results`() {

    }

}
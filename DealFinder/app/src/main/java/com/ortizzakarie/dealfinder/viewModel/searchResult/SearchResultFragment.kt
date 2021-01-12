package com.ortizzakarie.dealfinder.viewModel.searchResult

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.ortizzakarie.dealfinder.R
import com.ortizzakarie.dealfinder.databinding.FragmentSearchResultBinding
import com.ortizzakarie.dealfinder.model.dataModels.GameListLookup
import com.ortizzakarie.dealfinder.viewModel.searchResult.adapter.GameListAdapter
import com.ortizzakarie.dealfinder.viewModel.searchResult.adapter.GameListLoadStateAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_search_result.*
import kotlinx.android.synthetic.main.game_load_state_footer.*
import kotlinx.android.synthetic.main.game_load_state_footer.progress_bar_circular
import kotlinx.android.synthetic.main.game_load_state_footer.tv_loadError

/**
 * Created by Zakarie Ortiz on 1/11/21.
 */
@AndroidEntryPoint
class SearchResultFragment : Fragment(R.layout.fragment_search_result), GameListAdapter.OnItemClickListener {

    private val viewModel by viewModels<SearchResultViewModel>()

    private var _binding: FragmentSearchResultBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentSearchResultBinding.bind(view)

        val adapter = GameListAdapter(this)

        binding.apply {
            rvGames.setHasFixedSize(true)
            rvGames.itemAnimator = null
            rvGames.adapter = adapter.withLoadStateHeaderAndFooter(
                header = GameListLoadStateAdapter { adapter.retry() },
                footer = GameListLoadStateAdapter { adapter.retry() }
            )

            btnRetry.setOnClickListener {
                adapter.retry()
            }
        }

        viewModel.games.observe(viewLifecycleOwner) {
            adapter.submitData(viewLifecycleOwner.lifecycle, it)
        }

        adapter.addLoadStateListener { loadState ->
            binding.apply {
                progressBarCircular.isVisible = loadState.source.refresh is LoadState.Loading
                rvGames.isVisible = loadState.source.refresh is LoadState.NotLoading
                btnRetry.isVisible = loadState.source.refresh is LoadState.Error
                tvLoadError.isVisible = loadState.source.refresh is LoadState.Error
                ivLoadError.isVisible = loadState.source.refresh is LoadState.Error

                //Empty recycler view handling.
                if (loadState.source.refresh is LoadState.NotLoading &&
                    loadState.append.endOfPaginationReached && adapter.itemCount < 1) {
                    rvGames.isVisible = false
                    tvLoadError.isVisible = true
                    ivLoadError.isVisible = true
                } else {
                    tvLoadError.isVisible = false
                    ivLoadError.isVisible = false
                }
            }
        }

        setHasOptionsMenu(true)
    }

    override fun OnItemClick(game: GameListLookup) {
        val action = SearchResultFragmentDirections.actionSearchResultFragmentToGameDetailsFragment(game, game.external)
        findNavController().navigate(action)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

        inflater.inflate(R.menu.menu_search, menu)

        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    binding.rvGames.scrollToPosition(0)
                    viewModel.searchGames(query)
                    searchView.clearFocus()
                }

                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }

        })
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }



}

//TODO: - Then go to episode 6 to complete the viewmodel portion.
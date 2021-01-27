package com.ortizzakarie.dealfinder.viewModel.searchResult

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.ortizzakarie.dealfinder.R
import com.ortizzakarie.dealfinder.databinding.FragmentSearchResultBinding
import com.ortizzakarie.dealfinder.model.dataModels.GameListLookupResponse
import com.ortizzakarie.dealfinder.utils.ValidationUtil
import com.ortizzakarie.dealfinder.viewModel.searchResult.adapter.GameListAdapter
import com.ortizzakarie.dealfinder.viewModel.searchResult.adapter.GameListLoadStateAdapter
import com.ortizzakarie.dealfinder.viewModel.searchResult.searchView.EmptySubmitSearchView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.item_game_listing.*

/**
 * Created by Zakarie Ortiz on 1/11/21.
 */
@AndroidEntryPoint
class SearchResultFragment : Fragment(R.layout.fragment_search_result), GameListAdapter.OnItemClickListener {

    //In the future if I add more SearchResultFragments I will call this one GameSearchResultFragment.
    //TODO: Get rid or rename below TAG.
    private val TAG = "SRFragment.TAG"
    
    private val viewModel by viewModels<SearchResultViewModel>()

    //TODO: Need to doc out the specific reasoning for this binding var and val to be like this.
    private var _binding: FragmentSearchResultBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentSearchResultBinding.bind(view)

        val adapter = GameListAdapter(this)

        binding.apply {
            rvGames.setHasFixedSize(true)
            rvGames.itemAnimator = null
            //Gives custom adapter to the header and footer of the recycler view, utilizing LoadStateAdapter.
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

        //Load State listener so that the user can be informed when data fetching is happening, and if an error occurs a retry button can be displayed.
        adapter.addLoadStateListener { loadState ->
            binding.apply {
                progressBarCircular.isVisible = loadState.source.refresh is LoadState.Loading
                rvGames.isVisible = loadState.source.refresh is LoadState.NotLoading
                btnRetry.isVisible = loadState.source.refresh is LoadState.Error
                tvLoadError.isVisible = loadState.source.refresh is LoadState.Error
                ivLoadError.isVisible = loadState.source.refresh is LoadState.Error

                //Loading error handling.
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

    override fun onItemClick(game: GameListLookupResponse) {

        val extras = FragmentNavigatorExtras(
            iv_gameThumbnail to game.thumb,
            tv_gameTitle to game.external
        )

        val action = SearchResultFragmentDirections.actionSearchResultFragmentToGameDetailsFragment(game = game, gameTitle = game.external)
        findNavController().navigate(action, extras)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

        inflater.inflate(R.menu.menu_search, menu)

        val searchItem = menu.findItem(R.id.action_search)
        val customSearchView = searchItem.actionView as EmptySubmitSearchView

        //Implemented custom search view class [EmptySubmitSearchView] to handle search queries that are empty.
        customSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {

                    if (!ValidationUtil.validateSearchQuery(query)) {
                        displayToast(getString(R.string.search_empty_search_field), Toast.LENGTH_SHORT)
                        customSearchView.clearFocus()
                    }else {
                        binding.rvGames.scrollToPosition(0)
                        viewModel.searchForGames(query)
                        customSearchView.clearFocus()
                    }
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

    /**
     * Display a UX message to the user.
     *
     * @param _text = message to display to user
     * @param _duration = how long to display to user, only two options [Toast.LENGTH_LONG] & [Toast.LENGTH_SHORT]
     */

    private fun displayToast(_text: String, _duration: Int) {
        Log.i(TAG, "displayToast: Toast displaying.")
        val toast = Toast.makeText(requireContext(), _text, _duration)
        toast.show()
    }



}

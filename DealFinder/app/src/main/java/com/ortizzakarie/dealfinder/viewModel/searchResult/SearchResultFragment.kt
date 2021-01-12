package com.ortizzakarie.dealfinder.viewModel.searchResult

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import com.ortizzakarie.dealfinder.R
import com.ortizzakarie.dealfinder.databinding.FragmentSearchResultBinding
import com.ortizzakarie.dealfinder.viewModel.searchResult.adapter.GameListAdapter
import dagger.hilt.android.AndroidEntryPoint

/**
 * Created by Zakarie Ortiz on 1/11/21.
 */
@AndroidEntryPoint
class SearchResultFragment : Fragment(R.layout.fragment_search_result) {

    private val viewModel by viewModels<SearchResultViewModel>()

    private var _binding: FragmentSearchResultBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentSearchResultBinding.bind(view)

        val adapter = GameListAdapter()

        binding.apply {
            rvGames.setHasFixedSize(true)
            rvGames.adapter = adapter
        }

        viewModel.games.observe(viewLifecycleOwner) {
            adapter.submitData(viewLifecycleOwner.lifecycle, it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}

//TODO: - Then go to episode 6 to complete the viewmodel portion.
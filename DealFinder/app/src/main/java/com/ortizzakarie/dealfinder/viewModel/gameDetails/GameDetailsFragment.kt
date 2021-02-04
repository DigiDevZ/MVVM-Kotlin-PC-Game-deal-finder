package com.ortizzakarie.dealfinder.viewModel.gameDetails

import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.transition.TransitionInflater
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.ortizzakarie.dealfinder.R
import com.ortizzakarie.dealfinder.databinding.FragmentGameDetailsBinding
import com.ortizzakarie.dealfinder.model.dataModels.GameLookupResponse
import com.ortizzakarie.dealfinder.utils.Status
import com.ortizzakarie.dealfinder.viewModel.gameDetails.adapter.DealListAdapter
import dagger.hilt.android.AndroidEntryPoint

/**
 * Created by Zakarie Ortiz on 1/12/21.
 */
@AndroidEntryPoint
class GameDetailsFragment : Fragment(R.layout.fragment_game_details),
    DealListAdapter.OnItemClickListener {

    private val args by navArgs<GameDetailsFragmentArgs>()

    private val viewModel by viewModels<GameDetailsViewModel>()


    private var _binding: FragmentGameDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition =
            TransitionInflater.from(context).inflateTransition(android.R.transition.move)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentGameDetailsBinding.bind(view)

        val dealsAdapter = DealListAdapter(this)

        binding.apply {
            val game = args.game

            rvDeals.setHasFixedSize(true)
            rvDeals.itemAnimator = null
            rvDeals.adapter = dealsAdapter
            rvDeals.addItemDecoration(
                DividerItemDecoration(
                    context,
                    LinearLayoutManager.VERTICAL
                )
            )

            ivGameThumbnail.apply {
                transitionName = game.thumb

                Glide.with(this@GameDetailsFragment)
                    .load(game.thumb)
                    .error(R.drawable.ic_error_outline_24)
                    .listener(object : RequestListener<Drawable> {
                        override fun onLoadFailed(
                            e: GlideException?,
                            model: Any?,
                            target: Target<Drawable>?,
                            isFirstResource: Boolean
                        ): Boolean {
                            progressBarCircular.isVisible = false
                            return false
                        }

                        override fun onResourceReady(
                            resource: Drawable?,
                            model: Any?,
                            target: Target<Drawable>?,
                            dataSource: DataSource?,
                            isFirstResource: Boolean
                        ): Boolean {
                            progressBarCircular.isVisible = false
                            tvGameTitle.isVisible = true
                            return false
                        }

                    })
                    .into(this)
            }

            tvGameTitle.text = game.external
            tvGameTitle.transitionName = game.external

            viewModel.searchForGameDetails(game.gameID.toInt())


            viewModel.gameDetails.observe(viewLifecycleOwner) {
                if (it.status == Status.SUCCESS) {
                    setProgressBarVisibility(progressBarCircular, View.GONE)

                    val gameDetailsResponse = it.data
                    if (gameDetailsResponse != null) { //Assign the fragment UIs data here, where the gameDetailsResponse has valid data.
                        val retailPriceMessage =
                            "Retail Price: $" + gameDetailsResponse.deals[0].retailPrice
                        tvGameRetailPrice.text = retailPriceMessage
                        tvGameRetailPrice.visibility = View.VISIBLE


                        //Assign the cleaned deals data to the adapter.
                        val cleanedDeals =
                            viewModel.checkGameDetailDealsForFalseDeals(gameDetailsResponse.deals as MutableList<GameLookupResponse.GameDealResponse>)
                        dealsAdapter.submitList(cleanedDeals)

                        val dealCountMessage =
                            cleanedDeals.count().toString() + " deals found"
                        tvGameDealCount.text = dealCountMessage
                        tvGameDealCount.visibility = View.VISIBLE
                    }

                } else if (it.status == Status.LOADING) {
                    setProgressBarVisibility(progressBarCircular, View.VISIBLE)
                } else if (it.status == Status.ERROR) {
                    Log.i("GDFragment.TAG", "onViewCreated: response error")
                    //TODO: Display retry button w/error UI for attempting to reconnect.
                }

            }
        }

    }

    override fun onItemClick(deal: GameLookupResponse.GameDealResponse) {
        intentToDealPurchase(deal.dealID)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    //Will redirect user out of app and to their preferred browser to potentially purchase the game.
    private fun intentToDealPurchase(dealID: String) {
        val dealLink = "https://www.cheapshark.com/redirect?dealID=$dealID"
        val dealUri = Uri.parse(dealLink)

        val intent = Intent(Intent.ACTION_VIEW, dealUri)

        context?.startActivity(intent)
    }

    private fun setProgressBarVisibility(view: ProgressBar, visibility: Int) {
        view.visibility = visibility
    }

}
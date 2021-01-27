package com.ortizzakarie.dealfinder.viewModel.gameDetails

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.transition.TransitionInflater
import android.util.Log
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.ortizzakarie.dealfinder.R
import com.ortizzakarie.dealfinder.databinding.FragmentGameDetailsBinding
import com.ortizzakarie.dealfinder.utils.Status
import dagger.hilt.android.AndroidEntryPoint

/**
 * Created by Zakarie Ortiz on 1/12/21.
 */
@AndroidEntryPoint
class GameDetailsFragment : Fragment(R.layout.fragment_game_details) {

    private val args by navArgs<GameDetailsFragmentArgs>()

    private val viewModel by viewModels<GameDetailsViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition =
            TransitionInflater.from(context).inflateTransition(android.R.transition.move)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentGameDetailsBinding.bind(view)

        binding.apply {
            val game = args.game

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
                    Log.i("GDFragment.TAG", "onViewCreated: response success")
                    val gameDetailsResponse = it.data
                    if (gameDetailsResponse != null) {
                        Log.i("GDFragment.TAG", "onViewCreated: response data valid")
                        val retailPriceMessage = "$" + gameDetailsResponse.deals[0].retailPrice.toString()
                        tvGameRetailPrice.text = retailPriceMessage
                        tvGameRetailPrice.visibility = View.VISIBLE

                        val dealCountMessage = gameDetailsResponse.deals.count().toString() + " deals found"
                        tvGameDealCount.text = dealCountMessage
                        tvGameDealCount.visibility = View.VISIBLE

                        //TODO: PRIMARY, Implement RecyclerView for game deals.
                        // Look into what will be displayed in the cell, and then if any other data is needed.
                    }

                } else if (it.status == Status.LOADING) {
                    Log.i("GDFragment.TAG", "onViewCreated: response loading")
                    //TODO: Display progress bar.
                } else if (it.status == Status.ERROR) {
                    Log.i("GDFragment.TAG", "onViewCreated: response error")
                    //TODO: Display retry button w/error UI for attempting to reconnect.
                }

            }
        }



    }

}
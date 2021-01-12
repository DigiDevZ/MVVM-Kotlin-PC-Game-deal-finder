package com.ortizzakarie.dealfinder.viewModel.gameDetails

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.ortizzakarie.dealfinder.R
import com.ortizzakarie.dealfinder.databinding.FragmentGameDetailsBinding

/**
 * Created by Zakarie Ortiz on 1/12/21.
 */
class GameDetailsFragment : Fragment(R.layout.fragment_game_details) {

    private val args by navArgs<GameDetailsFragmentArgs>()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentGameDetailsBinding.bind(view)

        binding.apply {
            val game = args.game

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
                .into(ivGameThumbnail)

            tvGameTitle.text = game.external
        }
    }

}
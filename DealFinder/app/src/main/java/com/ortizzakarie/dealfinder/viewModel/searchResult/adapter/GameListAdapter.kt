package com.ortizzakarie.dealfinder.viewModel.searchResult.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.ortizzakarie.dealfinder.R
import com.ortizzakarie.dealfinder.databinding.ItemGameListingBinding
import com.ortizzakarie.dealfinder.model.dataModels.GameListLookup

/**
 * Created by Zakarie Ortiz on 1/11/21.
 */

/**
 * [GameListAdapter] is extended from [PagingDataAdapter] and displays the custom GameListLookup item.
 *
 * @param listener = whatever fragment uses this adapter must also implement this listener.
 *
 */

class GameListAdapter(private val listener: OnItemClickListener)  : PagingDataAdapter<GameListLookup, GameListAdapter.GameViewHolder>(
    GAME_COMPARATOR
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameViewHolder {
        val binding =
            ItemGameListingBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return GameViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GameViewHolder, position: Int) {
        val currentItem = getItem(position)

        if (currentItem != null) {
            holder.bind(currentItem)
        }
    }

    inner class GameViewHolder(private val binding: ItemGameListingBinding) :
        RecyclerView.ViewHolder(binding.root) {

        //On init set the onClickListener for the entire root layout.
        // This layout corresponds to the GameItem in the search results fragment.
        init {
            binding.root.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val item = getItem(position)
                    if (item != null) {
                        listener.onItemClick(item)
                    }
                }
            }
        }

        fun bind(game: GameListLookup) {
            binding.apply {

                Glide.with(itemView)
                    .load(game.thumb)
                    .centerCrop()
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .error(R.drawable.ic_cloud_off_24)
                    .into(ivGameThumbnail)

                tvGameTitle.text = game.external
                tvGameDiscountPrice.text = game.cheapest

                //TODO: Change the two below when I figure out how to update and get that specific data.
                tvGameRetailPrice.isVisible = false
                tvGameDiscountPercentage.isVisible = false
//                tvGameRetailPrice.text = "DEV 404"
//                tvGameDiscountPercentage.text = "DEV 404"
            }
        }

    }

    //OnItemClickListener to send game data for navigation.
    interface OnItemClickListener {
        fun onItemClick(game: GameListLookup)
    }

    companion object {
        //All adapters extending PagingDataAdapter must provide their own COMPARATOR object.
        private val GAME_COMPARATOR = object : DiffUtil.ItemCallback<GameListLookup>() {

            override fun areItemsTheSame(oldItem: GameListLookup, newItem: GameListLookup) =
                oldItem.gameID == newItem.gameID

            override fun areContentsTheSame(oldItem: GameListLookup, newItem: GameListLookup) =
                oldItem == newItem
        }
    }


}
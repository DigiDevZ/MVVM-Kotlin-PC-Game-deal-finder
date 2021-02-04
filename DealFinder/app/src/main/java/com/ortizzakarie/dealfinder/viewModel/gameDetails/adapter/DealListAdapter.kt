package com.ortizzakarie.dealfinder.viewModel.gameDetails.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ortizzakarie.dealfinder.databinding.ItemDealListingBinding
import com.ortizzakarie.dealfinder.model.dataModels.GameLookupResponse

/**
 * Created by Zakarie Ortiz on 2/4/21.
 */
class DealListAdapter(private val listener: OnItemClickListener) :
    ListAdapter<GameLookupResponse.GameDealResponse, DealListAdapter.DealViewHolder>(
        DEAL_COMPARATOR
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DealViewHolder {
        val binding =
            ItemDealListingBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return DealViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DealViewHolder, position: Int) {
        val currentItem = getItem(position)

        if (currentItem != null) {
            holder.bind(currentItem)
        }
    }


    inner class DealViewHolder(private val binding: ItemDealListingBinding) :
        RecyclerView.ViewHolder(binding.root) {

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

        fun bind(deal: GameLookupResponse.GameDealResponse) {
            binding.apply {

                val dealPriceMessage = "$" + deal.price
                tvDealPrice.text = dealPriceMessage

                val dealSavingsMessage =
                    "Save " + deal.savings.substringBefore(".", deal.savings) + "%"
                tvDealSavings.text = dealSavingsMessage
            }
        }

    }

    //OnItemClickListener to send deal data for redirection to website.
    interface OnItemClickListener {
        fun onItemClick(deal: GameLookupResponse.GameDealResponse)
    }

    companion object {
        private val DEAL_COMPARATOR =
            object : DiffUtil.ItemCallback<GameLookupResponse.GameDealResponse>() {

                override fun areItemsTheSame(
                    oldItem: GameLookupResponse.GameDealResponse,
                    newItem: GameLookupResponse.GameDealResponse
                ) =
                    oldItem.dealID == newItem.dealID


                override fun areContentsTheSame(
                    oldItem: GameLookupResponse.GameDealResponse,
                    newItem: GameLookupResponse.GameDealResponse
                ) =
                    oldItem == newItem
            }
    }


}



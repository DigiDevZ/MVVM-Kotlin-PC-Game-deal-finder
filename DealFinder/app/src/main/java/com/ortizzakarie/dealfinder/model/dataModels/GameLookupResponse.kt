package com.ortizzakarie.dealfinder.model.dataModels

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by Zakarie Ortiz on 1/10/21.
 */
@Parcelize
data class GameLookupResponse(
    val info: GameInfoResponse,
    val cheapestPriceEver: GameCheapestPriceEverResponse,
    val deals: List<GameDealResponse>
) : Parcelable {

    @Parcelize
    data class GameInfoResponse(
        val title: String,
        val steamAppID: String
    ) : Parcelable

    @Parcelize
    data class GameCheapestPriceEverResponse(
        val price: String,
        val date: Int
    ) : Parcelable

    @Parcelize
    data class GameDealResponse(
        val storeID: String,
        val dealID: String,
        val price: String,
        val retailPrice: String,
        val savings: String
    ) : Parcelable

}
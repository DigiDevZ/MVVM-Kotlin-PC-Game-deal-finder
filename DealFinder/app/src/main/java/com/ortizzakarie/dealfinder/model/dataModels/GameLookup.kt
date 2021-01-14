package com.ortizzakarie.dealfinder.model.dataModels

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by Zakarie Ortiz on 1/10/21.
 */
@Parcelize
data class GameLookup(
    val info: GameInfo,
    val cheapestPriceEver: GameCheapestPriceEver,
    val deals: GameDeal
) : Parcelable {

    @Parcelize
    data class GameInfo(
        val title: String,
        val steamAppID: String
    ) : Parcelable

    @Parcelize
    data class GameCheapestPriceEver(
        val price: String,
        val date: Int
    ) : Parcelable

    @Parcelize
    data class GameDeal(
        val storeID: String,
        val dealID: String,
        val price: String,
        val retailPrice: String,
        val savings: String
    ) : Parcelable

    val gameBasicDetails get() = "Title: ${info.title} \nCheapest Price Ever: ${cheapestPriceEver.price}"

}
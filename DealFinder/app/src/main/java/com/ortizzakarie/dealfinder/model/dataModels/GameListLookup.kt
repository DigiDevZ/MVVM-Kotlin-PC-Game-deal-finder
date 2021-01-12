package com.ortizzakarie.dealfinder.model.dataModels

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by Zakarie Ortiz on 1/11/21.
 */
@Parcelize
data class GameListLookup(
    val gameID: String,
    val steamAppID: String,
    val cheapest: String,
    val cheapestDealID: String,
    val external: String,
    val thumb: String
) : Parcelable {
}
package com.ortizzakarie.dealfinder.model.repository.remote.response

import com.ortizzakarie.dealfinder.model.dataModels.GameListLookup

/**
 * Created by Zakarie Ortiz on 1/11/21.
 */
data class GameListResponse(
    val objects: List<GameListLookup>
)
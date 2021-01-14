package com.ortizzakarie.dealfinder.model.repository.remote.response

import com.ortizzakarie.dealfinder.model.dataModels.GameListLookup

/**
 * Created by Zakarie Ortiz on 1/11/21.
 */
//TODO: May not need this class. Look into deletion.
data class GameListResponse(
    val objects: List<GameListLookup>
)
package com.ortizzakarie.dealfinder.model.repository

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.ortizzakarie.dealfinder.model.dataModels.GameListLookup
import com.ortizzakarie.dealfinder.utils.Resource

/**
 * Created by Zakarie Ortiz on 1/15/21.
 */

/**
 * [CheapSharkRepositoryInterface] acts as an interface for both the true repository and "fake" testing repository.
 *
 * The purpose of this is so that whenever I add new functionality to the repository class,
 * I can add the functions here and create a test scenario to be able to test the repository functionality separately,
 * without needing to replicate the Api, network requests, and such, for testing.
 */

interface CheapSharkRepositoryInterface {

    //I don't like that I am returning Any here,
    // If there is a way for me to setup this RepositoryInterface and it to have better testing with my project than  I will find it.
    fun searchGameByTitle(query: String): Any
}


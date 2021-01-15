package com.ortizzakarie.dealfinder.model.repository

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

    fun searchGameByTitle(query: String) : Any

}
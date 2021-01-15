package com.ortizzakarie.dealfinder.utils

/**
 * Created by Zakarie Ortiz on 1/14/21.
 */
object ValidationUtil {


    /**
     * Test cases
     *
     * The input is not valid if
     *  - the query is blank or empty
     */

    fun validateSearchQuery(query: String) : Boolean {

        return !(query.isEmpty() || query.isBlank())
    }
}
package com.ortizzakarie.dealfinder

import com.google.common.truth.Truth.assertThat
import com.ortizzakarie.dealfinder.utils.ValidationUtil
import org.junit.Test

/**
 * Created by Zakarie Ortiz on 1/14/21.
 */
class ValidationUtilTest {

    @Test
    fun `empty query returns false`() {
        val result = ValidationUtil.validateSearchQuery("")

        assertThat(result).isFalse()
    }

    @Test
    fun `valid query returns true`() {
        val result = ValidationUtil.validateSearchQuery("Games")

        assertThat(result).isTrue()
    }

}
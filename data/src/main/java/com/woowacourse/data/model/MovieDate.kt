package com.woowacourse.data.model

typealias DataMovieDate = MovieDate

data class MovieDate(
    val year: Int,
    val month: Int,
    val day: Int,
) {
    fun transform(): String = TRANSFORM_FORMAT.format(year, month, day)

    companion object {
        private const val TRANSFORM_FORMAT = "%04d-%02d-%02d"
    }
}

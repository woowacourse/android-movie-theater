package com.woowacourse.data.model

typealias DataMovieDate = MovieDate

data class MovieDate(
    val year: Int,
    val month: Int,
    val day: Int,
) {
    fun transform(): String = TRANSFORM_FORMAT.format(year, month, day)

    companion object {
        private const val DELIMITERS = "-"
        private const val TRANSFORM_FORMAT = "%04d$DELIMITERS%02d$DELIMITERS%02d"

        fun of(date: String): MovieDate {
            val (year, month, day) = date.split(DELIMITERS).map { it.toInt() }
            return MovieDate(year, month, day)
        }
    }
}

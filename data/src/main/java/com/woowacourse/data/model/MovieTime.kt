package com.woowacourse.data.model

typealias DataMovieTime = MovieTime

data class MovieTime(
    val hour: Int,
    val min: Int,
) {
    fun transform(): String = TRANSFORM_FORMAT.format(hour, min)

    companion object {
        private const val DELIMITERS = ":"
        private const val TRANSFORM_FORMAT = "%02d$DELIMITERS%02d"

        fun of(time: String): MovieTime {
            val (hour, min) = time.split(DELIMITERS).map { it.toInt() }
            return MovieTime(hour, min)
        }
    }
}

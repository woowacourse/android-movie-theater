package com.woowacourse.data.model

typealias DataMovieTime = MovieTime

data class MovieTime(
    val hour: Int,
    val min: Int,
) {
    fun transform(): String = TRANSFORM_FORMAT.format(hour, min)

    companion object {
        private const val TRANSFORM_FORMAT = "%02d:%02d"
    }
}

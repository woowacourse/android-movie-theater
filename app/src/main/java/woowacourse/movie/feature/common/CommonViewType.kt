package woowacourse.movie.feature.common

enum class CommonViewType {
    MOVIE,
    ADV,
    THEATER;

    companion object {
        fun of(ordinal: Int): CommonViewType = values()[ordinal]
    }
}

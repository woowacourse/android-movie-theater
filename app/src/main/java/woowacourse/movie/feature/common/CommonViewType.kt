package woowacourse.movie.feature.common

enum class CommonViewType {
    MOVIE,
    ADV,
    RESERVATION,
    THEATER;

    companion object {
        fun of(ordinal: Int): CommonViewType = values()[ordinal]
    }
}

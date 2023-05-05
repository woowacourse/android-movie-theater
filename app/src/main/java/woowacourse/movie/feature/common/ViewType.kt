package woowacourse.movie.feature.common

enum class ViewType {
    MOVIE,
    ADV,
    THEATER;

    companion object {
        fun of(ordinal: Int): ViewType = values()[ordinal]
    }
}

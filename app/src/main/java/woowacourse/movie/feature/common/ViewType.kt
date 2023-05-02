package woowacourse.movie.feature.common

enum class ViewType {
    MOVIE,
    ADV;

    companion object {
        fun of(ordinal: Int): ViewType = values()[ordinal]
    }
}

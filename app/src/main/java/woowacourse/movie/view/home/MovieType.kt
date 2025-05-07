package woowacourse.movie.view.home

import woowacourse.movie.model.movie.Movie
import woowacourse.movie.view.home.MovieType.ItemType.entries

sealed interface MovieType {
    data class MovieItem(
        val movie: Movie,
    ) : MovieType

    data class AdvertisementItem(
        val url: String,
    ) : MovieType

    enum class ItemType {
        MOVIE,
        ADVERTISEMENT,
        ;

        companion object {
            fun valueOf(ordinal: Int): ItemType =
                entries.find { it.ordinal == ordinal }
                    ?: throw IllegalArgumentException("찾을 수 없는 $ordinal 값 입니다.")
        }
    }
}

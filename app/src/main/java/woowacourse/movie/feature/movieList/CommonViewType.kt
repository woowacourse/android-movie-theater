package woowacourse.movie.feature.movieList

import woowacourse.movie.R

enum class CommonViewType(val layoutRes: Int) {
    MOVIE(R.layout.item_movie_layout),
    ADV(R.layout.itema_adv_layout);

    companion object {
        fun of(ordinal: Int): CommonViewType = values()[ordinal]
    }
}

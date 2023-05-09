package woowacourse.movie.feature.common

import woowacourse.movie.R

enum class CommonViewType(val layoutRes: Int) {
    MOVIE(R.layout.item_movie_layout),
    ADV(R.layout.itema_adv_layout),
    RESERVATION(R.layout.item_reservation_layout),
    THEATER(R.layout.item_theater_layout);

    companion object {
        fun of(ordinal: Int): CommonViewType = values()[ordinal]
    }
}

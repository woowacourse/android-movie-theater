package woowacourse.movie.feature.common.itemModel

import androidx.annotation.LayoutRes
import woowacourse.movie.R

enum class ViewType(@LayoutRes val layoutRes: Int) {
    MOVIE(R.layout.item_movie),
    ADV(R.layout.item_adv),
    TICKETS(R.layout.item_reservation),
    THEATER(R.layout.item_theater_layout);

    companion object {
        fun of(id: Int): ViewType = values()[id]
    }
}

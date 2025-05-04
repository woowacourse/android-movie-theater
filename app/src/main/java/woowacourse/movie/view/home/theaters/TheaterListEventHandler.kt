package woowacourse.movie.view.home.theaters

import woowacourse.movie.domain.model.theater.Theater

interface TheaterListEventHandler {
    fun onTheaterSelected(theater: Theater)
}

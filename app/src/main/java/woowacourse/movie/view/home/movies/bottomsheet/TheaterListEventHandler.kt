package woowacourse.movie.view.home.movies.bottomsheet

import woowacourse.movie.domain.model.theater.Theater

interface TheaterListEventHandler {
    fun onTheaterSelected(theater: Theater)
}

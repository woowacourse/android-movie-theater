package woowacourse.movie.ui.movielist.view

import woowacourse.movie.domain.model.theater.Theater

fun interface TheaterClickListener {
    fun onClick(theater: Theater)
}

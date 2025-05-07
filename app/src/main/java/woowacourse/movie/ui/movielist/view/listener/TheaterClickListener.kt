package woowacourse.movie.ui.movielist.view.listener

import woowacourse.movie.domain.model.Theater

fun interface TheaterClickListener {
    fun onClick(theater: Theater)
}

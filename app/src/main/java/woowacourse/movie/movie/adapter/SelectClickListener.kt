package woowacourse.movie.movie.adapter

import woowacourse.movie.movie.TheaterUiModel

fun interface SelectClickListener {
    fun onSelectClick(theater: TheaterUiModel)
}

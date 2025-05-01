package woowacourse.movie.theater

import woowacourse.movie.ui.model.TheaterUiModel

fun interface SelectClickListener {
    fun onSelectClick(theater: TheaterUiModel)
}

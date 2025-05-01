package woowacourse.movie.movie.adapter

import woowacourse.movie.ui.model.TheaterUiModel

fun interface SelectClickListener {
    fun onSelectClick(theater: TheaterUiModel)
}

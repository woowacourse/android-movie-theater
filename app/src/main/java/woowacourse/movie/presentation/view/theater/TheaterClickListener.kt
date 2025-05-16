package woowacourse.movie.presentation.view.theater

import woowacourse.movie.presentation.model.TheaterUiModel

interface TheaterClickListener {
    fun onTheaterClick(theaterUIModel: TheaterUiModel)
}

package woowacourse.movie.view.theater

import woowacourse.movie.view.model.TheaterUiModel

interface TheaterClickListener {
    fun onTheaterClick(theaterUIModel: TheaterUiModel)
}

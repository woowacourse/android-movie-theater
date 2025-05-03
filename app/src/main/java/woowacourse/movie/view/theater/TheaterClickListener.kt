package woowacourse.movie.view.theater

import woowacourse.movie.domain.model.TheaterUIModel

interface TheaterClickListener {
    fun onTheaterClick(theaterUIModel: TheaterUIModel)
}

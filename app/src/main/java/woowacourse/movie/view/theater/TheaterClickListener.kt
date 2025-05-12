package woowacourse.movie.view.theater

import woowacourse.movie.model.theater.TheaterUIModel

interface TheaterClickListener {
    fun onTheaterClick(theaterUIModel: TheaterUIModel)
}

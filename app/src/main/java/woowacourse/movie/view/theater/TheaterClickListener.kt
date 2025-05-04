package woowacourse.movie.view.theater

import woowacourse.movie.view.model.TheaterUIModel

interface TheaterClickListener {
    fun onTheaterClick(theaterUIModel: TheaterUIModel)
}

package woowacourse.movie.view.theater

import woowacourse.movie.model.TheaterUIModel

interface TheaterClickListener {
    fun onTheaterClick(theaterUIModel: TheaterUIModel)
}

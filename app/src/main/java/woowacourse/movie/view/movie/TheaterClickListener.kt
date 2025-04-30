package woowacourse.movie.view.movie

import woowacourse.movie.model.TheaterUIModel

interface TheaterClickListener {
    fun onTheaterClick(theaterUIModel: TheaterUIModel)
}

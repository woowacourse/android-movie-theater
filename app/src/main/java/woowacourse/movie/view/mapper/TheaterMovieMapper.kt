package woowacourse.movie.view.mapper

import woowacourse.movie.domain.model.TheaterMovie
import woowacourse.movie.view.data.TheaterMovieViewData
import woowacourse.movie.view.mapper.MovieMapper.toDomain
import woowacourse.movie.view.mapper.MovieMapper.toView

object TheaterMovieMapper : Mapper<TheaterMovie, TheaterMovieViewData> {
    override fun TheaterMovie.toView(): TheaterMovieViewData {
        return TheaterMovieViewData(this.theaterName, this.movie.toView(), screenTimes)
    }

    override fun TheaterMovieViewData.toDomain(): TheaterMovie {
        return TheaterMovie(this.theaterName, this.movie.toDomain(), screenTimes)
    }
}

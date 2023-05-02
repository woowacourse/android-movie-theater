package woowacourse.movie.mapper

import woowacourse.movie.data.MovieListViewType
import woowacourse.movie.data.MovieViewData
import woowacourse.movie.domain.Movie
import woowacourse.movie.mapper.DateRangeMapper.toDomain
import woowacourse.movie.mapper.DateRangeMapper.toView
import woowacourse.movie.mapper.ImageMapper.toDomain
import woowacourse.movie.mapper.ImageMapper.toView

object MovieMapper : Mapper<Movie, MovieViewData> {
    override fun Movie.toView(): MovieViewData {
        return MovieViewData(
            poster.toView(),
            title,
            date.toView(),
            runningTime,
            description,
            MovieListViewType.MOVIE
        )
    }

    override fun MovieViewData.toDomain(): Movie {
        return Movie(
            poster.toDomain(),
            title,
            date.toDomain(),
            runningTime,
            description
        )
    }
}

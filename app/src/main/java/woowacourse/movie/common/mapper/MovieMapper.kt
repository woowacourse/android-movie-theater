package woowacourse.movie.common.mapper

import woowacourse.movie.common.mapper.DateRangeMapper.toDomain
import woowacourse.movie.common.mapper.DateRangeMapper.toView
import woowacourse.movie.common.mapper.ImageMapper.toDomain
import woowacourse.movie.common.mapper.ImageMapper.toView
import woowacourse.movie.common.model.MovieListViewType
import woowacourse.movie.common.model.MovieViewData
import woowacourse.movie.domain.Movie

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

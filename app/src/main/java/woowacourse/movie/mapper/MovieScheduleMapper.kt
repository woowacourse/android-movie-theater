package woowacourse.movie.mapper

import woowacourse.movie.data.MovieScheduleViewData
import woowacourse.movie.domain.MovieSchedule
import woowacourse.movie.mapper.MovieMapper.toDomain
import woowacourse.movie.mapper.MovieMapper.toView

object MovieScheduleMapper : Mapper<MovieSchedule, MovieScheduleViewData> {
    override fun MovieSchedule.toView(): MovieScheduleViewData {
        return MovieScheduleViewData(
            movie.toView(),
            times
        )
    }

    override fun MovieScheduleViewData.toDomain(): MovieSchedule {
        return MovieSchedule(
            movie.toDomain(),
            times
        )
    }
}

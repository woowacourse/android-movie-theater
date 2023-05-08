package woowacourse.movie.common.mapper

import woowacourse.movie.common.mapper.MovieMapper.toDomain
import woowacourse.movie.common.mapper.MovieMapper.toView
import woowacourse.movie.common.model.MovieScheduleViewData
import woowacourse.movie.domain.MovieSchedule

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

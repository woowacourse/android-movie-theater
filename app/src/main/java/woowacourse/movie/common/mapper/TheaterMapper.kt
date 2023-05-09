package woowacourse.movie.common.mapper

import woowacourse.movie.common.mapper.MovieScheduleMapper.toDomain
import woowacourse.movie.common.mapper.MovieScheduleMapper.toView
import woowacourse.movie.common.model.TheaterViewData
import woowacourse.movie.domain.Theater

object TheaterMapper : Mapper<Theater, TheaterViewData> {
    override fun Theater.toView(): TheaterViewData {
        return TheaterViewData(name, movieSchedules.map { it.toView() })
    }

    override fun TheaterViewData.toDomain(): Theater {
        return Theater(name, movieSchedules.map { it.toDomain() })
    }
}

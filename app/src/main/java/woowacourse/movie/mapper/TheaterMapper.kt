package woowacourse.movie.mapper

import woowacourse.movie.data.TheaterViewData
import woowacourse.movie.domain.Theater
import woowacourse.movie.mapper.MovieScheduleMapper.toDomain
import woowacourse.movie.mapper.MovieScheduleMapper.toView

object TheaterMapper : Mapper<Theater, TheaterViewData> {
    override fun Theater.toView(): TheaterViewData {
        return TheaterViewData(name, movieSchedules.map { it.toView() })
    }

    override fun TheaterViewData.toDomain(): Theater {
        return Theater(name, movieSchedules.map { it.toDomain() })
    }
}

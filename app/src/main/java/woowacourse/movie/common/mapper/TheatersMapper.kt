package woowacourse.movie.common.mapper

import woowacourse.movie.common.mapper.TheaterMapper.toDomain
import woowacourse.movie.common.mapper.TheaterMapper.toView
import woowacourse.movie.common.model.TheatersViewData
import woowacourse.movie.domain.Theaters

object TheatersMapper : Mapper<Theaters, TheatersViewData> {
    override fun Theaters.toView(): TheatersViewData {
        return TheatersViewData(value.map { it.toView() })
    }

    override fun TheatersViewData.toDomain(): Theaters {
        return Theaters(value.map { it.toDomain() })
    }
}

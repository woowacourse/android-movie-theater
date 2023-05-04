package woowacourse.movie.mapper

import woowacourse.movie.data.TheatersViewData
import woowacourse.movie.domain.Theaters
import woowacourse.movie.mapper.TheaterMapper.toDomain
import woowacourse.movie.mapper.TheaterMapper.toView

object TheatersMapper : Mapper<Theaters, TheatersViewData> {
    override fun Theaters.toView(): TheatersViewData {
        return TheatersViewData(value.map { it.toView() })
    }

    override fun TheatersViewData.toDomain(): Theaters {
        return Theaters(value.map { it.toDomain() })
    }
}

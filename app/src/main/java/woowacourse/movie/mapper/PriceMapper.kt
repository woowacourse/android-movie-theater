package woowacourse.movie.mapper

import woowacourse.movie.data.PriceViewData
import woowacourse.movie.domain.Price

object PriceMapper : Mapper<Price, PriceViewData> {
    override fun Price.toView(): PriceViewData {
        return PriceViewData(value)
    }

    override fun PriceViewData.toDomain(): Price {
        return Price(value)
    }
}

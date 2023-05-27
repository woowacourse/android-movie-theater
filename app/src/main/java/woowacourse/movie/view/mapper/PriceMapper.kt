package woowacourse.movie.view.mapper

import woowacourse.movie.domain.model.Price
import woowacourse.movie.view.data.PriceViewData

object PriceMapper : Mapper<Price, PriceViewData> {
    override fun Price.toView(): PriceViewData {
        return PriceViewData(value)
    }

    override fun PriceViewData.toDomain(): Price {
        return Price(value)
    }
}

package woowacourse.movie.common.mapper

import woowacourse.movie.common.model.DateRangeViewData
import woowacourse.movie.domain.DateRange

object DateRangeMapper : Mapper<DateRange, DateRangeViewData> {
    override fun DateRange.toView(): DateRangeViewData {
        return DateRangeViewData(
            startDate,
            endDate
        )
    }

    override fun DateRangeViewData.toDomain(): DateRange {
        return DateRange(
            startDate,
            endDate
        )
    }
}

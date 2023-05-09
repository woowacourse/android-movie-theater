package woowacourse.movie.common.mapper

import woowacourse.movie.common.model.ReservationDetailViewData
import woowacourse.movie.domain.ReservationDetail

object ReservationDetailMapper : Mapper<ReservationDetail, ReservationDetailViewData> {
    override fun ReservationDetail.toView(): ReservationDetailViewData {
        return ReservationDetailViewData(
            date,
            peopleCount
        )
    }

    override fun ReservationDetailViewData.toDomain(): ReservationDetail {
        return ReservationDetail(
            date,
            peopleCount
        )
    }
}

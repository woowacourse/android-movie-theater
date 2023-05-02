package woowacourse.movie.mapper

import woowacourse.movie.data.ReservationDetailViewData
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

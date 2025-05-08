package woowacourse.movie.data.mapper

import woowacourse.movie.data.ReservationEntity
import woowacourse.movie.domain.model.ReservationInfo
import woowacourse.movie.domain.model.TicketCount

object ReservationMapper {
    fun ReservationEntity.toDomain(): ReservationInfo =
        ReservationInfo(
            title = movieTitle,
            dateTime = dateTime,
            seats = seats,
            count = TicketCount(ticketCount),
        )

    fun ReservationInfo.toEntity(theaterName: String): ReservationEntity =
        ReservationEntity(
            movieTitle = title,
            dateTime = dateTime,
            ticketCount = count.value,
            seats = seats,
            theaterName = theaterName,
            totalPrice = seats.totalPrice,
        )
}

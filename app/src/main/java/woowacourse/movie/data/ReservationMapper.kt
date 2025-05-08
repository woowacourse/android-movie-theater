package woowacourse.movie.data

import woowacourse.movie.data.db.ReservationEntity
import woowacourse.movie.domain.model.ReservationInfo
import woowacourse.movie.domain.model.TicketCount

object ReservationMapper {
    fun ReservationEntity.toDomain(): ReservationInfo =
        ReservationInfo(
            title = movieTitle,
            dateTime = dateTime,
            seats = seats,
            count = TicketCount(ticketCount),
            theaterName = theaterName,
        )

    fun ReservationInfo.toEntity(): ReservationEntity =
        ReservationEntity(
            movieTitle = title,
            dateTime = dateTime,
            ticketCount = count.value,
            seats = seats,
            theaterName = theaterName,
            totalPrice = seats.totalPrice,
        )
}

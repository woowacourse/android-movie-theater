package woowacourse.movie.data.extension

import woowacourse.movie.data.entity.TicketBundleEntity
import woowacourse.movie.data.entity.TicketEntity
import woowacourse.movie.domain.model.cinema.ticket.Ticket
import woowacourse.movie.domain.model.cinema.ticket.TicketBundle
import woowacourse.movie.domain.model.cinema.ticket.toLabel

fun TicketBundle.toEntity(): TicketBundleEntity =
    TicketBundleEntity(
        title = this.title,
        dateTime = this.dateTime.toString(),
        theater = this.theater,
    )

fun List<Ticket>.toEntityList(bundleId: Int): List<TicketEntity> =
    map {
        TicketEntity(
            seatLabel = it.seat.toLabel(),
            price = it.price,
            bundleId = bundleId,
            type = it.seat.type.toString(),
        )
    }

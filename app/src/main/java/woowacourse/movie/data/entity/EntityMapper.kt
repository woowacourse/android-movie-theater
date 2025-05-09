package woowacourse.movie.data.entity

import woowacourse.movie.domain.model.Ticket

fun Ticket.toEntity(): TicketEntity =
    TicketEntity(
        movie = this.movie,
        theater = this.theater,
        showtime = this.showtime,
        headCount = this.headCount,
        seats = this.seats,
    )

fun TicketEntity.toDomainModel(): Ticket =
    Ticket(
        movie = this.movie,
        theater = this.theater,
        showtime = this.showtime,
        headCount = this.headCount,
        seats = this.seats,
    )

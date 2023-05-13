package woowacourse.movie.mapper.seat

import domain.Seat
import domain.Seats
import woowacourse.movie.seat.dto.SeatDto
import woowacourse.movie.seat.dto.SeatsDto

fun SeatsDto.mapToSeats(): Seats {
    return Seats(this.seats.map { Seat(it.row.mapToDomain(), it.col.mapToDomain()) })
}

fun Seats.mapToSeatsDto(): SeatsDto {
    return SeatsDto(this.seats.map { SeatDto(it.row.mapToUIModel(), it.col.mapToUIModel()) })
}

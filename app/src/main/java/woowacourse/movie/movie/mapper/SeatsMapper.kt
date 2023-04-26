package woowacourse.movie.mapper

import domain.Seat
import domain.Seats
import woowacourse.movie.dto.SeatDto
import woowacourse.movie.dto.SeatsDto
import woowacourse.movie.movie.mapper.mapToDomain
import woowacourse.movie.movie.mapper.mapToUIModel

fun SeatsDto.mapToSeats(): Seats {
    return Seats(this.seats.map { Seat(it.row.mapToDomain(), it.col.mapToDomain()) })
}

fun Seats.mapToSeatsDto(): SeatsDto {
    return SeatsDto(this.seats.map { SeatDto(it.row.mapToUIModel(), it.col.mapToUIModel()) })
}

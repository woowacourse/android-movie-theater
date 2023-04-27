package woowacourse.movie.movie.mapper.seat

import domain.Seat
import woowacourse.movie.movie.dto.seat.SeatDto

fun SeatDto.mapToSeat(): Seat {
    return Seat(this.row.mapToDomain(), this.col.mapToDomain())
}

fun Seat.mapToSeatDto(): SeatDto {
    return SeatDto(this.row.mapToUIModel(), this.col.mapToUIModel())
}

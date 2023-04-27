package woowacourse.movie.movie.mapper.seat

import domain.SeatCol
import woowacourse.movie.movie.dto.seat.SeatColDto

fun SeatCol.mapToUIModel(): SeatColDto {
    return SeatColDto(this.col)
}

fun SeatColDto.mapToDomain(): SeatCol {
    return SeatCol(this.col)
}

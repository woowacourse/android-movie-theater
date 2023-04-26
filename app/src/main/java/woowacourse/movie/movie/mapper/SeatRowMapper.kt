package woowacourse.movie.movie.mapper

import domain.SeatRow
import woowacourse.movie.movie.dto.SeatRowDto

fun SeatRow.mapToUIModel(): SeatRowDto {
    return SeatRowDto.of(this.row)
}

fun SeatRowDto.mapToDomain(): SeatRow {
    return SeatRow(this.row.code - 64)
}

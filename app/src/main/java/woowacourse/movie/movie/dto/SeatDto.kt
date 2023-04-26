package woowacourse.movie.dto

import woowacourse.movie.movie.dto.SeatColDto
import woowacourse.movie.movie.dto.SeatRowDto
import java.io.Serializable

data class SeatDto(val row: SeatRowDto, val col: SeatColDto) : Serializable {
    fun getString(): String {
        return "${row.row}${col.col}"
    }
}

package woowacourse.movie.theater

import java.time.LocalTime

object TheaterDatabase {
    val theaters = listOf<TheaterEntity>(
        TheaterEntity(
            id = 0,
            name = "선릉 극장",
            rowSize = 5,
            columnSize = 4,
            screeningTimes = listOf(
                LocalTime.of(10, 0),
                LocalTime.of(12, 0),
                LocalTime.of(14, 0),
                LocalTime.of(16, 0),
                LocalTime.of(18, 0),
                LocalTime.of(20, 0),
            )
        ),
        TheaterEntity(
            id = 1,
            name = "강남 극장",
            rowSize = 5,
            columnSize = 4,
            screeningTimes = listOf(
                LocalTime.of(12, 0),
                LocalTime.of(15, 0),
                LocalTime.of(18, 0),
                LocalTime.of(21, 0),
            )
        ),
        TheaterEntity(
            id = 2,
            name = "잠실 극장",
            rowSize = 5,
            columnSize = 4,
            screeningTimes = listOf(
                LocalTime.of(9, 0),
                LocalTime.of(15, 0),
                LocalTime.of(21, 0),
            )
        ),
    )

    fun selectTheater(id: Long): TheaterEntity {
        return theaters.find { it.id == id }
            ?: throw NoSuchElementException("해당 상영관이 존재하지 않습니다. 입력된 상영관 id: $id")
    }
}

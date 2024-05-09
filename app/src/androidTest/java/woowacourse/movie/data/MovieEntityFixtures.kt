package woowacourse.movie.data

import woowacourse.movie.data.entity.ReservationEntity
import woowacourse.movie.data.entity.SeatEntity

fun reservationEntity(
    cinemaName: String = "CGV",
    movieName: String = "오둥이",
    synopsis: String = "어머니 키워주셔서 감사합니다.",
    runningTime: String = "120",
    releaseDate: String = "2024-05-08",
) = ReservationEntity(
    cinemaName = cinemaName,
    movieName = movieName,
    synopsis = synopsis,
    runningTime = runningTime,
    releaseDate = releaseDate
)

fun seatEntity(
    reservationId: Long = 0,
    row: Int = 1,
    col: Int = 1,
    grade: String = "S"
): SeatEntity = SeatEntity(
    reservationId,
    row,
    col,
    grade
)
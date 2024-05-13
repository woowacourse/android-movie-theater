package woowacourse.movie.model

import java.time.ZoneOffset

class Alarm(
    val id: Long,
    val title: String,
    val alarmTime: Long,
) {
    constructor(reservation: Reservation, beforeMinute: Long) : this(
        reservation.id,
        reservation.screening.movie.title,
        reservation.screening.localDateTime.minusMinutes(beforeMinute).toEpochSecond(ZoneOffset.UTC) * 1000,
    )
}

package woowacourse.movie.setting.uimodel

import woowacourse.movie.model.Reservation
import java.time.ZoneOffset

class ReservationAlarmUiModel(
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

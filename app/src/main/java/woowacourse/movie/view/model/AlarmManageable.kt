package woowacourse.movie.view.model

interface AlarmManageable {
    fun registerAlarms(reservations: List<ReservationUiModel>, minuteInterval: Long)
    fun registerAlarm(reservation: ReservationUiModel, minuteInterval: Long)
    fun cancelAlarms()
    fun createChannel()
}

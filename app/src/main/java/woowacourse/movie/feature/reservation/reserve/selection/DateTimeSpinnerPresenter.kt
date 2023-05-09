package woowacourse.movie.feature.reservation.reserve.selection

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

class DateTimeSpinnerPresenter(
    val view: DateTimeSpinnerContract.View,
    override val selectedDate: LocalDate,
    override val selectedTime: LocalTime
) : DateTimeSpinnerContract.Presenter {
    override fun setDateTime(dateTime: LocalDateTime) {
        TODO("Not yet implemented")
    }

    override fun clickDate(position: Int) {
        TODO("Not yet implemented")
    }

    override fun clickTime(position: Int) {
        TODO("Not yet implemented")
    }
}

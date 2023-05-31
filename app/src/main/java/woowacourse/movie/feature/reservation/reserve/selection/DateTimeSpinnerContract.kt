package woowacourse.movie.feature.reservation.reserve.selection

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

interface DateTimeSpinnerContract {

    interface View {
        fun setDateSpinnerAdapter(dates: List<LocalDate>)
        fun setTimeSpinnerAdapter(times: List<LocalTime>)
        fun setSelectDate(position: Int) // 스피너 클릭 리스너 최초 생성시 1회 호출 방지
        fun setSelectTime(position: Int)
    }

    interface Presenter {
        val selectedDate: LocalDate
        val selectedTime: LocalTime
        fun setDateTime(dateTime: LocalDateTime)
        fun clickDate(position: Int)
        fun clickTime(position: Int)
    }
}

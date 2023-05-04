package woowacourse.movie.feature.detail.dateTime

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

interface DateTimeContract {
    interface View {
        fun setDateSpinnerAdapter(dates: List<LocalDate>)
        fun setTimeSpinnerAdapter(times: List<LocalTime>)
        fun setSelectDate(position: Int)
        fun setSelectTime(position: Int)
    }

    interface Presenter {
        val selectDate: LocalDate
        val selectTime: LocalTime
        fun setDateTime(dateTime: LocalDateTime)
        fun clickDate(position: Int)
        fun clickTime(position: Int)
    }
}

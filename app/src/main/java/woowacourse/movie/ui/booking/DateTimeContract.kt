package woowacourse.movie.ui.booking

import java.time.LocalDate
import java.time.LocalTime

interface DateTimeContract {

    interface View {

        fun setTimes(screeningTimes: List<LocalTime>)
        fun setDates(screeningDates: List<LocalDate>)
        fun initDateSelectedListener(updateTimes: (selectedDate: LocalDate) -> (Unit))
    }

    interface Presenter {

        val screeningDates: List<LocalDate>
        val screeningTimes: (selectedDate: LocalDate) -> List<LocalTime>

        fun initDateTimes()
        fun onDateSelected(screeningDate: LocalDate)
    }
}

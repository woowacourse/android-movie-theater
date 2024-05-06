package woowacourse.movie.presentation.ui.detail.adapter

import woowacourse.movie.domain.model.ScreenDate
import java.time.LocalDate
import java.time.LocalTime

interface SpinnerActionHandler {
    fun createDateSpinnerAdapter(screenDates: List<ScreenDate>)

    fun createTimeSpinnerAdapter(screenDate: ScreenDate)

    fun registerDate(date: LocalDate)

    fun registerTime(time: LocalTime)
}

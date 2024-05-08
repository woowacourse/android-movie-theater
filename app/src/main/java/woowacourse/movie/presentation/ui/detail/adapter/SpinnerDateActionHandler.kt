package woowacourse.movie.presentation.ui.detail.adapter

import woowacourse.movie.domain.model.ScreenDate
import java.time.LocalDate

interface SpinnerDateActionHandler {
    fun loadTimeSpinnerAdapter(screenDate: ScreenDate)

    fun registerDate(date: LocalDate)
}

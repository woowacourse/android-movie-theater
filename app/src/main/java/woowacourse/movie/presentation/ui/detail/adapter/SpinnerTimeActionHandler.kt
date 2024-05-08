package woowacourse.movie.presentation.ui.detail.adapter

import java.time.LocalTime

interface SpinnerTimeActionHandler {
    fun registerTime(time: LocalTime)
}

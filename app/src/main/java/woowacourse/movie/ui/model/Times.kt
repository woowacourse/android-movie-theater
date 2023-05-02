package woowacourse.movie.ui.model

import java.time.LocalTime

class Times(times: List<LocalTime>) {
    val items: MutableList<LocalTime> = times.toMutableList()

    fun changeTimes(times: List<LocalTime>) {
        items.clear()
        items.addAll(times)
    }
}

package woowacourse.movie.ui.activity.detail

import java.time.LocalDate
import java.time.LocalTime

interface MovieDetailContract {
    interface Presenter {
        fun setPeopleCount(count: Int)

        fun decreasePeopleCount()

        fun increasePeopleCount()

        fun initTimes(date: LocalDate)

        fun updateTimes(date: LocalDate)
    }

    interface View {
        var presenter: Presenter

        fun setPeopleCount(count: Int)

        fun initTimeSpinner(times: List<LocalTime>)

        fun updateTimeSpinner()
    }
}

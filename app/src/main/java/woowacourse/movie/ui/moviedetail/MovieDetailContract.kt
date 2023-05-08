package woowacourse.movie.ui.moviedetail

import java.time.LocalDate

interface MovieDetailContract {
    interface View {
        val presenter: Presenter
        fun setPeopleCountView(count: Int)
        fun setDateSpinner(days: List<LocalDate>)
        fun setTimeSpinner()
    }

    interface Presenter {
        fun addCount()
        fun minusCount()
        fun setDateSpinner()
        fun setTimeSpinner(date: LocalDate)
        fun updatePeopleCount(count: Int)
        fun updateTimesByDate(date: LocalDate)
    }
}

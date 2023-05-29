package woowacourse.movie.ui.moviedetail

import woowacourse.movie.uimodel.PeopleCountModel
import java.time.LocalDate

interface MovieDetailContract {
    interface View {
        val presenter: Presenter
        fun setPeopleCountView(count: Int)
        fun setDateSpinner(days: List<LocalDate>)
        fun setTimeSpinner()
        fun updatePeopleCount(count: Int)
    }

    interface Presenter {
        fun addCount(peopleCount: PeopleCountModel)
        fun minusCount(peopleCount: PeopleCountModel)
        fun setPeopleCountText(peopleCount: PeopleCountModel)
        fun setDateSpinner()
        fun setTimeSpinner(date: LocalDate)
        fun updateTimesByDate(date: LocalDate)
    }
}

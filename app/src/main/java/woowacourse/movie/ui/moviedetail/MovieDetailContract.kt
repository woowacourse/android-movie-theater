package woowacourse.movie.ui.moviedetail

import woowacourse.movie.model.MovieListModel
import java.time.LocalDate

interface MovieDetailContract {
    interface View {
        val presenter: Presenter
        fun setPeopleCountView(count: Int)
    }

    interface Presenter {
        fun addCount()
        fun minusCount()
        fun updatePeopleCount(count: Int)
        fun updateTimesByDate(date: LocalDate)
        fun getDatesBetweenTwoDates(movie: MovieListModel.MovieModel): List<LocalDate>
    }
}

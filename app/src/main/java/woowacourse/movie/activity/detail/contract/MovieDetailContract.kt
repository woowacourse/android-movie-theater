package woowacourse.movie.activity.detail.contract

import woowacourse.movie.dto.movie.MovieDateUIModel
import woowacourse.movie.dto.movie.MovieTimeUIModel
import woowacourse.movie.dto.movie.MovieUIModel
import woowacourse.movie.dto.movie.TheaterUIModel
import woowacourse.movie.dto.ticket.TicketCountUIModel
import java.time.LocalDate

interface MovieDetailContract {
    interface View {
        // fun setUpState(savedInstanceState: Bundle?)
        // fun setToolBar()
        fun setMovieData(data: MovieUIModel)
        fun setDateSpinnerPosition(dateSpinnerPosition: Int)
        fun setTimeSpinnerPosition(timeSpinnerPosition: Int)
        fun formatMovieRunningDate(item: MovieUIModel): String
        fun setBookerNumber(number: TicketCountUIModel)
        fun showSeatSelectPage(
            data: MovieUIModel,
            count: TicketCountUIModel,
            date: MovieDateUIModel,
            time: MovieTimeUIModel,
        )

        fun setDateSpinnerData(data: List<String>)
        fun setTimeSpinnerData(data: List<String>)
    }

    interface Presenter {
        fun loadMovieData(data: MovieUIModel)
        fun loadDateSpinnerPosition()
        fun loadTimeSpinnerPosition()
        fun increaseNumber()
        fun decreaseNumber()
        fun onBookBtnClick(
            data: MovieUIModel,
            date: String,
            time: String,
        )

        fun loadDateSpinnerData(startDate: LocalDate, endDate: LocalDate)
        fun loadTimeSpinnerData(movieId: Int, theater: TheaterUIModel)

        fun getTimeSpinnerPosition(): Int
        fun getDateSpinnerPosition(): Int

        fun saveTimeSpinnerPosition(position: Int)
    }
}

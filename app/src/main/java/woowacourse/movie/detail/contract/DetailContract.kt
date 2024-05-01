package woowacourse.movie.detail.contract

import woowacourse.movie.detail.model.Count
import woowacourse.movie.detail.presenter.DetailPresenter
import woowacourse.movie.list.model.Movie
import java.time.LocalDate
import java.time.LocalTime

interface DetailContract {
    interface View {
        val presenter: DetailPresenter

        fun showCurrentResultTicketCountView(info: Int)

        fun setMovieView(info: Movie)

        fun startMovieTicketActivity(
            count: Count,
            theaterId: Long,
        )

        fun showToast(message: String)

        fun showSpinner(
            screeningDates: List<LocalDate>,
            screeningTimes: List<LocalTime>,
        )

        fun setOnSpinnerDateItemSelectedListener(screeningDates: List<LocalDate>)

        fun setOnSpinnerTimeItemSelectedListener(screeningTimes: List<LocalTime>)
    }

    interface Presenter {
        fun setCurrentResultTicketCountInfo()

        fun setPlusButtonClickInfo()

        fun setMinusButtonClickInfo()

        fun setTicketingButtonClickInfo()

        fun storeMovieId(movieId: Long)

        fun setMovieInfo()

        fun setSpinnerInfo(theaterId: Long)

        fun setSpinnerDateItemInfo()

        fun setSpinnerTimeItemInfo()

        fun storeSelectedTime(selectedTime: LocalTime)

        fun storeTheaterId(theaterId: Long)
    }
}

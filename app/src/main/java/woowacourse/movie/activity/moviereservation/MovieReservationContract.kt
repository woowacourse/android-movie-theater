package woowacourse.movie.activity.moviereservation

import android.os.Bundle
import woowacourse.movie.view.data.LocalFormattedDate
import woowacourse.movie.view.data.MovieViewData
import woowacourse.movie.view.data.ReservationDetailViewData
import java.time.LocalDateTime

interface MovieReservationContract {
    interface View {
        var presenter: Presenter
        fun initCount(count: Int)
        fun initDateSpinner(dateIndex: Int, timeIndex: Int, dates: List<LocalFormattedDate>)
        fun setCount(count: Int)
    }

    interface Presenter {
        fun saveCount(bundle: Bundle)
        fun saveDate(bundle: Bundle)
        fun saveTime(bundle: Bundle)
        fun initCount(bundle: Bundle?)
        fun initDateSpinner(movie: MovieViewData, bundle: Bundle?)
        fun plusCount()
        fun minusCount()
        fun getReservationDetailView(
            date: LocalDateTime,
            peopleCount: Int
        ): ReservationDetailViewData
    }
}

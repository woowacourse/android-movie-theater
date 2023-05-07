package woowacourse.movie.activity.moviereservation

import android.os.Bundle
import woowacourse.movie.activity.utils.MovieRenderContract
import woowacourse.movie.view.data.LocalFormattedDate
import woowacourse.movie.view.data.LocalFormattedTime
import woowacourse.movie.view.data.ReservationDetailViewData
import woowacourse.movie.view.data.TheaterMovieViewData
import java.time.LocalDateTime

interface MovieReservationContract {
    interface View : MovieRenderContract.View {
        var presenter: Presenter
        fun initCount(count: Int)
        fun initDateSpinner(dateIndex: Int, dates: List<LocalFormattedDate>)
        fun initTimeSpinner(timeIndex: Int, times: List<LocalFormattedTime>)
        fun setCount(count: Int)
        override fun renderMovie(
            image: Int,
            title: String,
            date: String,
            runningTime: String,
            description: String
        )
    }

    interface Presenter : MovieRenderContract.Presenter {
        fun saveCount(bundle: Bundle)
        fun saveDate(bundle: Bundle)
        fun saveTime(bundle: Bundle)
        fun initCount(bundle: Bundle?)
        fun initDateSpinner(theaterMovie: TheaterMovieViewData, bundle: Bundle?)
        fun initTimeSpinner(theaterMovie: TheaterMovieViewData, bundle: Bundle?)
        fun plusCount()
        fun minusCount()
        fun getReservationDetailView(
            date: LocalDateTime,
            theaterName: String
        ): ReservationDetailViewData
    }
}

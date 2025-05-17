package woowacourse.movie.moviebooked

import android.content.Context
import android.os.Handler
import android.os.Looper
import woowacourse.movie.data.MovieApplication
import woowacourse.movie.data.ReservationRepository
import kotlin.concurrent.thread

class MovieBookedPresenter(
    private val view: MovieBookedContract.View,
) : MovieBookedContract.Presenter {
    override fun loadReservationInfo(id: Long) {
        thread {
            val reservationRepository = ReservationRepository.get()
            val reservation = reservationRepository.getReservation(id) ?: throw IllegalArgumentException("${id}에 대한 값을 찾지 못했습니다.")
            Handler(Looper.getMainLooper()).post {
                view.showReservation(reservation)
            }
        }
    }
}

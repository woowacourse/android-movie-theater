package woowacourse.movie.moviebooked

import android.content.Context
import android.os.Handler
import android.os.Looper
import woowacourse.movie.data.MovieApplication
import kotlin.concurrent.thread

class MovieBookedPresenter(
    private val view: MovieBookedContract.View,
    private val context: Context,
) : MovieBookedContract.Presenter {
    override fun loadReservationInfo(id: Long) {
        thread {
            val db = (context.applicationContext as MovieApplication).database
            val reservation = db.reservationDao().getById(id) ?: throw IllegalArgumentException("${id}에 매칭되는 데이터베이스를 찾을 수 없습니다.")
            Handler(Looper.getMainLooper()).post {
                view.showReservation(reservation)
            }
        }
    }
}

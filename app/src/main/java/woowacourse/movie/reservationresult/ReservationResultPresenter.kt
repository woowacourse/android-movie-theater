package woowacourse.movie.reservationresult

import woowacourse.movie.repository.MovieRepository
import woowacourse.movie.setting.AlarmSetting
import kotlin.concurrent.thread

class ReservationResultPresenter(
    private val repository: MovieRepository,
    private val view: ReservationResultContract.View,
    private val alarmSetting: AlarmSetting,
) : ReservationResultContract.Presenter {
    override fun loadReservationResult(reservationId: Long) {
        thread {
            val reservationResult = repository.movieReservationById(reservationId)
            val theater = repository.theaterById(reservationResult.theaterId)
            view.showResult(reservationResult.toReservationResultUiModel(theater.name))
        }.join()
    }
}

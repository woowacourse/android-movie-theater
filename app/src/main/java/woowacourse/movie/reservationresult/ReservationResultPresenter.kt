package woowacourse.movie.reservationresult

import android.content.Context
import woowacourse.movie.repository.MovieRepository
import woowacourse.movie.setting.AlarmSetting
import kotlin.concurrent.thread

class ReservationResultPresenter(
    private val repository: MovieRepository,
    private val view: ReservationResultContract.View,
) : ReservationResultContract.Presenter {
    override fun loadReservationResult(reservationId: Long) {
        thread {
            val reservationResult = repository.movieReservationById(reservationId)
            val theater = repository.theaterById(reservationResult.theaterId)
            view.showResult(reservationResult.toReservationResultUiModel(theater.name))
        }.join()
    }

    override fun setAlarm(
        reservationId: Long,
        context: Context,
    ) {
        thread {
            runCatching {
                repository.movieReservationById(reservationId)
            }.onSuccess { reservation ->
                val settingAlarm = AlarmSetting()
                settingAlarm.setAlarm(context, reservation)
            }
        }.join()
    }
}

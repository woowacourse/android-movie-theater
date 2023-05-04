package woowacourse.movie.system

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import woowacourse.movie.data.dataSource.LocalDatabase
import woowacourse.movie.data.database.MovieDBHelper
import woowacourse.movie.data.repository.ReservationRepository
import woowacourse.movie.mapper.ReservationMapper.toView
import woowacourse.movie.system.BroadcastAlarm.registerAlarmReceiver
import woowacourse.movie.system.BroadcastAlarm.setAlarmAtDate

class NotificationSetReceiver : BroadcastReceiver() {
    private val reservationRepository: ReservationRepository = ReservationRepository()
    override fun onReceive(context: Context, intent: Intent) {
        LocalDatabase.movieDBHelper = MovieDBHelper(context)

        val reservations = reservationRepository.requestReservation()

        registerAlarmReceiver(
            context,
            ReservationAlarmReceiver(),
            ReservationAlarmReceiver.ACTION_ALARM
        )

        for (reservation in reservations) {
            val alarmIntent = ReservationAlarmReceiver.from(context, reservation.toView())
            setAlarmAtDate(context, reservation.reservationDetail.date, alarmIntent)
        }
    }
}

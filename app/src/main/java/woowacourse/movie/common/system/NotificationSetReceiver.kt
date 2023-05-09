package woowacourse.movie.common.system

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import woowacourse.movie.common.database.MovieDao
import woowacourse.movie.common.mapper.ReservationMapper.toView
import woowacourse.movie.common.repository.ReservationRepository
import woowacourse.movie.common.system.BroadcastAlarm.registerAlarmReceiver
import woowacourse.movie.common.system.BroadcastAlarm.setAlarmAtDate
import woowacourse.movie.domain.Reservation

class NotificationSetReceiver : BroadcastReceiver() {
    private lateinit var reservationRepository: ReservationRepository
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_BOOT_COMPLETED) {
            reservationRepository = ReservationRepository(MovieDao(context))
            val reservations = reservationRepository.requestReservation()

            registerAlarm(context, reservations)
        }
    }

    private fun registerAlarm(context: Context, reservations: List<Reservation>) {
        registerAlarmReceiver(
            context, ReservationAlarmReceiver(), ReservationAlarmReceiver.ACTION_ALARM
        )

        for (reservation in reservations) {
            val alarmIntent = ReservationAlarmReceiver.from(context, reservation.toView())
            setAlarmAtDate(context, reservation.reservationDetail.date, alarmIntent)
        }
    }
}

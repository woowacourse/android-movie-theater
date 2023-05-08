package woowacourse.movie.system

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import woowacourse.movie.data.database.MovieDao
import woowacourse.movie.data.repository.ReservationRepository
import woowacourse.movie.domain.Reservation
import woowacourse.movie.mapper.ReservationMapper.toView
import woowacourse.movie.system.BroadcastAlarm.registerAlarmReceiver
import woowacourse.movie.system.BroadcastAlarm.setAlarmAtDate

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

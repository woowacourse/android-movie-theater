package woowacourse.movie.view.util

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import woowacourse.movie.database.AppDatabase
import woowacourse.movie.model.reservation.ReservationInfo
import woowacourse.movie.view.main.setting.AlarmReceiver
import woowacourse.movie.view.util.Extras.AlarmData.RESERVATION_INFO_KEY
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.Locale

class AlarmManagerHelper(
    private val context: Context,
) {
    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    fun scheduleAllMovieAlarms() {
        Thread {
            val reservations = getAllReservations()
            for (reservation in reservations) {
                scheduleSingleMovieAlarms(reservation)
            }
        }.start()
    }

    @SuppressLint("ScheduleExactAlarm")
    fun scheduleSingleMovieAlarms(reservation: ReservationInfo) {
        val notificationTimeMillis = getNotificationTimeMillis(reservation)
        if (!isValidTicketTime(reservation, notificationTimeMillis)) return
        val pendingIntent = getCreatePendingIntent(reservation)
        Log.d(
            "AlarmDebug",
            "알람 등록 - 제목: ${reservation.title}, 해시: ${reservation.hashCode()}",
        )
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val alarmClock = AlarmManager.AlarmClockInfo(notificationTimeMillis, null)
            alarmManager.setAlarmClock(alarmClock, pendingIntent)
        } else {
            alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                notificationTimeMillis,
                pendingIntent,
            )
        }
    }

    private fun getCreatePendingIntent(reservation: ReservationInfo): PendingIntent {
        val intent =
            Intent(context, AlarmReceiver::class.java).apply {
                putExtra(RESERVATION_INFO_KEY, reservation)
            }

        val pendingIntent =
            PendingIntent.getBroadcast(
                context,
                reservation.hashCode(),
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE,
            )
        return pendingIntent
    }

    private fun getNotificationTimeMillis(reservation: ReservationInfo): Long {
        val startTimeMillis = getMovieStartTimeMillis(reservation.date, reservation.time)
        val notificationTimeMillis =
            startTimeMillis - (AlarmReceiver.ALARM_SHOWUP_MINUTE * 60 * 1000)
        return notificationTimeMillis
    }

    fun isValidTicketTime(
        reservation: ReservationInfo,
        notificationTimeMillis: Long,
    ): Boolean {
        if (System.currentTimeMillis() > notificationTimeMillis) return false
        if (isAlarmAlreadySet(reservation)) return false
        return true
    }

    fun cancelAllAlarms() {
        Thread {
            val reservations = getAllReservations()
            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

            for (reservation in reservations) {
                val pendingIntent = getRemovePendingIntent(reservation)
                Log.d(
                    "AlarmDebug",
                    "알람 취소 시도 - 제목: ${reservation.title}, ID: ${reservation.id}, 해시: ${reservation.hashCode()}",
                )
                Log.d("AlarmDebug", "PendingIntent 존재 여부: $pendingIntent")
                if (pendingIntent != null) {
                    alarmManager.cancel(pendingIntent)
                    pendingIntent.cancel()
                }
            }
        }.start()
    }

    private fun getRemovePendingIntent(reservation: ReservationInfo): PendingIntent? {
        val intent =
            Intent(context, AlarmReceiver::class.java).apply {
                putExtra(RESERVATION_INFO_KEY, reservation)
            }

        val pendingIntent: PendingIntent? =
            PendingIntent.getBroadcast(
                context,
                reservation.hashCode(),
                intent,
                PendingIntent.FLAG_NO_CREATE or PendingIntent.FLAG_IMMUTABLE,
            )
        return pendingIntent
    }

    private fun getAllReservations(): List<ReservationInfo> {
        val database = AppDatabase.getDatabase(context)
        Log.d(
            "AlarmManager",
            "getAllReservations() ${database.reservationInfoDao().getAllReservations()}",
        )
        return database.reservationInfoDao().getAllReservations()
    }

    private fun isAlarmAlreadySet(reservation: ReservationInfo): Boolean {
        val intent =
            Intent(context, AlarmReceiver::class.java).apply {
                putExtra(RESERVATION_INFO_KEY, reservation)
            }

        val pendingIntent =
            PendingIntent.getBroadcast(
                context,
                reservation.hashCode(),
                intent,
                PendingIntent.FLAG_NO_CREATE or PendingIntent.FLAG_IMMUTABLE,
            )

        return pendingIntent != null
    }

    private fun getMovieStartTimeMillis(
        dateStr: LocalDate,
        timeStr: String,
    ): Long {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
        val date = dateFormat.parse("$dateStr $timeStr")
        return date?.time ?: System.currentTimeMillis()
    }
}

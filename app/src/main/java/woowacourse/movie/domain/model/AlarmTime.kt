package woowacourse.movie.domain.model

import android.util.Log
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

interface AlarmTime {
    fun calculated(
        dateTime: LocalDateTime,
        alarmTimeMinutesBefore: Int,
    ): Long?
}

class AlarmTimeBeforeMinute : AlarmTime {
    override fun calculated(
        dateTime: LocalDateTime,
        alarmTimeMinutesBefore: Int,
    ): Long? {

        Log.d(TAG, "datetime: $dateTime") //   datetime: 2024-05-29T03:47

        val timeLong = dateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
        Log.d(TAG, "dateTime long: $timeLong")

        val alarmTime = timeLong - alarmTimeMinutesBefore * 60 * 1000
        val currentTime = System.currentTimeMillis()

        val alarmTimeLocalDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(alarmTime), ZoneId.of("Asia/Seoul"))
        Log.d(TAG, "calculated: $alarmTimeLocalDateTime") // calculated: 2024-05-29T03:17

        return if (alarmTime > currentTime) {
            alarmTime
        } else {
            null // 이미 영화 시간이 지났다
        }
    }

    companion object {
        private const val TAG = "AlarmTime"
    }
}

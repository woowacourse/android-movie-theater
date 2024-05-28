package woowacourse.movie.domain.model

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
        val time = dateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()

        val alarmTime = time - alarmTimeMinutesBefore * 60 * 1000
        val currentTime = System.currentTimeMillis()

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

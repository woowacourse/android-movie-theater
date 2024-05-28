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
        val timeLong = dateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()

        val alarmTime = timeLong - alarmTimeMinutesBefore * 60 * 1000
        val currentTime = System.currentTimeMillis()


        return if (alarmTime > currentTime) {
            alarmTime
        } else {
            null // 이미 영화 시간이 지났다
        }
    }
}

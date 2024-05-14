package woowacourse.movie.setting

import woowacourse.movie.model.MovieReservation

interface AlarmSetting {
    fun setAlarm(movieReservation: MovieReservation)

    fun cancelAlarm(movieReservation: MovieReservation)
}

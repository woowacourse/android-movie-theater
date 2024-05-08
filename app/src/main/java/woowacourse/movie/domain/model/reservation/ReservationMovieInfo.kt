package woowacourse.movie.domain.model.reservation

import android.content.Context
import android.util.Log
import woowacourse.movie.R
import woowacourse.movie.domain.model.ScreeningInfo
import woowacourse.movie.domain.model.reservation.date.ScreeningDateTime

class ReservationMovieInfo(
    val title: String,
    val theaterName: String,
    screeningInfo: ScreeningInfo,
) {
    val dateTime = ScreeningDateTime(screeningInfo)

    fun changeDate(
        year: Int,
        month: Int,
        day: Int,
    ) {
        dateTime.changeScreeningDate(year, month, day)
    }

    fun changeTime(
        hour: Int,
        minute: Int,
    ) {
        dateTime.changeScreeningTime(hour, minute)
    }

    fun reservationSchedule(context: Context): String {
        val info = context.getString(
            R.string.reservation_schedule_format, "24-3-1",
            "15:00",
            theaterName,
        )
        Log.d("info",info)
        return info
    }
}

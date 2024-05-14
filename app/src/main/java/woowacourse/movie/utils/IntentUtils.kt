package woowacourse.movie.utils

import android.content.Intent
import android.os.Build
import woowacourse.movie.model.Reservation

fun Intent.getParcelableReservationExtra(key: String): Reservation? {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        getParcelableExtra(key, Reservation::class.java)
    } else {
        getParcelableExtra(key)
    }
}

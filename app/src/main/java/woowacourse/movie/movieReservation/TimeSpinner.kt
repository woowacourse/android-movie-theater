package woowacourse.movie.movieReservation

import android.os.Bundle
import woowacourse.movie.common.model.LocalFormattedTime

class TimeSpinner(val spinner: SaveStateSpinner) {
    fun make(savedInstanceState: Bundle?) {
        spinner.load(savedInstanceState)
    }

    fun setTimes(times: List<LocalFormattedTime>) {
        spinner.initSpinner(times)
    }

    fun save(savedInstanceState: Bundle) {
        spinner.save(savedInstanceState)
    }
}

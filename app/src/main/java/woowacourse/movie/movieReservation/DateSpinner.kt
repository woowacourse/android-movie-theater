package woowacourse.movie.movieReservation

import android.os.Bundle
import woowacourse.movie.common.model.LocalFormattedDate
import woowacourse.movie.common.model.MovieViewData

class DateSpinner(val spinner: SaveStateSpinner) {
    var dates: List<LocalFormattedDate> = emptyList()
    fun make(
        savedInstanceState: Bundle?,
        movie: MovieViewData
    ) {
        dates = movie.date.toList().map { LocalFormattedDate(it) }

        spinner.initSpinner(dates)
        spinner.load(savedInstanceState)
    }

    fun save(savedInstanceState: Bundle) {
        spinner.save(savedInstanceState)
    }
}

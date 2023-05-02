package woowacourse.movie.view.widget

import android.os.Bundle
import woowacourse.movie.data.LocalFormattedDate
import woowacourse.movie.data.MovieViewData

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

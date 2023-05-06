package woowacourse.movie.view.widget

import android.R
import android.widget.ArrayAdapter
import android.widget.Spinner
import woowacourse.movie.domain.movieTimePolicy.MovieTime
import woowacourse.movie.domain.movieTimePolicy.WeekdayMovieTime
import woowacourse.movie.domain.movieTimePolicy.WeekendMovieTime
import woowacourse.movie.view.data.LocalFormattedTime
import java.time.LocalDate

class TimeSpinnerManager(val spinner: Spinner) {
    fun initSpinner(
        date: LocalDate,
        timeIndex: Int
    ) {
        val times = MovieTime(
            listOf(WeekdayMovieTime, WeekendMovieTime)
        ).determine(date).map { LocalFormattedTime(it) }
        val dateAdapter =
            ArrayAdapter(spinner.context, R.layout.simple_spinner_dropdown_item, times)
        spinner.adapter = dateAdapter
        spinner.setSelection(timeIndex)
    }
}

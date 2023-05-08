package woowacourse.movie.view.moviereservation.widget

import android.widget.Spinner
import woowacourse.movie.util.LocalFormattedDate
import java.time.LocalDate

class DateSpinner(spinner: Spinner, savedStateKey: String) :
    SaveStateSpinner(savedStateKey, spinner) {
    fun setAdapter(
        dates: List<LocalDate>,
    ) {
        val dates = dates.map { LocalFormattedDate(it) }
        setArrayAdapter(dates)
    }

    fun getSelectedDate(): LocalDate {
        return (spinner.selectedItem as LocalFormattedDate).date
    }
}

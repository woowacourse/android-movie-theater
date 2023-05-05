package woowacourse.movie.view

import android.widget.Spinner
import woowacourse.movie.model.MovieUiModel
import woowacourse.movie.util.LocalFormattedDate
import java.time.LocalDate

class DateSpinner(spinner: Spinner, savedStateKey: String) :
    SaveStateSpinner(savedStateKey, spinner) {
    fun setAdapter(
        movieUiModel: MovieUiModel,
    ) {
        val dates = movieUiModel.getDateList().map { LocalFormattedDate(it) }
        setArrayAdapter(dates)
    }
    fun getSelectedDate(): LocalDate {
        return (spinner.selectedItem as LocalFormattedDate).date
    }
}

package woowacourse.movie.view.moviereservation.widget

import android.widget.Spinner
import woowacourse.movie.util.LocalFormattedTime
import java.time.LocalTime

class TimeSpinner(spinner: Spinner, savedStateKey: String) :
    SaveStateSpinner(savedStateKey, spinner) {
    fun setAdapter(times: List<LocalTime>) {
        setArrayAdapter( times.map { LocalFormattedTime(it) })
    }

    fun getSelectedTime(): LocalTime {
        return ((spinner.selectedItem as LocalFormattedTime).time)
    }
}

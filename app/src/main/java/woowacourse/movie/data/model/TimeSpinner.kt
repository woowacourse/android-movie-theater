package woowacourse.movie.data.model

import android.os.Bundle
import android.widget.Spinner
import woowacourse.movie.data.model.uimodel.TheaterUIModel
import java.time.LocalDate
import java.time.LocalTime

class TimeSpinner(spinner: Spinner, savedStateKey: String, val theater: TheaterUIModel) :
    SaveStateSpinner(savedStateKey, spinner) {
    fun make(savedInstanceState: Bundle?, date: LocalDate) {
        val times = theater.timeTable.map { LocalFormattedTime(it) }
        setArrayAdapter(times)
        load(savedInstanceState)
    }

    fun getSelectedTime(): LocalTime {
        return (spinner.selectedItem as LocalFormattedTime).time
    }
}

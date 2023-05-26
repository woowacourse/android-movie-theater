package woowacourse.movie.data.model

import android.os.Bundle
import woowacourse.movie.data.model.uimodel.MovieUIModel
import java.time.LocalDateTime

class MovieDateTimePicker(
    private val dateSpinner: DateSpinner,
    private val timeSpinner: TimeSpinner,
) {

    fun makeView(movieUiModel: MovieUIModel, savedInstanceState: Bundle?) {
        dateSpinner.make(savedInstanceState, movieUiModel, timeSpinner)
    }

    fun save(outState: Bundle) {
        dateSpinner.save(outState)
        timeSpinner.save(outState)
    }

    fun getSelectedDateTime(): LocalDateTime {
        val date = dateSpinner.getSelectedDate()
        val time = timeSpinner.getSelectedTime()
        return LocalDateTime.of(date, time)
    }
}

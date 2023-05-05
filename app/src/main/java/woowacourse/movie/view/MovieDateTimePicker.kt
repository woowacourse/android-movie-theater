package woowacourse.movie.view

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import woowacourse.movie.model.MovieUiModel
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

class MovieDateTimePicker(
    val dateSpinner: DateSpinner,
    val timeSpinner: TimeSpinner,
) {

    fun setDateList(movieUiModel: MovieUiModel) {
        dateSpinner.setAdapter(movieUiModel)
    }

    fun setDateSelectListener(selectEvent: (LocalDate) -> Unit, savedInstanceState: Bundle?) {
        dateSpinner.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) = Unit
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                selectEvent(getSelectedDate())
                timeSpinner.load(savedInstanceState)
            }
        }
    }

    fun save(outState: Bundle) {
        dateSpinner.save(outState)
        timeSpinner.save(outState)
    }

    fun getSelectedDateTime(): LocalDateTime {
        val date = getSelectedDate()
        val time = getSelectedTime()
        return LocalDateTime.of(date, time)
    }

    private fun getSelectedDate(): LocalDate {
        return dateSpinner.getSelectedDate()
    }

    private fun getSelectedTime(): LocalTime {
        return timeSpinner.getSelectedTime()
    }
}

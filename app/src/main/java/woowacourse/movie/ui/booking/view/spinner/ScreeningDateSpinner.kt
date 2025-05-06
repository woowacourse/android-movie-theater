package woowacourse.movie.ui.booking.view.spinner

import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import java.time.LocalDate

class ScreeningDateSpinner(
    private val spinner: Spinner,
    dates: List<LocalDate>,
) {
    init {
        spinner.adapter =
            ArrayAdapter(
                spinner.context,
                android.R.layout.simple_spinner_item,
                dates,
            )
    }

    fun setOnItemSelectedListener(listener: AdapterView.OnItemSelectedListener) {
        spinner.onItemSelectedListener = listener
    }

    fun select(position: Int) {
        spinner.setSelection(position)
    }
}

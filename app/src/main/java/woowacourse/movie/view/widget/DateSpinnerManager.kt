package woowacourse.movie.view.widget

import android.R
import android.widget.ArrayAdapter
import android.widget.Spinner
import woowacourse.movie.view.data.LocalFormattedDate

class DateSpinnerManager(val spinner: Spinner) {
    fun initSpinner(
        dates: List<LocalFormattedDate>,
        dateIndex: Int
    ) {
        val dateAdapter =
            ArrayAdapter(spinner.context, R.layout.simple_spinner_dropdown_item, dates)
        spinner.adapter = dateAdapter
        spinner.setSelection(dateIndex)
    }
}

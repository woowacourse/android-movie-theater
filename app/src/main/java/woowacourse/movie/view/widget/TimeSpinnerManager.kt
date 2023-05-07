package woowacourse.movie.view.widget

import android.R
import android.widget.ArrayAdapter
import android.widget.Spinner
import woowacourse.movie.view.data.LocalFormattedTime

class TimeSpinnerManager(val spinner: Spinner) {
    fun initSpinner(
        times: List<LocalFormattedTime>,
        timeIndex: Int
    ) {
        val dateAdapter =
            ArrayAdapter(spinner.context, R.layout.simple_spinner_dropdown_item, times)
        spinner.adapter = dateAdapter
        spinner.setSelection(timeIndex)
    }
}

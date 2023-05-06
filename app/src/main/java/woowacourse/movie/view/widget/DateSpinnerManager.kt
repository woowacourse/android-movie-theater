package woowacourse.movie.view.widget

import android.R
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import woowacourse.movie.view.data.LocalFormattedDate

class DateSpinnerManager(val spinner: Spinner) {
    fun initSpinner(
        dates: List<LocalFormattedDate>,
        timeSpinnerManager: TimeSpinnerManager,
        dateIndex: Int,
        timeIndex: Int
    ) {
        val dateAdapter =
            ArrayAdapter(spinner.context, R.layout.simple_spinner_dropdown_item, dates)
        spinner.adapter = dateAdapter
        spinner.setSelection(dateIndex)
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                timeSpinnerManager.initSpinner(dates[p2].date, timeIndex)
            }
        }
    }
}

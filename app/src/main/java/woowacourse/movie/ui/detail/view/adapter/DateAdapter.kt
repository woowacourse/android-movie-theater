package woowacourse.movie.ui.detail.view.adapter

import android.content.Context
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import woowacourse.movie.domain.model.DateRange
import woowacourse.movie.ui.detail.view.SelectDateListener
import java.time.LocalDate

class DateAdapter(
    context: Context,
    dateRange: DateRange,
    private val selectDateListener: SelectDateListener,
) : ArrayAdapter<LocalDate>(context, android.R.layout.simple_spinner_item, dateRange.allDates()), AdapterView.OnItemSelectedListener {
    init {
        Log.d(TAG, "init: ")
        this.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
    }

    override fun onItemSelected(
        parent: AdapterView<*>?,
        view: View?,
        position: Int,
        id: Long,
    ) {
        Log.d(TAG, "onItemSelected: position: $position, ")
        selectDateListener.selectDate(position)
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        Log.e("ScreenDetailDateTimeSpinnerView", "Nothing Selected")
    }

    companion object {
        const val TAG = "DateAdapter"
    }
}

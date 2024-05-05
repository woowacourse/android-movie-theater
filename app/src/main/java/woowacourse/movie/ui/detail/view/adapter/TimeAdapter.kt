package woowacourse.movie.ui.detail.view.adapter

import android.content.Context
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import woowacourse.movie.domain.model.WeeklyScreenTimePolicy
import woowacourse.movie.ui.detail.view.SelectTimeListener
import java.time.LocalDate
import java.time.LocalTime

class TimeAdapter(
    context: Context,
    screenTimePolicy: WeeklyScreenTimePolicy,
    date: LocalDate,
    private val selectTimeListener: SelectTimeListener,
) : ArrayAdapter<LocalTime>(
        context,
        android.R.layout.simple_spinner_item,
        screenTimePolicy.screeningTimes(date).toList(),
    ),
    AdapterView.OnItemSelectedListener {
    init {
        this.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
    }

    override fun onItemSelected(
        parent: AdapterView<*>?,
        view: View?,
        position: Int,
        id: Long,
    ) {
        selectTimeListener.selectTime(position)
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        Log.e("ScreenDetailDateTimeSpinnerView", "Nothing Selected")
    }
}

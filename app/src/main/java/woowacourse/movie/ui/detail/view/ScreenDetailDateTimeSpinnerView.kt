package woowacourse.movie.ui.detail.view

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import woowacourse.movie.R
import woowacourse.movie.databinding.HolderSpinnerDateTimeBinding
import woowacourse.movie.domain.model.DateRange
import woowacourse.movie.domain.model.ScreenTimePolicy
import woowacourse.movie.ui.detail.view.adapter.DateAdapter
import woowacourse.movie.ui.detail.view.adapter.TimeAdapter
import java.time.LocalDate

class ScreenDetailDateTimeSpinnerView(context: Context, attrs: AttributeSet? = null) : DateTimeSpinnerView,
    ConstraintLayout(context, attrs) {
    private val binding: HolderSpinnerDateTimeBinding by lazy {
        DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.holder_spinner_date_time, this, true)
    }

    private lateinit var dateAdapter: DateAdapter
    private lateinit var timeAdapter: TimeAdapter

    override fun show(
        dateRange: DateRange,
        screenTimePolicy: ScreenTimePolicy,
        onDateSelectedListener: OnItemSelectedListener,
        onTimeSelectedListener: OnItemSelectedListener,
    ) {
        initDateAdapter(dateRange, screenTimePolicy, onDateSelectedListener)
        initTimeAdapter(dateRange.start, screenTimePolicy, onTimeSelectedListener)
    }

    override fun restoreDatePosition(position: Int) {
        binding.spnDate.setSelection(position)
    }

    override fun restoreTimePosition(position: Int) {
        binding.spnTime.setSelection(position)
    }

    override fun selectedDatePosition(): Int {
        return binding.spnDate.selectedItemPosition
    }

    override fun selectedTimePosition(): Int {
        return binding.spnTime.selectedItemPosition
    }

    private fun initDateAdapter(
        dateRange: DateRange,
        screenTimePolicy: ScreenTimePolicy,
        onDateSelectedListener: OnItemSelectedListener,
    ) {
        dateAdapter =
            DateAdapter(context, dateRange) { position ->
                val times = screenTimePolicy.screeningTimes(dateRange.allDates()[position]).toList()
                Log.d(TAG, "initDateAdapter: times: $times")

                timeAdapter.clear()
                timeAdapter.addAll(times)
                onDateSelectedListener.onItemSelected(position)
            }
        binding.dateAdapter = dateAdapter
        binding.spnDate.onItemSelectedListener = dateAdapter
    }

    private fun initTimeAdapter(
        date: LocalDate,
        screenTimePolicy: ScreenTimePolicy,
        onTimeSelectListener: OnItemSelectedListener,
    ) {
        timeAdapter =
            TimeAdapter(context, screenTimePolicy, date, onTimeSelectedListener = { position ->
                Log.d(TAG, "initTimeAdapter: position: $position")
                onTimeSelectListener.onItemSelected(position)
            })

        binding.timeAdapter = timeAdapter
        binding.spnTime.onItemSelectedListener = timeAdapter
    }

    companion object {
        const val TAG = "ScreenDetailDateTimeSpinnerView"
    }
}

package woowacourse.movie.ui.booking

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.AdapterView
import androidx.constraintlayout.widget.ConstraintLayout
import woowacourse.movie.R
import woowacourse.movie.databinding.DateTimeSpinnerBinding
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

class DateTimeSpinner(
    context: Context,
    attrs: AttributeSet,
) : ConstraintLayout(context, attrs), DateTimeContract.View {

    lateinit var binding: DateTimeSpinnerBinding
    private val dateAdapter: SpinnerAdapter<LocalDate> by lazy {
        SpinnerAdapter(context, R.layout.screening_date_time_item, R.id.textSpinnerDateTime)
    }
    private val timeAdapter: SpinnerAdapter<LocalTime> by lazy {
        SpinnerAdapter(context, R.layout.screening_date_time_item, R.id.textSpinnerDateTime)
    }
    val selectedDateTime: LocalDateTime
        get() = LocalDateTime.of(
            dateAdapter.getItem(binding.movieDateSpinner.selectedItemPosition),
            timeAdapter.getItem(binding.movieTimeSpinner.selectedItemPosition)
        )

    init {
        initBinding()
        initAdapters()
    }

    private fun initBinding() {
        val layoutInflater = LayoutInflater.from(context)

        binding = DateTimeSpinnerBinding.inflate(layoutInflater)
    }

    private fun initAdapters() {
        binding.movieDateSpinner.adapter = dateAdapter
        binding.movieTimeSpinner.adapter = timeAdapter
    }

    override fun setDates(screeningDates: List<LocalDate>) {
        dateAdapter.initItems(screeningDates)
    }

    override fun setTimes(screeningTimes: List<LocalTime>) {
        timeAdapter.initItems(screeningTimes)
    }

    override fun initDateSelectedListener(updateTimes: (selectedDate: LocalDate) -> (Unit)) {
        binding.movieDateSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long,
            ) {
                dateAdapter.getItem(position)?.let { screeningDate ->
                    updateTimes(screeningDate)
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) = Unit
        }
    }
}

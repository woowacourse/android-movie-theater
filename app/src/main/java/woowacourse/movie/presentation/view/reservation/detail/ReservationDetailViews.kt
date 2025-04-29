package woowacourse.movie.presentation.view.reservation.detail

import android.content.Context
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import woowacourse.movie.R
import woowacourse.movie.databinding.FragmentReservationDetailBinding
import woowacourse.movie.presentation.extension.setImage
import woowacourse.movie.presentation.extension.toDateTimeFormatter
import woowacourse.movie.presentation.model.MovieUiModel
import woowacourse.movie.presentation.util.CustomAlertDialog
import java.time.LocalDate
import java.time.LocalTime

class ReservationDetailViews(
    private val context: Context,
    private val binding: FragmentReservationDetailBinding,
) {
    private val dateAdapter = createSpinnerAdapter<LocalDate>()
    private val timeAdapter = createSpinnerAdapter<LocalTime>()

    val dialog: CustomAlertDialog by lazy { CustomAlertDialog(context) }

    fun bindMovieInfo(movie: MovieUiModel) {
        binding.tvReservationTitle.text = movie.title
        binding.tvScreeningPeriod.text = formatPeriod(movie)
        binding.tvReservationRunningTime.text =
            context.getString(R.string.running_time, movie.runningTime.toString())
        movie.poster.setImage(binding.ivReservationPoster)
    }

    fun setOnReservationCountChanged(
        onDecrease: () -> Unit,
        onIncrease: () -> Unit,
    ) {
        binding.btnReservationCountMinus.setOnClickListener { onDecrease() }
        binding.btnReservationCountPlus.setOnClickListener { onIncrease() }
    }

    fun setOnFinishClickListener(action: () -> Unit) {
        binding.btnReservationFinish.setOnClickListener { action() }
    }

    fun selectedSpinnerDateAndTime(): Pair<LocalDate?, LocalTime?> =
        binding.spinnerReservationDate.selectedItem as? LocalDate to binding.spinnerReservationTime.selectedItem as? LocalTime

    fun reservationCount(): Int? =
        binding.tvReservationCount.text
            .toString()
            .toIntOrNull()

    fun updateReservationCount(
        newCount: Int,
        isClickable: Boolean,
    ) {
        binding.tvReservationCount.text = newCount.toString()
        updateReservationCountMinusButton(isClickable)
    }

    fun setSpinners(
        onDateSelected: (LocalDate) -> Unit,
        shouldIgnoreNext: () -> Boolean,
        clearIgnoreNext: () -> Unit,
    ) {
        binding.spinnerReservationDate.adapter = dateAdapter
        binding.spinnerReservationTime.adapter = timeAdapter

        binding.spinnerReservationDate.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View?,
                    position: Int,
                    id: Long,
                ) {
                    if (shouldIgnoreNext()) {
                        clearIgnoreNext()
                        return
                    }
                    val selected = parent.getItemAtPosition(position) as LocalDate
                    onDateSelected(selected)
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }
    }

    fun updateDateSpinnerItems(
        items: List<LocalDate>,
        selected: LocalDate? = null,
    ) {
        updateSpinnerItems(binding.spinnerReservationDate, dateAdapter, items, selected)
    }

    fun updateTimeSpinnerItems(
        items: List<LocalTime>,
        selected: LocalTime? = null,
    ) {
        updateSpinnerItems(binding.spinnerReservationTime, timeAdapter, items, selected)
    }

    private fun updateReservationCountMinusButton(isEnabled: Boolean) {
        binding.btnReservationCountMinus.apply {
            alpha = if (isEnabled) 1f else 0.4f
            this.isClickable = isEnabled
        }
    }

    private fun formatPeriod(movie: MovieUiModel): String {
        val formatter =
            context.getString(R.string.movie_screening_period_format).toDateTimeFormatter()
        val start = movie.screeningPeriod.startDate.format(formatter)
        val end = movie.screeningPeriod.endDate.format(formatter)
        return context.getString(R.string.movie_date, start, end)
    }

    private fun <T> createSpinnerAdapter(): ArrayAdapter<T> =
        ArrayAdapter(context, android.R.layout.simple_spinner_item, mutableListOf<T>()).apply {
            setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }

    private fun <T> updateSpinnerItems(
        spinner: Spinner,
        adapter: ArrayAdapter<T>,
        items: List<T>,
        selectedItem: T?,
    ) {
        adapter.clear()
        adapter.addAll(items)
        adapter.notifyDataSetChanged()

        val position = selectedItem?.let { adapter.getPosition(it) } ?: 0
        spinner.setSelection(position)
    }
}

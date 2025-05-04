package woowacourse.movie.view.reservation

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import woowacourse.movie.R
import woowacourse.movie.databinding.ActivityReservationBinding
import woowacourse.movie.domain.model.ReservationCount
import woowacourse.movie.domain.model.ReservationInfo
import woowacourse.movie.domain.model.Screening
import woowacourse.movie.view.base.BaseActivity
import woowacourse.movie.view.extension.getParcelableCompat
import woowacourse.movie.view.reservation.seat.SeatSelectionActivity
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

class ReservationActivity :
    BaseActivity<ActivityReservationBinding>(R.layout.activity_reservation),
    ReservationContract.View {
    private val presenter = ReservationPresenter(this)
    private var shouldIgnoreNextSelection = false

    private val dateSpinnerAdapter: ArrayAdapter<LocalDate> by lazy {
        ArrayAdapter(this, android.R.layout.simple_spinner_item, mutableListOf<LocalDate>()).apply {
            setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }
    }
    private val timeSpinnerAdapter: ArrayAdapter<LocalTime> by lazy {
        ArrayAdapter(this, android.R.layout.simple_spinner_item, mutableListOf<LocalTime>()).apply {
            setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }
    }

    private val unavailableDateTimeDialog by lazy {
        AlertDialog
            .Builder(this)
            .setMessage(R.string.unavailable_reservation_message)
            .setPositiveButton(R.string.confirm) { _, _ ->
                onBackPressedDispatcher.onBackPressed()
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val screening = intent?.getParcelableCompat<Screening>(BUNDLE_KEY_MOVIE)
        val count = savedInstanceState?.getInt(RESTORE_BUNDLE_KEY_RESERVATION_NUMBER)
        val reservationDateTime =
            savedInstanceState?.getString(RESTORE_BUNDLE_KEY_RESERVATION_DATETIME)
        presenter.loadData(screening, count, reservationDateTime)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        val selectedDate = binding.spinnerReservationDate.selectedItem as? LocalDate
        val selectedTime = binding.spinnerReservationTime.selectedItem as? LocalTime

        val reservationDateTime =
            if (selectedDate != null && selectedTime != null) {
                LocalDateTime.of(
                    selectedDate,
                    selectedTime,
                )
            } else {
                ""
            }

        outState.apply {
            putString(RESTORE_BUNDLE_KEY_RESERVATION_DATETIME, reservationDateTime.toString())
            putInt(
                RESTORE_BUNDLE_KEY_RESERVATION_NUMBER,
                binding.tvReservationCount.text.toString().toIntOrNull() ?: 1,
            )
        }
    }

    override fun showMovieDetail(screening: Screening) {
        setMovieInfo(screening)
        setupDateSpinner()
    }

    override fun notifyInvalidReservationInfo() {
        showToast(getString(R.string.invalid_reservation_message))
    }

    override fun updateReservationCount(count: Int) {
        binding.count = count
        binding.invalidateAll()
    }

    override fun updateDateSet(
        dates: List<LocalDate>,
        selectedDate: LocalDate?,
    ) {
        dateSpinnerAdapter.clear()
        dateSpinnerAdapter.addAll(dates)
        dateSpinnerAdapter.notifyDataSetChanged()

        selectedDate?.let {
            val position = dateSpinnerAdapter.getPosition(it)
            binding.spinnerReservationDate.setSelection(position)
        }
    }

    override fun updateTimeSet(
        times: List<LocalTime>,
        selectedTime: LocalTime?,
    ) {
        timeSpinnerAdapter.clear()
        timeSpinnerAdapter.addAll(times)
        timeSpinnerAdapter.notifyDataSetChanged()

        selectedTime?.let {
            val timePosition = timeSpinnerAdapter.getPosition(it)
            if (timePosition >= 0) {
                binding.spinnerReservationTime.setSelection(timePosition)
            }
        }
    }

    override fun notifyUnavailableDate() {
        unavailableDateTimeDialog.show()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressedDispatcher.onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun navigateToSeatSelectionScreen(reservationInfo: ReservationInfo) {
        val intent = SeatSelectionActivity.newIntent(this, reservationInfo)
        startActivity(intent)
    }

    fun submitReservation(view: View) {
        presenter.onReserve(
            reservationDate = binding.spinnerReservationDate.selectedItem as? LocalDate,
            reservationTime = binding.spinnerReservationTime.selectedItem as? LocalTime,
        )
    }

    fun increase(view: View) {
        presenter.increaseCount(1)
    }

    fun decrease(view: View) {
        runCatching {
            presenter.decreaseCount(1)
        }.onFailure {
            showToast(
                getString(
                    R.string.invalid_reservation_count_message,
                    ReservationCount.MINIMUM_RESERVATION_COUNT,
                ),
            )
        }
    }

    private fun setMovieInfo(screening: Screening) {
        binding.screening = screening
    }

    private fun setupDateSpinner() {
        binding.spinnerReservationDate.adapter = dateSpinnerAdapter
        binding.spinnerReservationTime.adapter = timeSpinnerAdapter

        binding.spinnerReservationDate.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View?,
                    position: Int,
                    id: Long,
                ) {
                    if (shouldIgnoreNextSelection) {
                        shouldIgnoreNextSelection = false
                        return
                    }

                    val selectedDate = parent.getItemAtPosition(position) as LocalDate
                    presenter.selectDate(selectedDate)
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }

        presenter.selectDate(LocalDate.now())
    }

    companion object {
        private const val BUNDLE_KEY_MOVIE = "movie"
        private const val RESTORE_BUNDLE_KEY_RESERVATION_DATETIME = "reservation_datetime"
        private const val RESTORE_BUNDLE_KEY_RESERVATION_NUMBER = "reservation_number"

        fun newIntent(
            context: Context,
            screening: Screening,
        ): Intent =
            Intent(context, ReservationActivity::class.java).putExtra(
                BUNDLE_KEY_MOVIE,
                screening,
            )
    }
}

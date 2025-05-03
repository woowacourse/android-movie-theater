package woowacourse.movie.presentation.booking

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import woowacourse.movie.R
import woowacourse.movie.common.BaseActivity
import woowacourse.movie.common.util.bundleSerializable
import woowacourse.movie.common.util.intentSerializable
import woowacourse.movie.databinding.ActivityBookingBinding
import woowacourse.movie.domain.model.Screening
import woowacourse.movie.domain.model.Ticket
import woowacourse.movie.domain.model.movie.Movie
import woowacourse.movie.presentation.seats.SeatsActivity
import java.time.LocalDate
import java.time.LocalTime

class BookingActivity :
    BaseActivity<ActivityBookingBinding>(R.layout.activity_booking),
    BookingContract.View {
    private lateinit var presenter: BookingPresenter
    private lateinit var screening: Screening

    private lateinit var dateAdapter: ArrayAdapter<LocalDate>
    private lateinit var timeAdapter: ArrayAdapter<LocalTime>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (!fetchScreening()) return
        presenter = BookingPresenter(this, screening)
        initView()
        presenter.loadBooking()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putSerializable(EXTRA_TICKET, presenter.ticket)
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        val restoredTicket = savedInstanceState.bundleSerializable(EXTRA_TICKET, Ticket::class.java)
        restoredTicket?.let { presenter.restoreTicket(it) }
    }

    override fun showMovie(movie: Movie) {
        binding.movie = movie
    }

    override fun showHeadCount(count: Int) {
        binding.textviewHeadcount.text = count.toString()
    }

    override fun updateDecreaseButtonState(isEnabled: Boolean) {
        binding.buttonDecrease.isEnabled = isEnabled
    }

    override fun updateIncreaseButtonState(isEnabled: Boolean) {
        binding.buttonIncrease.isEnabled = isEnabled
    }

    override fun showBookableDates(
        dates: List<LocalDate>,
        selectedDate: LocalDate,
    ) {
        dateAdapter.clear()
        dateAdapter.addAll(dates)
        binding.spinnerDate.setSelection(dateAdapter.getPosition(selectedDate))

        binding.spinnerDate.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long,
                ) {
                    presenter.selectScreeningDate(dates[position])
                }

                override fun onNothingSelected(parent: AdapterView<*>?) = Unit
            }
    }

    override fun showBookableTimes(
        times: List<LocalTime>,
        selectedTime: LocalTime,
    ) {
        timeAdapter.clear()
        timeAdapter.addAll(times)
        binding.spinnerTime.setSelection(timeAdapter.getPosition(selectedTime))
        binding.spinnerTime.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long,
                ) {
                    presenter.selectScreeningTime(times[position])
                }

                override fun onNothingSelected(parent: AdapterView<*>?) = Unit
            }
    }

    override fun navigateToSeatSelect(ticket: Ticket) {
        val intent = SeatsActivity.newIntent(this, ticket)
        startActivity(intent)
    }

    private fun fetchScreening(): Boolean {
        val data = intent.intentSerializable(EXTRA_SCREENING, Screening::class.java)
        if (data == null) {
            Toast.makeText(this, ERROR_INTENT_KEY, Toast.LENGTH_SHORT).show()
            finish()
            return false
        }
        screening = data
        return true
    }

    private fun initView() {
        initButtons()
        initSpinners()
    }

    private fun initButtons() {
        binding.buttonIncrease.setOnClickListener {
            presenter.increaseHeadCount()
        }
        binding.buttonDecrease.setOnClickListener {
            presenter.decreaseHeadCount()
        }
        binding.buttonSelect.setOnClickListener {
            presenter.confirmBooking()
        }
    }

    private fun initSpinners() {
        dateAdapter =
            ArrayAdapter(this, android.R.layout.simple_spinner_item, mutableListOf<LocalDate>())
        timeAdapter =
            ArrayAdapter(this, android.R.layout.simple_spinner_item, mutableListOf<LocalTime>())
        binding.spinnerDate.adapter = dateAdapter
        binding.spinnerTime.adapter = timeAdapter
    }

    companion object {
        fun newIntent(
            context: Context?,
            screening: Screening,
        ): Intent =
            Intent(context, BookingActivity::class.java).apply {
                putExtra(EXTRA_SCREENING, screening)
            }

        private const val EXTRA_SCREENING = "screening"
        private const val EXTRA_TICKET = "ticket"
        private const val ERROR_INTENT_KEY = "[ERROR] 키 값이 올바르지 않습니다."
    }
}

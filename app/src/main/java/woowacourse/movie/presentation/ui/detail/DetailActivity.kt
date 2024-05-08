package woowacourse.movie.presentation.ui.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import woowacourse.movie.R
import woowacourse.movie.data.repository.remote.DummyScreens
import woowacourse.movie.databinding.ActivityDetailBinding
import woowacourse.movie.domain.model.Screen
import woowacourse.movie.domain.model.ScreenDate
import woowacourse.movie.presentation.base.BaseMvpBindingActivity
import woowacourse.movie.presentation.model.ReservationInfo
import woowacourse.movie.presentation.ui.detail.DetailContract.View
import woowacourse.movie.presentation.ui.detail.adapter.SpinnerDateAdapter
import woowacourse.movie.presentation.ui.detail.adapter.SpinnerTimeAdapter
import woowacourse.movie.presentation.ui.seatselection.SeatSelectionActivity
import java.time.LocalDate
import java.time.LocalTime

class DetailActivity : BaseMvpBindingActivity<ActivityDetailBinding>(), View {
    override val layoutResourceId: Int
        get() = R.layout.activity_detail
    override val presenter: DetailPresenter by lazy { DetailPresenter(this, DummyScreens) }

    private val spinnerDateAdapter: SpinnerDateAdapter by lazy {
        SpinnerDateAdapter(this, presenter)
    }
    private val spinnerTimeAdapter: SpinnerTimeAdapter by lazy {
        SpinnerTimeAdapter(this, presenter)
    }

    override fun initStartView() {
        binding.presenter = presenter

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val movieId = intent.getIntExtra(PUT_EXTRA_KEY_MOVIE_ID, DEFAULT_ID)
        val theaterId = intent.getIntExtra(PUT_EXTRA_KEY_THEATER_ID, DEFAULT_ID)
        initAdapter()
        presenter.loadScreen(movieId, theaterId)
    }

    private fun initAdapter() {
        binding.spinnerDate.adapter = spinnerDateAdapter
        binding.spinnerDate.onItemSelectedListener =
            spinnerDateAdapter.initClickListener(presenter.date)

        binding.spinnerTime.adapter = spinnerTimeAdapter
        binding.spinnerTime.onItemSelectedListener = spinnerTimeAdapter.initClickListener()
    }

    override fun showScreen(screen: Screen) {
        binding.screen = screen
        binding.count = presenter.count
        presenter.createDateSpinnerAdapter(screen.selectableDates)
        presenter.createTimeSpinnerAdapter(screen.selectableDates.first())
    }

    override fun showDateSpinnerAdapter(screenDates: List<ScreenDate>) {
        binding.spinnerDate.setSelection(0)
        spinnerDateAdapter.updateDate(screenDates.map { it.date })
    }

    override fun showTimeSpinnerAdapter(screenDate: ScreenDate) {
        binding.spinnerTime.setSelection(0)
        spinnerTimeAdapter.updateTime(screenDate.getSelectableTimes().map { it })
    }

    override fun showTicket(count: Int) {
        binding.count = count
    }

    override fun navigateToSeatSelection(reservationInfo: ReservationInfo) {
        SeatSelectionActivity.startActivity(this, reservationInfo)
        navigateBackToPrevious()
    }

    override fun navigateBackToPrevious() = runOnUiThread { finish() }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        navigateBackToPrevious()
        return true
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(PUT_TICKET_STATE_KEY, presenter.count)
        outState.putSerializable(PUT_STATE_KEY_SELECTED_DATE, presenter.date)
        outState.putSerializable(PUT_STATE_KEY_SELECTED_TIME, presenter.selectedTime)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        restoreTicketCount(savedInstanceState)
        restoreSelectedDate(savedInstanceState)
        restoreSelectedTime(savedInstanceState)
    }

    private fun restoreTicketCount(savedInstanceState: Bundle) {
        val count = savedInstanceState.getInt(PUT_TICKET_STATE_KEY, DEFAULT_TICKET_COUNT)
        if (count != DEFAULT_TICKET_COUNT) {
            presenter.updateTicket(count)
        }
    }

    private fun restoreSelectedDate(savedInstanceState: Bundle) {
        val savedLocalDate =
            savedInstanceState.getSerializable(PUT_STATE_KEY_SELECTED_DATE) as LocalDate?
        savedLocalDate?.let { localDate ->
            presenter.registerDate(localDate)
            val position = findPositionForSelectedDate(localDate)
            binding.spinnerDate.onItemSelectedListener =
                spinnerDateAdapter.initClickListener(localDate)
            binding.spinnerDate.setSelection(position)
            presenter.createTimeSpinnerAdapter(ScreenDate(localDate))
        }
    }

    private fun findPositionForSelectedDate(selectedDate: LocalDate): Int {
        presenter.selectableDates.forEachIndexed { index, screenDate ->
            if (screenDate.date == selectedDate) {
                return index
            }
        }
        return 0
    }

    private fun restoreSelectedTime(savedInstanceState: Bundle) {
        val savedLocalTime =
            savedInstanceState.getSerializable(PUT_STATE_KEY_SELECTED_TIME) as LocalTime?
        savedLocalTime?.let { localTime ->
            presenter.registerTime(localTime)
            val position = findPositionForSelectedTime(localTime)
            binding.spinnerTime.setSelection(position)
        }
    }

    private fun findPositionForSelectedTime(selectedTime: LocalTime): Int {
        presenter.selectedDate?.let { screenDate ->
            screenDate.getSelectableTimes().forEachIndexed { index, screenTime ->
                if (screenTime == selectedTime) {
                    return index
                }
            }
        }
        return 0
    }

    companion object {
        private const val DEFAULT_ID = -1
        private const val PUT_EXTRA_KEY_MOVIE_ID = "movieId"
        private const val PUT_EXTRA_KEY_THEATER_ID = "theaterId"

        private const val DEFAULT_TICKET_COUNT = -1
        private const val PUT_TICKET_STATE_KEY = "ticketCount"
        private const val PUT_STATE_KEY_SELECTED_DATE = "selectedDate"
        private const val PUT_STATE_KEY_SELECTED_TIME = "selectedTime"

        fun startActivity(
            context: Context,
            movieId: Int,
            theaterId: Int,
        ) {
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra(PUT_EXTRA_KEY_MOVIE_ID, movieId)
            intent.putExtra(PUT_EXTRA_KEY_THEATER_ID, theaterId)
            context.startActivity(intent)
        }
    }
}

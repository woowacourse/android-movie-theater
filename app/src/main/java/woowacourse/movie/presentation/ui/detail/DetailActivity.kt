package woowacourse.movie.presentation.ui.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
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

    private val spinnerDateAdapter: SpinnerDateAdapter by lazy { SpinnerDateAdapter(this) }
    private val spinnerTimeAdapter: SpinnerTimeAdapter by lazy { SpinnerTimeAdapter(this) }

    private val filterActivityLauncher: ActivityResultLauncher<Intent> =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == RESULT_OK) {
                finish()
            }
        }

    override fun initStartView() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.presenter = presenter
        val movieId = intent.getIntExtra(PUT_EXTRA_KEY_MOVIE_ID, DEFAULT_ID)
        val theaterId = intent.getIntExtra(PUT_EXTRA_KEY_THEATER_ID, DEFAULT_ID)
        presenter.loadScreen(movieId, theaterId)
    }

    override fun showScreen(screen: Screen) {
        binding.screen = screen
        binding.count = presenter.count
        binding.localDates = screen.selectableDates.map { it.date }
        initAdapter()
    }

    private fun initAdapter() {
        binding.spinnerDate.adapter = spinnerDateAdapter
        binding.spinnerTime.adapter = spinnerTimeAdapter
    }

    override fun showTime(screenDate: ScreenDate) {
        binding.spinnerTime.setSelection(0)
        binding.localTimes = screenDate.getSelectableTimes().map { it }
    }

    override fun showTicket(count: Int) {
        binding.count = count
    }

    override fun navigateToSeatSelection(reservationInfo: ReservationInfo) {
        val intent = SeatSelectionActivity.getIntent(this, reservationInfo)
        filterActivityLauncher.launch(intent)
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
            presenter.selectDate(localDate)
            val position = findPositionForSelectedDate(localDate)
            binding.spinnerDate.setSelection(position)
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
            val intent = getIntent(context)
            intent.putExtra(PUT_EXTRA_KEY_MOVIE_ID, movieId)
            intent.putExtra(PUT_EXTRA_KEY_THEATER_ID, theaterId)
            context.startActivity(intent)
        }

        fun getIntent(context: Context): Intent = Intent(context, DetailActivity::class.java)
    }
}

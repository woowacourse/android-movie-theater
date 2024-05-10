package woowacourse.movie.ui.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import woowacourse.movie.R
import woowacourse.movie.domain.model.DateRange
import woowacourse.movie.domain.model.Ticket
import woowacourse.movie.domain.model.WeeklyScreenTimePolicy
import woowacourse.movie.domain.repository.DummyMovies
import woowacourse.movie.domain.repository.DummyReservation
import woowacourse.movie.domain.repository.DummyScreens
import woowacourse.movie.ui.ScreenDetailUI
import woowacourse.movie.ui.detail.view.DateTimeSpinnerView
import woowacourse.movie.ui.detail.view.ScreenDetailScreenView
import woowacourse.movie.ui.detail.view.ScreenDetailTicketView
import woowacourse.movie.ui.detail.view.SelectDateListener
import woowacourse.movie.ui.detail.view.SelectTimeListener
import woowacourse.movie.ui.detail.view.TicketReserveListener
import woowacourse.movie.ui.seat.SeatReservationActivity

class ScreenDetailActivity : AppCompatActivity(), ScreenDetailContract.View {
    private val presenter: ScreenDetailContract.Presenter by lazy {
        ScreenDetailPresenter(
            this,
            DummyMovies(),
            DummyScreens(),
            DummyReservation,
            WeeklyScreenTimePolicy(),
        )
    }

    private val screenDetailView: ScreenDetailScreenView by lazy { findViewById(R.id.screen_detail_screen_view) }
    private val ticketView: ScreenDetailTicketView by lazy { findViewById(R.id.screen_detail_ticket_view) }
    private val dateTimeSpinnerView: DateTimeSpinnerView by lazy { findViewById(R.id.screen_detail_date_time_spinner_view) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_screen_detail)
        initView()
    }

    private fun initView() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val screenId = intent.getIntExtra(SCREEN_ID, DEFAULT_SCREEN_ID)
        val theaterId = intent.getIntExtra(THEATER_ID, DEFAULT_THEATER_ID)
        initClickListener(screenId, theaterId)

        presenter.loadScreen(screenId)
        presenter.loadTicket()

        dateTimeSpinnerView.selectedDatePosition()
    }

    private fun initClickListener(
        screenId: Int,
        theaterId: Int,
    ) {
        ticketView.initClickListener(
            screenId = screenId,
            object : TicketReserveListener<Int> {
                override fun increaseTicket() {
                    presenter.plusTicket()
                }

                override fun decreaseTicket() {
                    presenter.minusTicket()
                }

                override fun reserve(screenId: Int) {
                    presenter.reserve(screenId, theaterId)
                }
            },
        )
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        with(outState) {
            putInt(TICKET_COUNT, ticketView.ticketCount())
            putInt(DATE_POSITION, dateTimeSpinnerView.selectedDatePosition())
            putInt(TIME_POSITION, dateTimeSpinnerView.selectedTimePosition())
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        savedInstanceState.let { bundle ->
            val count = bundle.getInt(TICKET_COUNT)
            ticketView.restoreTicketCount(count)
            presenter.saveTicket(count)

            val datePosition = bundle.getInt(DATE_POSITION)
            dateTimeSpinnerView.restoreDatePosition(datePosition)
            presenter.saveDatePosition(datePosition)

            val timePosition = bundle.getInt(TIME_POSITION)
            dateTimeSpinnerView.restoreTimePosition(timePosition)
            presenter.saveTimePosition(timePosition)
        }
    }

    override fun showScreen(screen: ScreenDetailUI) {
        screenDetailView.show(screen)
    }

    override fun showTicket(count: Int) {
        ticketView.updateTicketCount(count)
    }

    override fun showDateTimePicker(
        dateRange: DateRange,
        screenTimePolicy: WeeklyScreenTimePolicy,
        selectDateListener: SelectDateListener,
        selectTimeListener: SelectTimeListener,
    ) {
        dateTimeSpinnerView.show(
            dateRange,
            screenTimePolicy,
            selectDateListener,
            selectTimeListener,
        )
    }

    override fun navigateToSeatsReservation(
        timeReservationId: Int,
        theaterId: Int,
    ) {
        SeatReservationActivity.startActivity(
            context = this,
            timeReservationId = timeReservationId,
            theaterId = theaterId,
        )
    }

    override fun goToBack(e: Throwable) {
        showToastMessage(e)
        finish()
    }

    override fun unexpectedFinish(e: Throwable) {
        showSnackBar(e)
        finish()
    }

    override fun showToastMessage(e: Throwable) {
        when (e) {
            is NoSuchElementException ->
                Toast.makeText(
                    this,
                    R.string.NO_SHOWTIME_INFORMATION,
                    Toast.LENGTH_SHORT,
                ).show()

            is IllegalArgumentException ->
                Toast.makeText(
                    this,
                    getString(R.string.UNEXPECTED_ERROR_OCCURRED).format(
                        Ticket.MIN_TICKET_COUNT,
                        Ticket.MAX_TICKET_COUNT,
                    ),
                    Toast.LENGTH_SHORT,
                ).show()

            else ->
                Toast.makeText(this, R.string.UNEXPECTED_ERROR_OCCURRED, Toast.LENGTH_SHORT)
                    .show()
        }
    }

    override fun showSnackBar(e: Throwable) {
        when (e) {
            is NoSuchElementException ->
                Snackbar.make(
                    findViewById(android.R.id.content),
                    R.string.NO_SHOWTIME_INFORMATION,
                    Snackbar.LENGTH_SHORT,
                ).show()

            is IllegalArgumentException ->
                Snackbar.make(
                    findViewById(android.R.id.content),
                    getString(R.string.UNEXPECTED_ERROR_OCCURRED).format(
                        Ticket.MIN_TICKET_COUNT,
                        Ticket.MAX_TICKET_COUNT,
                    ),
                    Toast.LENGTH_SHORT,
                ).show()

            else ->
                Snackbar.make(
                    findViewById(android.R.id.content),
                    R.string.UNEXPECTED_ERROR_OCCURRED,
                    Snackbar.LENGTH_SHORT,
                ).show()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        finish()
        return true
    }

    companion object {
        private const val SCREEN_ID = "screenId"
        private const val THEATER_ID = "theaterId"
        private const val TICKET_COUNT = "ticketCount"
        private const val DATE_POSITION = "datePosition"
        private const val TIME_POSITION = "timePosition"
        private const val DEFAULT_SCREEN_ID = -1
        private const val DEFAULT_THEATER_ID = -1

        fun startActivity(
            context: Context,
            screenId: Int,
            theaterId: Int,
        ) {
            Intent(context, ScreenDetailActivity::class.java).apply {
                putExtra(SCREEN_ID, screenId)
                putExtra(THEATER_ID, theaterId)
                context.startActivity(this)
            }
        }
    }
}

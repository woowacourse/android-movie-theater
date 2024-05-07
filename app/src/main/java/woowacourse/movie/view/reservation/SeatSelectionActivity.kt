package woowacourse.movie.view.reservation

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TableRow
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import androidx.databinding.DataBindingUtil
import woowacourse.movie.R
import woowacourse.movie.databinding.ActivitySeatSelectionBinding
import woowacourse.movie.db.screening.ScreeningDao
import woowacourse.movie.db.seats.SeatsDao
import woowacourse.movie.model.movie.Movie
import woowacourse.movie.model.movie.ScreeningDateTime
import woowacourse.movie.model.seats.Grade
import woowacourse.movie.model.seats.Seat
import woowacourse.movie.model.seats.Seats
import woowacourse.movie.model.ticket.HeadCount
import woowacourse.movie.model.ticket.Ticket
import woowacourse.movie.presenter.reservation.SeatSelectionContract
import woowacourse.movie.presenter.reservation.SeatSelectionPresenter
import woowacourse.movie.repository.ReservationTicketRepositoryImpl
import woowacourse.movie.utils.MovieUtils.bundleSerializable
import woowacourse.movie.utils.MovieUtils.convertAmountFormat
import woowacourse.movie.utils.MovieUtils.intentSerializable
import woowacourse.movie.utils.MovieUtils.makeToast
import woowacourse.movie.view.home.HomeFragment.Companion.MOVIE_ID
import woowacourse.movie.view.reservation.ReservationDetailActivity.Companion.HEAD_COUNT
import woowacourse.movie.view.reservation.ReservationDetailActivity.Companion.RESERVATION_TICKET_ID
import woowacourse.movie.view.reservation.ReservationDetailActivity.Companion.SCREENING_DATE_TIME
import woowacourse.movie.view.result.ReservationResultActivity
import woowacourse.movie.view.setting.SettingFragment.Companion.PUSH_SETTING
import woowacourse.movie.view.theater.TheaterSelectionFragment.Companion.THEATER_ID

class SeatSelectionActivity : AppCompatActivity(), SeatSelectionContract.View {
    private val binding: ActivitySeatSelectionBinding by lazy {
        DataBindingUtil.setContentView(
            this,
            R.layout.activity_seat_selection,
        )
    }
    private val presenter: SeatSelectionPresenter by lazy {
        SeatSelectionPresenter(
            view = this,
            seatsDao = SeatsDao(),
            screeningDao = ScreeningDao(),
            repository = ReservationTicketRepositoryImpl(this@SeatSelectionActivity),
        )
    }

    private lateinit var seatsTable: List<Button>
    private lateinit var headCount: HeadCount
    private lateinit var screeningDateTime: ScreeningDateTime
    private var movieId = 0
    private var theaterId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        movieId = takeMovieId()
        theaterId = receiveTheaterId()
        receiveHeadCount()
        receiveScreeningDateTime()
        seatsTable = collectSeatsInTableLayout()
        with(presenter) {
            initSeatNumbers()
            loadMovie(movieId)
        }
        initializeConfirmButton()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.apply {
            putSerializable(HEAD_COUNT, headCount)
            putSerializable(SEATS, presenter.seats)
            putIntegerArrayList(SEATS_INDEX, ArrayList(presenter.seats.seatsIndex))
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        savedInstanceState.let { bundle ->
            runCatching {
                restoreSeatsData(bundle)
                restoreReservationData(bundle)
            }.onFailure {
                showErrorToast()
                finish()
            }
        }
    }

    override fun restoreSelectedSeats(selectedSeats: List<Int>) {
        seatsTable.forEachIndexed { index, button ->
            button.isSelected = index in selectedSeats
        }
    }

    override fun initializeSeatsTable(
        index: Int,
        seat: Seat,
    ) {
        seatsTable[index].apply {
            showSeatNumber(seat)
            updateReservationInformation(index, seat)
        }
    }

    override fun Button.showSeatNumber(seat: Seat) {
        text = getString(R.string.select_seat_number, seat.row, seat.column)
        setTextColor(setUpSeatColorByGrade(seat.grade))
    }

    override fun Button.updateReservationInformation(
        index: Int,
        seat: Seat,
    ) {
        setOnClickListener {
            if (getSeatsCount() < headCount.count || isSelected) {
                updateSeatSelectedState(index, isSelected)
                presenter.manageSelectedSeats(isSelected, index, seat)
                presenter.updateTotalPrice(isSelected, seat)
                setConfirmButtonEnabled(getSeatsCount())
            }
        }
    }

    override fun setUpSeatColorByGrade(grade: Grade): Int {
        return when (grade) {
            Grade.B -> getColor(R.color.purple_500)
            Grade.S -> getColor(R.color.teal_700)
            Grade.A -> getColor(R.color.blue)
        }
    }

    override fun updateSeatSelectedState(
        index: Int,
        isSelected: Boolean,
    ) {
        seatsTable[index].isSelected = !isSelected
    }

    override fun showMovieTitle(movie: Movie) {
        binding.textviewSeatSelectionTitle.text = movie.title
    }

    override fun showAmount(amount: Int) {
        binding.textviewSeatSelectionPrice.text = convertAmountFormat(this, amount)
    }

    override fun navigateToFinished(
        ticket: Ticket,
        ticketId: Long,
    ) = runOnUiThread {
        if (isOnAlarmState()) {
            presenter.settingAlarm(
                context = this@SeatSelectionActivity,
                movieTitle = binding.textviewSeatSelectionTitle.text.toString(),
                ticket = ticket,
                ticketId = ticketId,
            )
        }
        val intent = Intent(this, ReservationResultActivity::class.java)
        intent.putExtra(RESERVATION_TICKET_ID, ticketId)
        startActivity(intent)
    }

    override fun setConfirmButtonEnabled(count: Int) {
        binding.buttonSeatSelectionConfirm.isEnabled = count >= headCount.count
    }

    override fun launchReservationConfirmDialog() {
        if (binding.buttonSeatSelectionConfirm.isEnabled) {
            AlertDialog.Builder(this)
                .setTitle(getString(R.string.seat_selection_reservation_confirm))
                .setMessage(getString(R.string.seat_selection_reservation_ask_purchase_ticket))
                .setPositiveButton(getString(R.string.seat_selection_reservation_finish)) { _, _ ->
                    Thread {
                        presenter.makeTicket(movieId, theaterId, screeningDateTime)
                    }.start()
                }
                .setNegativeButton(getString(R.string.seat_selection_cancel)) { dialog, _ ->
                    dialog.dismiss()
                }
                .setCancelable(false)
                .show()
        }
    }

    override fun showErrorToast() = makeToast(this, getString(R.string.all_error))

    private fun takeMovieId() =
        intent.getIntExtra(
            MOVIE_ID,
            ReservationDetailActivity.DEFAULT_ID,
        )

    private fun receiveHeadCount() {
        runCatching {
            intent.intentSerializable(HEAD_COUNT, HeadCount::class.java)
                ?: throw NoSuchElementException()
        }.onSuccess {
            headCount = it
        }.onFailure {
            showErrorToast()
            finish()
        }
    }

    private fun receiveTheaterId(): Int = intent.getIntExtra(THEATER_ID, 0)

    private fun receiveScreeningDateTime() {
        runCatching {
            intent.intentSerializable(
                SCREENING_DATE_TIME, ScreeningDateTime::class.java,
            ) ?: throw NoSuchElementException()
        }.onSuccess {
            screeningDateTime = it
        }.onFailure {
            showErrorToast()
            finish()
        }
    }

    private fun collectSeatsInTableLayout(): List<Button> =
        binding.tableLayoutSeatSelection.children.filterIsInstance<TableRow>()
            .flatMap { it.children }
            .filterIsInstance<Button>().toList()

    private fun getSeatsCount(): Int = seatsTable.count { seat -> seat.isSelected }

    private fun initializeConfirmButton() {
        binding.buttonSeatSelectionConfirm.setOnClickListener {
            presenter.initializeConfirmButton()
        }
    }

    private fun restoreReservationData(bundle: Bundle) {
        val headCount =
            bundle.bundleSerializable(HEAD_COUNT, HeadCount::class.java)
                ?: throw NoSuchElementException()
        presenter.restoreReservation(headCount.count)
    }

    private fun restoreSeatsData(bundle: Bundle) {
        val seats =
            bundle.bundleSerializable(SEATS, Seats::class.java) ?: throw NoSuchElementException()
        val index = bundle.getIntegerArrayList(SEATS_INDEX) ?: throw NoSuchElementException()
        presenter.restoreSeats(seats, index.toList())
    }

    private fun isOnAlarmState(): Boolean {
        val sharedPreference = this.getSharedPreferences(PUSH_SETTING, MODE_PRIVATE)
        return sharedPreference.getBoolean(PUSH_SETTING, false)
    }

    companion object {
        const val SEATS = "seats"
        const val SEATS_INDEX = "seatsIndex"
    }
}

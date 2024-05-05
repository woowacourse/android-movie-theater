package woowacourse.movie.feature.seatselection

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
import woowacourse.movie.db.theater.TheaterDao
import woowacourse.movie.feature.finished.ReservationFinishedActivity
import woowacourse.movie.feature.home.HomeFragment.Companion.MOVIE_ID
import woowacourse.movie.feature.reservation.ReservationActivity
import woowacourse.movie.feature.reservation.ReservationActivity.Companion.HEAD_COUNT
import woowacourse.movie.feature.reservation.ReservationActivity.Companion.SCREENING_DATE_TIME
import woowacourse.movie.feature.reservation.ReservationActivity.Companion.TICKET
import woowacourse.movie.feature.theater.TheaterSelectionFragment.Companion.THEATER_ID
import woowacourse.movie.model.movie.Movie
import woowacourse.movie.model.movie.ScreeningDateTime
import woowacourse.movie.model.seats.Grade
import woowacourse.movie.model.seats.Seat
import woowacourse.movie.model.seats.Seats
import woowacourse.movie.model.ticket.HeadCount
import woowacourse.movie.model.ticket.Ticket
import woowacourse.movie.utils.MovieUtils.bundleSerializable
import woowacourse.movie.utils.MovieUtils.convertAmountFormat
import woowacourse.movie.utils.MovieUtils.intentSerializable
import woowacourse.movie.utils.MovieUtils.makeToast

class SeatSelectionActivity : AppCompatActivity(), SeatSelectionContract.View {
    private val binding: ActivitySeatSelectionBinding by lazy { DataBindingUtil.setContentView(this, R.layout.activity_seat_selection) }
    private lateinit var presenter: SeatSelectionPresenter

    private lateinit var seatsTable: List<Button>
    private lateinit var headCount: HeadCount
    private lateinit var screeningDateTime: ScreeningDateTime

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.activity = this
        initPresenter()
        initAmount()
        receiveHeadCount()
        receiveScreeningDateTime()
        seatsTable = collectSeatsInTableLayout()
        with(presenter) {
            loadSeatNumber()
            loadMovie()
        }
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
                val isReservationValid = getSeatsCount() >= headCount.count
                setConfirmButtonEnabled(isReservationValid)
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
        binding.movieTitle = movie.title
    }

    override fun showAmount(amount: Int) {
        binding.amount = convertAmountFormat(this, amount)
    }

    override fun navigateToFinished(ticket: Ticket) {
        val intent = Intent(this, ReservationFinishedActivity::class.java)
        intent.putExtra(TICKET, ticket)
        startActivity(intent)
    }

    override fun setConfirmButtonEnabled(isEnabled: Boolean) {
        binding.buttonSeatSelectionConfirm.isEnabled = isEnabled
    }

    override fun launchReservationConfirmDialog() {
        if (binding.buttonSeatSelectionConfirm.isEnabled) {
            AlertDialog.Builder(this)
                .setTitle(getString(R.string.seat_selection_reservation_confirm))
                .setMessage(getString(R.string.seat_selection_reservation_ask_purchase_ticket))
                .setPositiveButton(getString(R.string.seat_selection_reservation_finish)) { _, _ ->
                    presenter.makeTicket(screeningDateTime)
                }
                .setNegativeButton(getString(R.string.seat_selection_cancel)) { dialog, _ ->
                    dialog.dismiss()
                }
                .setCancelable(false)
                .show()
        }
    }

    override fun showErrorToast() = makeToast(this, getString(R.string.all_error))

    private fun initPresenter() {
        presenter =
            SeatSelectionPresenter(
                this,
                SeatsDao(),
                ScreeningDao(),
                TheaterDao(),
                takeMovieId(),
                receiveTheaterId(),
            )
        binding.presenter = presenter
    }

    private fun takeMovieId() =
        intent.getIntExtra(
            MOVIE_ID,
            ReservationActivity.DEFAULT_MOVIE_ID,
        )

    private fun receiveHeadCount() {
        runCatching {
            intent.intentSerializable(HEAD_COUNT, HeadCount::class.java) ?: throw NoSuchElementException()
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
        binding.tableLayoutSeatSelection.children.filterIsInstance<TableRow>().flatMap { it.children }
            .filterIsInstance<Button>().toList()

    private fun getSeatsCount(): Int = seatsTable.count { seat -> seat.isSelected }

    private fun restoreReservationData(bundle: Bundle) {
        val headCount = bundle.bundleSerializable(HEAD_COUNT, HeadCount::class.java) ?: throw NoSuchElementException()
        presenter.restoreReservation(headCount.count)
    }

    private fun restoreSeatsData(bundle: Bundle) {
        val seats = bundle.bundleSerializable(SEATS, Seats::class.java) ?: throw NoSuchElementException()
        val index = bundle.getIntegerArrayList(SEATS_INDEX) ?: throw NoSuchElementException()
        presenter.restoreSeats(seats, index.toList())
    }

    private fun initAmount() {
        binding.amount = getString(R.string.select_seat_default_price)
    }

    companion object {
        const val SEATS = "seats"
        const val SEATS_INDEX = "seatsIndex"
    }
}

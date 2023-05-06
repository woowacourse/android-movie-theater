package woowacourse.app.ui.seat

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import woowacourse.app.model.BookedMovieUiModel
import woowacourse.app.model.Mapper.toBookedMovie
import woowacourse.app.model.Mapper.toUiModel
import woowacourse.app.model.seat.SeatMapper.toDomainModel
import woowacourse.app.model.seat.SeatMapper.toUiModel
import woowacourse.app.model.seat.SeatRankColor
import woowacourse.app.model.seat.SelectedSeatUiModel
import woowacourse.app.ui.completed.CompletedActivity
import woowacourse.app.usecase.theater.TheaterUseCase
import woowacourse.app.util.getParcelable
import woowacourse.app.util.getParcelableBundle
import woowacourse.app.util.shortToast
import woowacourse.data.reservation.ReservationDatabase
import woowacourse.data.reservation.ReservationRepositoryImpl
import woowacourse.data.theater.TheaterDatabase
import woowacourse.data.theater.TheaterRepositoryImpl
import woowacourse.domain.BoxOffice
import woowacourse.domain.reservation.Reservation
import woowacourse.domain.ticket.Position
import woowacourse.domain.ticket.Seat
import woowacourse.movie.R
import woowacourse.movie.databinding.ActivitySeatBinding

class SeatActivity : AppCompatActivity(), SeatContract.View {
    private lateinit var binding: ActivitySeatBinding
    override lateinit var presenter: SeatPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySeatBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initPresenter()
        binding.presenter = presenter
        presenter.setTable()
    }

    private fun initPresenter() {
        val bookedMovieUiModel = getData()
        presenter = SeatPresenter(
            this,
            BoxOffice(ReservationRepositoryImpl(ReservationDatabase)),
            bookedMovieUiModel.toBookedMovie(),
            TheaterUseCase(TheaterRepositoryImpl(TheaterDatabase)),
        )
    }

    private fun getData(): BookedMovieUiModel {
        val bookedMovieUiModel = intent.getParcelable(BOOKED_MOVIE, BookedMovieUiModel::class.java)
        if (bookedMovieUiModel == null) {
            shortToast(R.string.error_no_such_data)
            finish()
        }
        return bookedMovieUiModel!!
    }

    override fun onSaveInstanceState(outState: Bundle) {
        val selectedSeatUiModel =
            SelectedSeatUiModel(presenter.getSelectedSeats().map { it.toUiModel() }.toSet())
        outState.putParcelable("SELECTED_SEAT", selectedSeatUiModel)
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        val selectedSeatUiModel =
            savedInstanceState.getParcelableBundle("SELECTED_SEAT", SelectedSeatUiModel::class.java)
        restoreSelectedSeat(selectedSeatUiModel)
    }

    private fun restoreSelectedSeat(selectedSeatUiModel: SelectedSeatUiModel) {
        selectedSeatUiModel.selectedSeat.forEach {
            presenter.selectSeat(it.toDomainModel())
        }
    }

    override fun errorControl() {
        shortToast(R.string.error_no_such_theater)
        finish()
    }

    override fun showSeatFull() {
        shortToast(R.string.no_more_seat)
    }

    override fun setTableSize(rowSize: Int, columnSize: Int) {
        binding.seatTableLayout.setTable(rowSize, columnSize)
    }

    override fun setTableColor(
        sRank: List<IntRange>,
        aRank: List<IntRange>,
        bRank: List<IntRange>,
    ) {
        binding.seatTableLayout.setColorRange(
            mapOf(
                sRank to SeatRankColor.S.colorId,
                aRank to SeatRankColor.A.colorId,
                bRank to SeatRankColor.B.colorId,
            ),
        )
    }

    override fun setTableClickListener(getSeat: (clickedPosition: Position) -> Seat) {
        binding.seatTableLayout.setClickListener { clickedPosition ->
            val seat = getSeat(clickedPosition)
            presenter.selectSeat(seat)
        }
    }

    override fun selectSeatView(seat: Seat) {
        val view = binding.seatTableLayout[seat.position.row][seat.position.column]
        view.isSelected = !view.isSelected
    }

    override fun setConfirmButtonEnable(isSeatFull: Boolean) {
        val buttonConfirm = binding.buttonSeatConfirm
        buttonConfirm.isEnabled = isSeatFull
        if (buttonConfirm.isEnabled) {
            buttonConfirm.setBackgroundResource(R.color.purple_700)
            return
        }
        buttonConfirm.setBackgroundResource(R.color.gray_400)
    }

    override fun completeBooking(reservation: Reservation) {
        ScreeningTimeReminder(this, reservation.toUiModel())
        startActivity(CompletedActivity.getIntent(this, reservation.toUiModel()))
        finish()
    }

    override fun showDialog() {
        AlertDialog.Builder(this)
            .setTitle(R.string.booking_confirm)
            .setMessage(R.string.booking_really)
            .setPositiveButton(R.string.yes) { _, _ -> presenter.completeBooking() }
            .setNegativeButton(R.string.no) { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    override fun setPaymentText(payment: Int) {
        binding.textSeatPayment.text = getString(R.string.won, payment)
    }

    companion object {
        private const val BOOKED_MOVIE = "BOOKED_MOVIE"

        fun getIntent(
            context: Context,
            bookedMovieUiModel: BookedMovieUiModel,
        ): Intent {
            return Intent(context, SeatActivity::class.java).apply {
                putExtra(BOOKED_MOVIE, bookedMovieUiModel)
            }
        }
    }
}

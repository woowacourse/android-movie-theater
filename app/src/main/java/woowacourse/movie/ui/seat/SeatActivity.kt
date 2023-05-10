package woowacourse.movie.ui.seat

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import woowacourse.movie.R
import woowacourse.movie.databinding.ActivitySeatBinding
import woowacourse.movie.model.BookedMovie
import woowacourse.movie.model.ReservationUiModel
import woowacourse.movie.ui.bookinghistory.BookingHistoryDBAdapter
import woowacourse.movie.ui.bookinghistory.BookingHistoryDBHelper
import woowacourse.movie.ui.completed.CompletedActivity
import woowacourse.movie.util.getParcelable
import woowacourse.movie.util.shortToast

class SeatActivity : AppCompatActivity(), SeatContract.View {

    private lateinit var binding: ActivitySeatBinding
    private val bookedMovie: BookedMovie? by lazy {
        intent.getParcelable(BOOKED_MOVIE, BookedMovie::class.java)
    }
    private val seatPresenter: SeatPresenter by lazy {
        bookedMovie?.let {
            SeatPresenter(
                view = this,
                repository = BookingHistoryDBAdapter(BookingHistoryDBHelper(this)),
                timeReminder = ScreeningTimeReminder(this),
                bookedMovie = it,
            )
        } ?: throw IllegalArgumentException(RECEIVING_MOVIE_ERROR)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_seat)
        binding.presenter = seatPresenter

        seatPresenter.initMovieTitle()
        seatPresenter.initSelectedSeats()
        initSeatSelectListener()
    }

    override fun initMovieTitleText(movieTitle: String) {
        binding.textSeatMovieTitle.text = movieTitle
    }

    override fun showCannotSelectSeat() {
        shortToast(CANNOT_SELECT)
    }

    override fun initSeatTableView(
        rowSize: Int,
        columnSize: Int
    ) {
        binding.seatTableLayout.addRow(rowSize, columnSize)
        binding.seatTableLayout.initSeatsText()
    }

    override fun initSeatSelectListener() {
        binding.seatTableLayout.setOnSeatSelectListener { row, col ->
            seatPresenter.onSeatSelected(row, col)
        }
    }

    override fun setSeatPayment(payment: Int) {
        binding.textSeatPayment.text = payment.toString()
    }

    override fun setButtonState(enabled: Boolean) {
        binding.buttonSeatConfirm.isEnabled = enabled

        with(binding.buttonSeatConfirm) {
            if (isEnabled) {
                setBackgroundResource(R.color.purple_700)
                return
            }
            setBackgroundResource(R.color.gray_400)
        }
    }

    override fun setSeatSelected(row: Int, col: Int) {
        binding.seatTableLayout.setSelected(row, col)
    }

    override fun showSeatReservationDialog(reservation: ReservationUiModel) {
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.reservation_check))
            .setMessage(getString(R.string.ask_reservation))
            .setPositiveButton(getString(R.string.yes)) { _, _ -> navigateToCompletedView(reservation) }
            .setNegativeButton(R.string.no) { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    private fun navigateToCompletedView(reservation: ReservationUiModel) {
        startActivity(CompletedActivity.getIntent(this, reservation))
        finish()
    }

    companion object {
        private const val RECEIVING_MOVIE_ERROR = "예약하는 영화의 정보를 받아올 수 없습니다."
        private const val CANNOT_SELECT = "더 이상 자리를 선택할 수 없습니다."
        private const val BOOKED_MOVIE = "BOOKED_MOVIE"

        fun getIntent(context: Context, bookedMovie: BookedMovie?): Intent {
            return Intent(context, SeatActivity::class.java).apply {
                putExtra(BOOKED_MOVIE, bookedMovie)
            }
        }
    }
}
